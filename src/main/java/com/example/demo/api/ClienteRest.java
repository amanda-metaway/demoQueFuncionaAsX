package com.example.demo.api;

import com.example.demo.Model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ClienteRest {

    @GET
    @Path("/{id}")
    User getCliente(@PathParam("id") Integer id);
}
