package org.demo.demo.skill.model;

import lombok.*;
import org.demo.demo.util.Level;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class SkillModel implements Serializable {
    private UUID id;
    private int numberOfPlayingYears;
    private Level level;
    private int version;
    private LocalDateTime creationDateTime;
    private LocalDateTime updateDateTime;
    private String favouriteModelName;
    private String instrument;
}
