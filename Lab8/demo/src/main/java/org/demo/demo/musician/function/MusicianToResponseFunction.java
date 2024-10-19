package org.demo.demo.musician.function;

import org.demo.demo.musician.dto.GetMusicianResponse;
import org.demo.demo.musician.entity.Musician;

import java.util.function.Function;

public class MusicianToResponseFunction implements Function<Musician, GetMusicianResponse> {
    @Override
    public GetMusicianResponse apply(Musician musician) {
        return GetMusicianResponse.builder()
                .id(musician.getId())
                .firstName(musician.getFirstName())
                .lastName(musician.getLastName())
                .login(musician.getLogin())
                .albumsReleased(musician.getAlbumsReleased())
                .email(musician.getEmail())
                .birthday(musician.getBirthday())
                .skills(musician.getSkills().stream()
                        .map(skill -> GetMusicianResponse.Skill.builder()
                                .id(skill.getId())
                                .level(skill.getLevel())
                                .build())
                        .toList())
                .build();
    }
}
