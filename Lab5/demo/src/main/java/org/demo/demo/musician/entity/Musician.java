package org.demo.demo.musician.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.demo.demo.skill.entity.Skill;

import java.io.Serializable;
import java.time.LocalDate;
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
@Table(name = "musicians")
public class Musician implements Serializable {
    @Id
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private LocalDate birthday;

    @Column(unique = true)
    private String login;

    @Column(name = "albums_released")
    private int albumsReleased;

    @ToString.Exclude
    private String password;

    @Column(unique = true)
    private String email;

    /*@ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "skill", cascade = CascadeType.REMOVE)
    private List<Skill> skills;*/
}
