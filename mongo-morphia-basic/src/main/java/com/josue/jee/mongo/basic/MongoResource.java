/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josue.jee.mongo.basic;

import com.josue.jee.mongo.basic.bean.Address;
import com.josue.jee.mongo.basic.bean.User;
import com.josue.jee.mongo.basic.storage.MorphiaStorage;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author Josue
 */
@Path("mongo")
@Stateless
public class MongoResource {

    @Context
    private UriInfo context;

    @Inject
    MorphiaStorage storage;

    private static final Logger LOG = Logger.getLogger(MongoResource.class.getName());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByField() {
        List<User> foundUsers = storage.findAll();
        return Response.status(Response.Status.OK).entity(foundUsers).build();
    }

    @GET
    @Path("{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("uuid") String uuid) {
        User foundUser = storage.findById(uuid);
        return Response.status(Response.Status.OK).entity(foundUser).build();
    }

    @GET
    @Path("by-field")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByField(@QueryParam("field") String field, @QueryParam("value") String value) {
        List<User> foundUsers = storage.findBy(field, value);
        return Response.status(Response.Status.OK).entity(foundUsers).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@QueryParam("name") String name) {
        User foundUser = storage.createUser(randUser(name));
        return Response.status(Response.Status.CREATED).entity(foundUser).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@QueryParam("uuid") String id, @QueryParam("field") String field, @QueryParam("value") String value) {
        User foundUser = storage.update(id, field, value);
        return Response.status(Response.Status.CREATED).entity(foundUser).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam("uuid") String id) {
        storage.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    private User randUser(String name) {
        User user = new User();
        user.setUuid(UUID.randomUUID().toString());
        user.setIdade(new Random().nextInt(Integer.MAX_VALUE) + 1);
        user.setName(name);

        Address address = new Address();
        address.setCity("CITY-" + UUID.randomUUID().toString());
        address.setNumber(new Random().nextInt(Integer.MAX_VALUE) + 1);
        address.setStreet("STREET-" + UUID.randomUUID().toString());

        user.setAddress(address);

        return user;

    }

}
