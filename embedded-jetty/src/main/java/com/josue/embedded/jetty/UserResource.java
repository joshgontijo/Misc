package com.josue.embedded.jetty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.logging.Logger;

/**
 * Created by Josue on 30/11/2016.
 */
@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UserResource {

    private static final Logger logger = Logger.getLogger(UserResource.class.getName());

    @Context
    private UriInfo uriInfo;

    @Inject
    private UserControl control;

    @GET
    public User getUser(){

        logger.info(uriInfo.getAbsolutePath().getPath());

        return control.getUser();
    }
}
