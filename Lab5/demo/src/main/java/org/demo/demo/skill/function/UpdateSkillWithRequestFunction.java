package org.demo.demo.skill.function;

import org.demo.demo.skill.dto.PatchSkillRequest;
import org.demo.demo.skill.entity.Skill;

import java.util.function.BiFunction;

public class UpdateSkillWithRequestFunction implements BiFunction<Skill, PatchSkillRequest, Skill> {
    @Override
    public Skill apply(Skill skill, PatchSkillRequest patchSkillRequest) {
        return Skill.builder()
                .id(skill.getId())
                .level(patchSkillRequest.getLevel())
                .numberOfPlayingYears(patchSkillRequest.getNumberOfPlayingYears())
                .favouriteModelName(patchSkillRequest.getFavouriteModelName())
                //.musician(skill.getMusician())
                .instrument(skill.getInstrument())
                .build();
    }
}
