package org.demo.demo.musician.function;

import org.demo.demo.musician.dto.PatchMusicianRequest;
import org.demo.demo.musician.entity.Musician;

import java.util.function.BiFunction;

public class UpdateMusicianWithRequestFunction implements BiFunction<Musician, PatchMusicianRequest, Musician> {
    @Override
    public Musician apply(Musician musician, PatchMusicianRequest patchMusicianRequest) {
        return Musician.builder()
                .id(musician.getId())
                .login(patchMusicianRequest.getLogin())
                .email(patchMusicianRequest.getEmail())
                .birthday(patchMusicianRequest.getBirthday())
                .firstName(patchMusicianRequest.getFirstName())
                .lastName(patchMusicianRequest.getLastName())
                .albumsReleased(patchMusicianRequest.getAlbumsReleased())
                .password(musician.getPassword())
                .skills(musician.getSkills())
                .build();
    }
}
