/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josue.tomcat.cdi.jpa.jaxrs;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("users")
@RequestScoped
public class UsersResource {

    @Inject
    UserControl control;

    @GET
    @Produces("application/json")
    public List<User> getUsers() {
        return control.getUsers();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public User getUser(@PathParam("id") String id) {
        return control.getUser(id);
    }

    @POST
    @Produces("application/json")
    public User getUser(User user) {
        return control.createUser(user);
    }

}
