package org.demo.demo.skill.model.function;

import lombok.SneakyThrows;
import org.demo.demo.skill.entity.Skill;
import org.demo.demo.skill.model.SkillEditModel;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateSkillWithModelFunction implements BiFunction<Skill, SkillEditModel, Skill>, Serializable {

    @Override
    @SneakyThrows
    public Skill apply(Skill skill, SkillEditModel skillEditModel) {
        return Skill.builder()
                .id(skill.getId())
                .level(skillEditModel.getLevel())
                .numberOfPlayingYears(skillEditModel.getNumberOfPlayingYears())
                .favouriteModelName(skillEditModel.getFavouriteModelName())
                .musician(skill.getMusician())
                .instrument(skill.getInstrument())
                .build();
    }
}
