package org.demo.demo.skill.model;

import lombok.*;
import org.demo.demo.util.Level;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class SkillEditModel implements Serializable {
    private int numberOfPlayingYears;
    private Level level;
    private String favouriteModelName;
}
