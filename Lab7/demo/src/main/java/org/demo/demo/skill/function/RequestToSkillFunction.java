package org.demo.demo.skill.function;

import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.musician.entity.Musician;
import org.demo.demo.skill.dto.PutSkillRequest;
import org.demo.demo.skill.entity.Skill;
import org.demo.demo.util.TriFunction;

import java.util.UUID;

public class RequestToSkillFunction implements TriFunction<UUID, PutSkillRequest, UUID, Skill> {
    @Override
    public Skill apply(UUID uuid, PutSkillRequest putSkillRequest, UUID instrumentId) {
        return Skill.builder()
                .id(uuid)
                .level(putSkillRequest.getLevel())
                .favouriteModelName(putSkillRequest.getFavouriteModelName())
                .numberOfPlayingYears(putSkillRequest.getNumberOfPlayingYears())
                /*.musician(Musician.builder()
                        .id(putSkillRequest.getMusician())
                        .build())*/
                .instrument(Instrument.builder()
                        .id(instrumentId)
                        .build())
                .build();
    }
}
