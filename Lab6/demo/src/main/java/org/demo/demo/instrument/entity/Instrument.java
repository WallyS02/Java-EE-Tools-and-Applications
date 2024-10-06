package org.demo.demo.instrument.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.demo.demo.skill.entity.Skill;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "instruments")
public class Instrument implements Serializable {
    @Id
    private UUID id;

    @Column(unique = true)
    private String name;

    private String type;

    @Column(name = "typical_price")
    private int typicalPrice;

    @Column(name = "tuning_frequency")
    private float tuningFrequency;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "instrument", cascade = CascadeType.REMOVE)
    private List<Skill> skills;
}
