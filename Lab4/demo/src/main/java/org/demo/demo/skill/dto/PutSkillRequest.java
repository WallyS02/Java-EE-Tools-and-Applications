package org.demo.demo.skill.dto;

import lombok.*;
import org.demo.demo.util.Level;

//import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutSkillRequest {
    private int numberOfPlayingYears;
    private Level level;
    private String favouriteModelName;
    //private UUID musician;
    //private UUID instrument;
}
