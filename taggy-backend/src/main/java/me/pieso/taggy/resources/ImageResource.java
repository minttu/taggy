package me.pieso.taggy.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.io.ByteStreams;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.PATCH;
import io.dropwizard.jersey.params.LongParam;
import me.pieso.taggy.daos.ImageDAO;
import me.pieso.taggy.exceptions.NonImageFileException;
import me.pieso.taggy.models.Image;
import me.pieso.taggy.models.Tag;
import me.pieso.taggy.services.ImageService;
import me.pieso.taggy.services.ImageUpload;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.exception.ConstraintViolationException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Path("/images")
@Produces(MediaType.APPLICATION_JSON)
public class ImageResource {
    private final ImageDAO dao;
    private final ImageService imageService;

    public ImageResource(ImageDAO dao, ImageService imageService) {
        this.dao = dao;
        this.imageService = imageService;
    }

    @GET
    @Timed
    @UnitOfWork
    public List<Image> getImages(@QueryParam("cursor") Optional<Long> cursor,
                                 @QueryParam("tags") Optional<String> tags) {
        String[] tagList = Stream.of(tags.orElse("").split(",")).map(s -> s.trim()).filter(s -> s.length() > 0).toArray(size -> new String[size]);
        long cursorValue = cursor.orElse(Long.MAX_VALUE);
        if (tagList.length > 0) {
            return dao.getImagesHavingTags(cursorValue, tagList);
        } else {
            return dao.getImages(cursorValue);
        }
    }

    @POST
    @Timed
    @UnitOfWork
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Image postImage(@Context final HttpServletRequest request,
                           @FormDataParam("data") InputStream inputStream,
                           @FormDataParam("data") FormDataContentDisposition fileDetail) {
        int contentLength = request.getContentLength();
        if (contentLength == -1 || contentLength > 5 * 1024 * 1024) {
            throw new BadRequestException("Image size must be under 5MiB");
        }

        ImageUpload imageUpload;
        Image image;

        try {
            byte[] imageData = ByteStreams.toByteArray(inputStream);
            imageUpload = new ImageUpload(imageData);
            image = new Image(imageUpload.getHash(), imageUpload.getType());
            dao.save(image); // Hash uniqueness is checked by schema
            imageService.save(imageUpload);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new InternalServerErrorException(e);
        } catch (ConstraintViolationException e) {
            throw new BadRequestException("This image has already been uploaded");
        } catch (NonImageFileException e) {
            throw new BadRequestException("Uploaded file is corrupt");
        }

        return image;
    }

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Image getImage(@PathParam("id") LongParam id) {
        Optional<Image> image = dao.findById(id.get());
        return dao.findById(id.get()).orElseThrow(() -> new NotFoundException("No such image"));
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    @UnitOfWork
    public Image tagImage(@PathParam("id") LongParam id,
                          @NotNull @Valid Image imageParam) {
        Optional<Image> image = dao.findById(id.get());
        if (!image.isPresent()) {
            throw new NotFoundException("No such image");
        }
        Image actualImage = image.get();
        Set<Tag> wantedTags = imageParam.getTags();
        actualImage.setTags(wantedTags);
        return actualImage;
    }
}
