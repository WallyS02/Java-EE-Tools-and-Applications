package org.jee.musician.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jee.skill.entity.Skill;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Musician {
    private String login;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private int albumsReleased;
    private List<Skill> skills;
}
