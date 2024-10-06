package org.demo.demo.skill.dto;

import lombok.*;
import org.demo.demo.util.Level;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetSkillResponse {
    private UUID id;
    private int numberOfPlayingYears;
    private Level level;
    private String favouriteModelName;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Musician {
        private UUID id;
        private String login;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Instrument {
        private UUID id;
        private String name;
    }

    private GetSkillResponse.Musician musician;
    private GetSkillResponse.Instrument instrument;
}
