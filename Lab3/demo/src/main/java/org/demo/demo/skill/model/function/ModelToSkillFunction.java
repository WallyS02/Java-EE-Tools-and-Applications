package org.demo.demo.skill.model.function;

import lombok.SneakyThrows;
import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.skill.entity.Skill;
import org.demo.demo.skill.model.SkillCreateModel;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToSkillFunction implements Function<SkillCreateModel, Skill>, Serializable {

    @Override
    @SneakyThrows
    public Skill apply(SkillCreateModel skillCreateModel) {
        return Skill.builder()
                .id(skillCreateModel.getId())
                .level(skillCreateModel.getLevel())
                .favouriteModelName(skillCreateModel.getFavouriteModelName())
                .numberOfPlayingYears(skillCreateModel.getNumberOfPlayingYears())
                .instrument(Instrument.builder()
                        .id(skillCreateModel.getInstrument().getId())
                        .build())
                .build();
    }
}
