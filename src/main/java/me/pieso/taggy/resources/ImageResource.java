package me.pieso.taggy.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import me.pieso.taggy.core.Image;
import me.pieso.taggy.db.ImageDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/images")
@Produces(MediaType.APPLICATION_JSON)
public class ImageResource {
    private final ImageDAO dao;

    public ImageResource(ImageDAO dao) {
        this.dao = dao;
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

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Image getImage(@PathParam("id") LongParam id) {
        Optional<Image> image = dao.findById(id.get());
        return dao.findById(id.get()).orElseThrow(() -> new NotFoundException("No such image."));
    }
}
