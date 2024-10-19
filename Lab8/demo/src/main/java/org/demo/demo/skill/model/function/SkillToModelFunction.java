package org.demo.demo.skill.model.function;

import lombok.SneakyThrows;
import org.demo.demo.skill.entity.Skill;
import org.demo.demo.skill.model.SkillModel;

import java.io.Serializable;
import java.util.function.Function;

public class SkillToModelFunction implements Function<Skill, SkillModel>, Serializable {
    @Override
    @SneakyThrows
    public SkillModel apply(Skill skill) {
        return SkillModel.builder()
                .id(skill.getId())
                .level(skill.getLevel())
                .favouriteModelName(skill.getFavouriteModelName())
                .numberOfPlayingYears(skill.getNumberOfPlayingYears())
                .version(skill.getVersion())
                .creationDateTime(skill.getCreationDateTime())
                .updateDateTime(skill.getUpdateDateTime())
                .instrument(skill.getInstrument().getName())
                .build();
    }
}
