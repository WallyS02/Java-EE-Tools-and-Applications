package org.demo.demo.skill.model;

import lombok.*;
import org.demo.demo.util.Level;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class SkillsModel implements Serializable {
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
    private List<Skill> skills;
}
