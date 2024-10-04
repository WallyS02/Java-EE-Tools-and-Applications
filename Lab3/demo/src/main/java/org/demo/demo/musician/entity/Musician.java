package org.demo.demo.musician.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.demo.demo.skill.entity.Skill;

import java.io.Serializable;
import java.time.LocalDate;
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
public class Musician implements Serializable {
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String login;
    private int albumsReleased;
    @ToString.Exclude
    private String password;
    private String email;
    @Builder.Default
    private List<Skill> skills = new ArrayList<>();
}
