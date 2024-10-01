package org.demo.demo.musician.dto;

import lombok.*;
import org.demo.demo.util.Level;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetMusicianResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String login;
    private String email;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Skill {
        private UUID id;
        private Level level;
    }

    @Singular
    private List<GetMusicianResponse.Skill> skills;
}
