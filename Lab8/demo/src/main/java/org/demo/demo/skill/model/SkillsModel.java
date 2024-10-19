package org.demo.demo.skill.model;

import lombok.*;
import org.demo.demo.util.Level;

import java.io.Serializable;
import java.time.LocalDateTime;
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
        private int version;
        private LocalDateTime creationDateTime;
        private LocalDateTime updateDateTime;
    }

    @Singular
    private List<Skill> skills;
}
