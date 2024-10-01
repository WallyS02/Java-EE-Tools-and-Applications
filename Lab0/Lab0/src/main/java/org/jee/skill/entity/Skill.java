package org.jee.skill.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jee.instrument.entity.Instrument;
import org.jee.musician.entity.Musician;
import org.jee.util.Level;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Skill {
    private int numberOfPlayingYears;
    private Level level;
    private String favouriteModelName;
    private Musician musician;
    private Instrument instrument;
}
