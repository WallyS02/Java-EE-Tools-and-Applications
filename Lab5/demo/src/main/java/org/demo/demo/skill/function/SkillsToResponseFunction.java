package org.demo.demo.skill.function;

import org.demo.demo.skill.dto.GetSkillsResponse;
import org.demo.demo.skill.entity.Skill;

import java.util.List;
import java.util.function.Function;

public class SkillsToResponseFunction implements Function<List<Skill>, GetSkillsResponse> {
    @Override
    public GetSkillsResponse apply(List<Skill> skills) {
        return GetSkillsResponse.builder()
                .skills(skills.stream()
                        .map(skill -> GetSkillsResponse.Skill.builder()
                                .id(skill.getId())
                                .level(skill.getLevel())
                                .build())
                        .toList())
                .build();
    }
}
