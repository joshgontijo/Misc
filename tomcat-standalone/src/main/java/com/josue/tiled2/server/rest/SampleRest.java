/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josue.tiled2.server.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Josue
 */
@Path("ping")
public class SampleRest {
 
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ping(){
        return "Ok";
    }
    
}
