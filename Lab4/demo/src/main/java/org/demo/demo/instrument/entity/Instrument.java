package org.demo.demo.instrument.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.demo.demo.skill.entity.Skill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Instrument implements Serializable {
    private UUID id;
    private String name;
    private String type;
    private int typicalPrice;
    private float tuningFrequency;
    @Builder.Default
    private List<Skill> skills = new ArrayList<>();
}
