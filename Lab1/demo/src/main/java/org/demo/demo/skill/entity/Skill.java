package org.demo.demo.skill.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.musician.entity.Musician;
import org.demo.demo.util.Level;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Skill implements Serializable {
    private UUID id;
    private int numberOfPlayingYears;
    private Level level;
    private String favouriteModelName;
    private Musician musician;
    private Instrument instrument;
}
