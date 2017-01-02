package com.josue.jetee.example.user;

import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CacheResult;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Josue on 30/11/2016.
 */
@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
@CacheDefaults(cacheName = "userCache")
public class UserResource {

    private static final Logger logger = Logger.getLogger(UserResource.class.getName());

    @Context
    private UriInfo uriInfo;

    @Inject
    private UserJpaRepository userRepository;

    @GET
    @CacheResult
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @GET
    @Path("{id}")
    @CacheResult
    public User getById(@PathParam("id") @CacheKey String id) {
        return userRepository.find(id);
    }

    @POST
    public Response create(User user) {
        User created = userRepository.create(user);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }
}
