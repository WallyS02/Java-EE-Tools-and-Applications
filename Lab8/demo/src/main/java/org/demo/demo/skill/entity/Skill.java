package org.demo.demo.skill.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.musician.entity.Musician;
import org.demo.demo.skill.validation.annotation.ValidPlayingYearsProportionality;
import org.demo.demo.util.Level;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@ValidPlayingYearsProportionality
public class Skill implements Serializable {
    @Id
    private UUID id;

    @Column(name = "number_of_playing_years")
    @NotNull
    private Integer numberOfPlayingYears;

    private Level level;

    @Column(name = "favourite_model_name")
    @NotBlank
    private String favouriteModelName;

    @Version
    private int version;

    @Column(name = "creation_date_time", updatable = false)
    private LocalDateTime creationDateTime;

    @Column(name = "update_date_time")
    private LocalDateTime updateDateTime;

    @PrePersist
    protected void onCreate() {
        creationDateTime = LocalDateTime.now();
        updateDateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateDateTime = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "musician_id")
    private Musician musician;

    @ManyToOne
    @JoinColumn(name = "instrument_id")
    private Instrument instrument;
}
