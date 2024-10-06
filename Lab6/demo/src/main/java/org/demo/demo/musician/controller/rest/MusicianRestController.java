package org.demo.demo.musician.controller.rest;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.demo.demo.component.DtoFunctionFactory;
import org.demo.demo.musician.controller.api.MusicianController;
import org.demo.demo.musician.dto.*;
import org.demo.demo.musician.service.MusicianService;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class MusicianRestController implements MusicianController {
    private MusicianService musicianService;

    private final DtoFunctionFactory dtoFunctionFactory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public MusicianRestController(DtoFunctionFactory dtoFunctionFactory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.dtoFunctionFactory = dtoFunctionFactory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setMusicianService(MusicianService musicianService) {
        this.musicianService = musicianService;
    }

    @Override
    public GetMusiciansResponse getMusicians() {
        return dtoFunctionFactory.musiciansToResponseFunction().apply(musicianService.findAll());
    }

    @Override
    public GetMusicianResponse getMusician(UUID id) {
        try {
            return dtoFunctionFactory.musicianToResponseFunction().apply(musicianService.find(id).orElseThrow(NotFoundException::new));
        } catch (EJBAccessException ex) {
            log.log(java.util.logging.Level.WARNING, ex.getMessage(), ex);
            throw new ForbiddenException(ex.getMessage());
        }
    }

    @Override
    public GetMusicianResponse getMusicianByLogin(String login) {
        return dtoFunctionFactory.musicianToResponseFunction().apply(musicianService.findByLogin(login).orElseThrow(NotFoundException::new));
    }

    @Override
    public GetMusicianResponse getMusiciansByEmail(String email) {
        return dtoFunctionFactory.musicianToResponseFunction().apply(musicianService.findByEmail(email).orElseThrow(NotFoundException::new));
    }

    @Override
    public GetMusiciansResponse getMusiciansByFirstName(String firstName) {
        return dtoFunctionFactory.musiciansToResponseFunction().apply(musicianService.findByFirstName(firstName));
    }

    @Override
    public GetMusiciansResponse getMusiciansByLastName(String lastName) {
        return dtoFunctionFactory.musiciansToResponseFunction().apply(musicianService.findByLastName(lastName));
    }

    @Override
    @SneakyThrows
    public void create(UUID id, PutMusicianRequest request) {
        try {
            musicianService.create(dtoFunctionFactory.requestToMusicianFunction().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(MusicianController.class, "getMusician")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }
    }

    @Override
    public void update(UUID id, PatchMusicianRequest request) {
        musicianService.find(id).ifPresentOrElse(
                entity -> {
                    try {
                        musicianService.update(dtoFunctionFactory.updateMusicianWithRequestFunction().apply(entity, request));
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
    public void updatePassword(UUID id, PutPasswordRequest request) {
        musicianService.find(id).ifPresentOrElse(
                entity -> {
                    try {
                        musicianService.update(dtoFunctionFactory.updateMusicianPasswordRequestFunction().apply(entity, request));
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
        musicianService.find(id).ifPresentOrElse(
                entity -> {
                    try {
                        musicianService.delete(id);
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
    public byte[] getImage(UUID id) throws IOException {
        try {
            return musicianService.getImage(id);
        } catch (EJBAccessException ex) {
            log.log(java.util.logging.Level.WARNING, ex.getMessage(), ex);
            throw new ForbiddenException(ex.getMessage());
        }
    }

    @Override
    public void createImage(UUID id, InputStream image) {
        musicianService.find(id).ifPresentOrElse(
                entity -> {
                    try {
                        musicianService.updateImage(id, image);
                    } catch (EJBAccessException ex) {
                        log.log(java.util.logging.Level.WARNING, ex.getMessage(), ex);
                        throw new ForbiddenException(ex.getMessage());
                    }
                    response.setHeader("Location", uriInfo.getBaseUriBuilder()
                            .path(MusicianController.class, "getImage")
                            .build(id)
                            .toString());
                    throw new WebApplicationException(Response.Status.CREATED);
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void updateImage(UUID id, InputStream image) {
        musicianService.find(id).ifPresentOrElse(
                entity -> {
                    try {
                        musicianService.updateImage(id, image);
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
    public void deleteImage(UUID id) {
        musicianService.find(id).ifPresentOrElse(
                entity -> {
                    try {
                        musicianService.deleteImage(id);
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
