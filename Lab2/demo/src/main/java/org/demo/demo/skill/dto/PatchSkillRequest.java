package org.demo.demo.skill.dto;

import lombok.*;
import org.demo.demo.util.Level;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchSkillRequest {
    private int numberOfPlayingYears;
    private Level level;
    private String favouriteModelName;
}
