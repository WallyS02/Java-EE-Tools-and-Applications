package org.demo.demo.skill.dto;

import lombok.*;
import org.demo.demo.util.Level;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetSkillsResponse {
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
    private List<GetSkillsResponse.Skill> skills;
}
