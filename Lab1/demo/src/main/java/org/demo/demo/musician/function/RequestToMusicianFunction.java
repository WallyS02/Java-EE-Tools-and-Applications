package org.demo.demo.musician.function;

import org.demo.demo.musician.dto.PutMusicianRequest;
import org.demo.demo.musician.entity.Musician;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToMusicianFunction implements BiFunction<UUID, PutMusicianRequest, Musician> {

    @Override
    public Musician apply(UUID uuid, PutMusicianRequest putMusicianRequest) {
        return Musician.builder()
                .id(uuid)
                .login(putMusicianRequest.getLogin())
                .email(putMusicianRequest.getEmail())
                .birthday(putMusicianRequest.getBirthday())
                .firstName(putMusicianRequest.getFirstName())
                .lastName(putMusicianRequest.getLastName())
                .password(putMusicianRequest.getPassword())
                .build();
    }
}
