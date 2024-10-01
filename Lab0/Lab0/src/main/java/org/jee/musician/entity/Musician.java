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
    private String wholeName;
    private LocalDate birthday;
    private List<Skill> skills;
}
