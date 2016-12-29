package me.pieso.taggy.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import me.pieso.taggy.core.Tag;
import me.pieso.taggy.db.TagDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
public class TagResource {
    private final TagDAO tagDAO;

    public TagResource(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @GET
    @Path("/{name}")
    @Timed
    @UnitOfWork
    public Tag getTag(@PathParam("name") String name) {
        return tagDAO.findByName(name).orElseThrow(() -> new NotFoundException("No such tag"));
    }
}
