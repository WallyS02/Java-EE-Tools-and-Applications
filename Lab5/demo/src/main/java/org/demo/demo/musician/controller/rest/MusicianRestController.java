package org.demo.demo.musician.controller.rest;

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
    private final MusicianService musicianService;

    private final DtoFunctionFactory dtoFunctionFactory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public MusicianRestController(MusicianService musicianService, DtoFunctionFactory dtoFunctionFactory, UriInfo uriInfo) {
        this.musicianService = musicianService;
        this.dtoFunctionFactory = dtoFunctionFactory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetMusiciansResponse getMusicians() {
        return dtoFunctionFactory.musiciansToResponseFunction().apply(musicianService.findAll());
    }

    @Override
    public GetMusicianResponse getMusician(UUID id) {
        return dtoFunctionFactory.musicianToResponseFunction().apply(musicianService.find(id).orElseThrow(NotFoundException::new));
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
        } catch (TransactionalException ex) {
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
                entity -> musicianService.update(dtoFunctionFactory.updateMusicianWithRequestFunction().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void updatePassword(UUID id, PutPasswordRequest request) {
        musicianService.find(id).ifPresentOrElse(
                entity -> musicianService.update(dtoFunctionFactory.updateMusicianPasswordRequestFunction().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void delete(UUID id) {
        musicianService.find(id).ifPresentOrElse(
                entity -> musicianService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public byte[] getImage(UUID id) throws IOException {
        return musicianService.getImage(id);
    }

    @Override
    public void createImage(UUID id, InputStream image) {
        musicianService.find(id).ifPresentOrElse(
                entity -> musicianService.updateImage(id, image),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void updateImage(UUID id, InputStream image) {
        musicianService.find(id).ifPresentOrElse(
                entity -> musicianService.updateImage(id, image),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteImage(UUID id) {
        musicianService.find(id).ifPresentOrElse(
                entity -> musicianService.deleteImage(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
