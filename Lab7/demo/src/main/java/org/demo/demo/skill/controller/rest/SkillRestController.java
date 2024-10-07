package org.demo.demo.skill.controller.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;
import org.demo.demo.component.DtoFunctionFactory;
import org.demo.demo.musician.entity.MusicianRoles;
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
@RolesAllowed(MusicianRoles.USER)
public class SkillRestController implements SkillController {
    private SkillService skillService;

    private final DtoFunctionFactory dtoFunctionFactory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public SkillRestController(DtoFunctionFactory dtoFunctionFactory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.dtoFunctionFactory = dtoFunctionFactory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setSkillService(SkillService skillService) {
        this.skillService = skillService;
    }

    @Override
    public GetSkillsResponse getSkills() {
        return dtoFunctionFactory.skillsToResponseFunction().apply(skillService.findAllForCallerPrincipal());
    }

    @Override
    public GetSkillsResponse getSkillsByLevel(Level level) {
        return dtoFunctionFactory.skillsToResponseFunction().apply(skillService.findAllByLevel(level));
    }

    @Override
    public GetSkillsResponse getSkillsByMusician(UUID musicianId) {
        return dtoFunctionFactory.skillsToResponseFunction().apply(skillService.findAllByMusician(musicianId).orElseThrow(NotFoundException::new));
    }

    @Override
    public GetSkillsResponse getSkillsByInstrument(UUID instrumentId) {
        return dtoFunctionFactory.skillsToResponseFunction().apply(skillService.findAllByInstrument(instrumentId).orElseThrow(NotFoundException::new));
    }

    @Override
    public GetSkillResponse getSkill(UUID id) {
        return dtoFunctionFactory.skillToResponseFunction().apply(skillService.findForCallerPrincipal(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public void create(UUID id, PutSkillRequest request, UUID instrumentId) {
        try {
            skillService.createForCallerPrincipal(dtoFunctionFactory.requestToSkillFunction().apply(id, request, instrumentId));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(SkillController.class, "getSkill")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (EJBException ex) {
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
                entity -> {
                    try {
                        skillService.update(dtoFunctionFactory.updateSkillWithRequestFunction().apply(entity, request));
                    } catch (EJBAccessException ex) {
                        log.log(java.util.logging.Level.WARNING, ex.getMessage(), ex);
                        throw new ForbiddenException(ex.getMessage());
                    }
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void delete(UUID id) {
        skillService.find(id).ifPresentOrElse(
                entity -> {
                    try {
                        skillService.delete(id);
                    } catch (EJBAccessException ex) {
                        log.log(java.util.logging.Level.WARNING, ex.getMessage(), ex);
                        throw new ForbiddenException(ex.getMessage());
                    }
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
