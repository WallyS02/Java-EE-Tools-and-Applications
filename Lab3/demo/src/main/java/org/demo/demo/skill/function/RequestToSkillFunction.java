package org.demo.demo.skill.function;

import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.musician.entity.Musician;
import org.demo.demo.skill.dto.PutSkillRequest;
import org.demo.demo.skill.entity.Skill;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToSkillFunction implements BiFunction<UUID, PutSkillRequest, Skill> {
    @Override
    public Skill apply(UUID uuid, PutSkillRequest putSkillRequest) {
        return Skill.builder()
                .id(uuid)
                .level(putSkillRequest.getLevel())
                .favouriteModelName(putSkillRequest.getFavouriteModelName())
                .numberOfPlayingYears(putSkillRequest.getNumberOfPlayingYears())
                .musician(Musician.builder()
                        .id(putSkillRequest.getMusician())
                        .build())
                .instrument(Instrument.builder()
                        .id(putSkillRequest.getInstrument())
                        .build())
                .build();
    }
}
