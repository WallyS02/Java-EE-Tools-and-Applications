package org.demo.demo.skill.model.function;

import lombok.SneakyThrows;
import org.demo.demo.skill.entity.Skill;
import org.demo.demo.skill.model.SkillEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class SkillToEditModelFunction implements Function<Skill, SkillEditModel>, Serializable {

    @Override
    @SneakyThrows
    public SkillEditModel apply(Skill skill) {
        return SkillEditModel.builder()
                .level(skill.getLevel())
                .favouriteModelName(skill.getFavouriteModelName())
                .numberOfPlayingYears(skill.getNumberOfPlayingYears())
                .build();
    }
}
