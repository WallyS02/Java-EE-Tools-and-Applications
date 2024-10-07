package org.demo.demo.instrument.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.demo.demo.instrument.dto.GetInstrumentResponse;
import org.demo.demo.instrument.dto.GetInstrumentsResponse;
import org.demo.demo.instrument.dto.PatchInstrumentRequest;
import org.demo.demo.instrument.dto.PutInstrumentRequest;

import java.util.UUID;

@Path("")
public interface InstrumentController {
    @GET
    @Path("/instruments")
    @Produces(MediaType.APPLICATION_JSON)
    GetInstrumentsResponse getInstruments();

    @GET
    @Path("/instruments/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    GetInstrumentsResponse getInstrumentsByName(@PathParam("name") String name);

    @GET
    @Path("/instruments/type/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    GetInstrumentsResponse getInstrumentsByType(@PathParam("type") String type);

    @GET
    @Path("/instruments/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetInstrumentResponse getInstrument(@PathParam("id") UUID id);

    @PUT
    @Path("/instruments/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void create(@PathParam("id") UUID id, PutInstrumentRequest request);

    @PATCH
    @Path("/instruments/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void update(@PathParam("id") UUID id, PatchInstrumentRequest request);

    @DELETE
    @Path("/instruments/{id}")
    void delete(@PathParam("id") UUID id);
}
