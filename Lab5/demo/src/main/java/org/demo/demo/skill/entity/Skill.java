package org.demo.demo.skill.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "skills")
public class Skill implements Serializable {
    @Id
    private UUID id;

    @Column(name = "number_of_playing_years")
    private Integer numberOfPlayingYears;

    private Level level;

    @Column(name = "favourite_model_name")
    private String favouriteModelName;

    /*@ManyToOne
    @JoinColumn(name = "musician")
    private Musician musician;*/

    @ManyToOne
    @JoinColumn(name = "instrument_id")
    private Instrument instrument;
}
