package org.demo.demo.musician.controller.api;

import org.demo.demo.musician.dto.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public interface MusicianController {
    GetMusiciansResponse getMusicians();
    GetMusicianResponse getMusician(UUID id);
    GetMusicianResponse getMusicianByLogin(String login);
    GetMusiciansResponse getMusiciansByEmail(String email);
    GetMusiciansResponse getMusiciansByFirstName(String firstName);
    GetMusiciansResponse getMusiciansByLastName(String lastName);
    void create(UUID id, PutMusicianRequest request);
    void update(UUID id, PatchMusicianRequest request);
    void updatePassword(UUID id, PutPasswordRequest request);
    void delete(UUID id);
    byte[] getImage(UUID id) throws IOException;
    void updateImage(UUID id, InputStream image);
    void deleteImage(UUID id);
}
