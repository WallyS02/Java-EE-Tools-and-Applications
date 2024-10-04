package org.demo.demo.skill.function;

import org.demo.demo.skill.dto.GetSkillResponse;
import org.demo.demo.skill.entity.Skill;

import java.util.function.Function;

public class SkillToResponseFunction implements Function<Skill, GetSkillResponse> {
    @Override
    public GetSkillResponse apply(Skill skill) {
        return GetSkillResponse.builder()
                .id(skill.getId())
                .level(skill.getLevel())
                .favouriteModelName(skill.getFavouriteModelName())
                .numberOfPlayingYears(skill.getNumberOfPlayingYears())
                /*.musician(GetSkillResponse.Musician.builder()
                        .id(skill.getMusician().getId())
                        .login(skill.getMusician().getLogin())
                        .build())*/
                .instrument(GetSkillResponse.Instrument.builder()
                        .id(skill.getInstrument().getId())
                        .name(skill.getInstrument().getName())
                        .build())
                .build();
    }
}
