package org.demo.demo.skill.model.function;

import lombok.SneakyThrows;
import org.demo.demo.skill.entity.Skill;
import org.demo.demo.skill.model.SkillsModel;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

public class SkillsToModelFunction implements Function<List<Skill>, SkillsModel>, Serializable {
    @Override
    @SneakyThrows
    public SkillsModel apply(List<Skill> skills) {
        return SkillsModel.builder()
                .skills(skills.stream()
                        .map(skill -> SkillsModel.Skill.builder()
                                .id(skill.getId())
                                .level(skill.getLevel())
                                .version(skill.getVersion())
                                .creationDateTime(skill.getCreationDateTime())
                                .updateDateTime(skill.getUpdateDateTime())
                                .build())
                        .toList())
                .build();
    }
}
