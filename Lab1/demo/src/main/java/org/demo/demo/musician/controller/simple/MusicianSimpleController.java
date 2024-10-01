package org.demo.demo.musician.controller.simple;

import org.demo.demo.controller.servlet.exception.NotFoundException;
import org.demo.demo.controller.servlet.exception.BadRequestException;
import org.demo.demo.component.DtoFunctionFactory;
import org.demo.demo.musician.controller.api.MusicianController;
import org.demo.demo.musician.dto.*;
import org.demo.demo.musician.service.MusicianService;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class MusicianSimpleController implements MusicianController {
    private final MusicianService musicianService;

    private final DtoFunctionFactory dtoFunctionFactory;

    public MusicianSimpleController(MusicianService musicianService, DtoFunctionFactory dtoFunctionFactory) {
        this.musicianService = musicianService;
        this.dtoFunctionFactory = dtoFunctionFactory;
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
    public GetMusiciansResponse getMusiciansByEmail(String email) {
        return dtoFunctionFactory.musiciansToResponseFunction().apply(musicianService.findByEmail(email));
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
    public void create(UUID id, PutMusicianRequest request) {
        try {
            musicianService.create(dtoFunctionFactory.requestToMusicianFunction().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
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
