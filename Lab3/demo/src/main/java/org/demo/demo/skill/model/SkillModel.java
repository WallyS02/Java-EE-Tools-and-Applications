package org.demo.demo.skill.model;

import lombok.*;
import org.demo.demo.util.Level;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class SkillModel implements Serializable {
    private UUID id;
    private int numberOfPlayingYears;
    private Level level;
    private String favouriteModelName;
    private String instrument;
}
