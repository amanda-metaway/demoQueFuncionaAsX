package com.example.demo.api;

import com.example.demo.Model.Pet;
import com.example.demo.Model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UserRest {

    @POST
    @Path("/")
    User createUser(User user);

    @GET
    @Path("/{id}")
    User getUser(@PathParam("id") Integer id);

    @PUT
    @Path("/{id}")
    User updateUser(@PathParam("id") Integer id, User user);

    @DELETE
    @Path("/{id}")
    void deleteUser(@PathParam("id") Integer id);

    @GET
    @Path("/")
    List<User> listUsers();

    @POST
    @Path("/createUserAndPet")
    void createUserAndPet(User user, Pet pet);

    @POST
    @Path("/savePet")
    void savePet(Pet pet);
}