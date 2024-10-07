package org.demo.demo.musician.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.demo.demo.musician.dto.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Path("")
public interface MusicianController {
    @GET
    @Path("/musicians")
    @Produces(MediaType.APPLICATION_JSON)
    GetMusiciansResponse getMusicians();

    @GET
    @Path("/musicians/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetMusicianResponse getMusician(@PathParam("id") UUID id);

    @GET
    @Path("/musicians/login/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    GetMusicianResponse getMusicianByLogin(@PathParam("login") String login);

    @GET
    @Path("/musicians/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    GetMusicianResponse getMusiciansByEmail(@PathParam("email") String email);

    @GET
    @Path("/musicians/first-name/{firstName}")
    @Produces(MediaType.APPLICATION_JSON)
    GetMusiciansResponse getMusiciansByFirstName(@PathParam("firstName") String firstName);

    @GET
    @Path("/musicians/last-name/{lastName}")
    @Produces(MediaType.APPLICATION_JSON)
    GetMusiciansResponse getMusiciansByLastName(@PathParam("lastName") String lastName);

    @PUT
    @Path("/musicians/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void create(@PathParam("id") UUID id, PutMusicianRequest request);

    @PATCH
    @Path("/musicians/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void update(@PathParam("id") UUID id, PatchMusicianRequest request);

    @PATCH
    @Path("/musicians/{id}/password")
    @Consumes({MediaType.APPLICATION_JSON})
    void updatePassword(@PathParam("id") UUID id, PutPasswordRequest request);

    @DELETE
    @Path("/musicians/{id}")
    void delete(@PathParam("id") UUID id);

    @GET
    @Path("/musicians/{id}/image")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    byte[] getImage(@PathParam("id") UUID id) throws IOException;

    @PUT
    @Path("/musicians/{id}/image")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    void createImage(@PathParam("id") UUID id, InputStream image);

    @PATCH
    @Path("/musicians/{id}/image")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    void updateImage(@PathParam("id") UUID id, InputStream image);

    @DELETE
    @Path("/musicians/{id}/image")
    void deleteImage(@PathParam("id") UUID id);
}
