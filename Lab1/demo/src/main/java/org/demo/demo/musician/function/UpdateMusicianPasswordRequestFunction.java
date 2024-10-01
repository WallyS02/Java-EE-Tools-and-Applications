package org.demo.demo.musician.function;

import org.demo.demo.musician.dto.PutPasswordRequest;
import org.demo.demo.musician.entity.Musician;

import java.util.function.BiFunction;

public class UpdateMusicianPasswordRequestFunction implements BiFunction<Musician, PutPasswordRequest, Musician> {
    @Override
    public Musician apply(Musician musician, PutPasswordRequest putPasswordRequest) {
        return Musician.builder()
                .id(musician.getId())
                .login(musician.getLogin())
                .email(musician.getEmail())
                .birthday(musician.getBirthday())
                .firstName(musician.getFirstName())
                .lastName(musician.getLastName())
                .password(putPasswordRequest.getPassword())
                .skills(musician.getSkills())
                .build();
    }
}
