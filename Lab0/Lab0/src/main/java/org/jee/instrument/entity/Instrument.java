package org.jee.instrument.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jee.skill.entity.Skill;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Instrument {
    private String name;
    private String type;
    private int typicalPrice;
    private float tuningFrequency;
    private List<Skill> skills;
}
