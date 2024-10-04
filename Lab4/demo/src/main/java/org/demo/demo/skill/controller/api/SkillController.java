package org.demo.demo.skill.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.demo.demo.skill.dto.GetSkillResponse;
import org.demo.demo.skill.dto.GetSkillsResponse;
import org.demo.demo.skill.dto.PatchSkillRequest;
import org.demo.demo.skill.dto.PutSkillRequest;
import org.demo.demo.util.Level;

import java.util.UUID;

public interface SkillController {
    @GET
    @Path("/skills")
    @Produces(MediaType.APPLICATION_JSON)
    GetSkillsResponse getSkills();

    @GET
    @Path("/skills/level/{level}")
    @Produces(MediaType.APPLICATION_JSON)
    GetSkillsResponse getSkillsByLevel(@PathParam("level") Level level);

    /*@GET
    @Path("/skills/musician/{musicianId}")
    @Produces(MediaType.APPLICATION_JSON)
    GetSkillsResponse getSkillsByMusician(@PathParam("musicianId") UUID musicianId);*/

    @GET
    @Path("/instruments/{instrumentId}/skills")
    @Produces(MediaType.APPLICATION_JSON)
    GetSkillsResponse getSkillsByInstrument(@PathParam("instrumentId") UUID instrumentId);

    @GET
    @Path("/skills/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetSkillResponse getSkill(@PathParam("id") UUID id);

    @PUT
    @Path("/instruments/{instrumentId}/skills/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void create(@PathParam("id") UUID id, PutSkillRequest request, @PathParam("instrumentId") UUID instrumentId);

    @PATCH
    @Path("/skills/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void update(@PathParam("id") UUID id, PatchSkillRequest request);

    @DELETE
    @Path("/skills/{id}")
    void delete(@PathParam("id") UUID id);
}
