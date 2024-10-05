package org.demo.demo.skill.controller.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;
import org.demo.demo.component.DtoFunctionFactory;
import org.demo.demo.skill.controller.api.SkillController;
import org.demo.demo.skill.dto.GetSkillResponse;
import org.demo.demo.skill.dto.GetSkillsResponse;
import org.demo.demo.skill.dto.PatchSkillRequest;
import org.demo.demo.skill.dto.PutSkillRequest;
import org.demo.demo.skill.service.SkillService;
import org.demo.demo.util.Level;

import java.util.UUID;

@Path("")
@Log
public class SkillRestController implements SkillController {
    private final SkillService skillService;

    private final DtoFunctionFactory dtoFunctionFactory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public SkillRestController(SkillService skillService, DtoFunctionFactory dtoFunctionFactory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.skillService = skillService;
        this.dtoFunctionFactory = dtoFunctionFactory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetSkillsResponse getSkills() {
        return dtoFunctionFactory.skillsToResponseFunction().apply(skillService.findAll());
    }

    @Override
    public GetSkillsResponse getSkillsByLevel(Level level) {
        return dtoFunctionFactory.skillsToResponseFunction().apply(skillService.findAllByLevel(level));
    }

    /*@Override
    public GetSkillsResponse getSkillsByMusician(UUID musicianId) {
        return dtoFunctionFactory.skillsToResponseFunction().apply(skillService.findAllByMusician(musicianId).orElseThrow(NotFoundException::new));
    }*/

    @Override
    public GetSkillsResponse getSkillsByInstrument(UUID instrumentId) {
        return dtoFunctionFactory.skillsToResponseFunction().apply(skillService.findAllByInstrument(instrumentId).orElseThrow(NotFoundException::new));
    }

    @Override
    public GetSkillResponse getSkill(UUID id) {
        return dtoFunctionFactory.skillToResponseFunction().apply(skillService.find(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public void create(UUID id, PutSkillRequest request, UUID instrumentId) {
        try {
            skillService.create(dtoFunctionFactory.requestToSkillFunction().apply(id, request, instrumentId));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(SkillController.class, "getSkill")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (TransactionalException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(java.util.logging.Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }
    }

    @Override
    public void update(UUID id, PatchSkillRequest request) {
        skillService.find(id).ifPresentOrElse(
                entity -> skillService.update(dtoFunctionFactory.updateSkillWithRequestFunction().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void delete(UUID id) {
        skillService.find(id).ifPresentOrElse(
                entity -> skillService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
