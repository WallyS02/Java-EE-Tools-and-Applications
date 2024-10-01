package org.demo.demo.instrument.function;

import org.demo.demo.instrument.dto.GetInstrumentResponse;
import org.demo.demo.instrument.entity.Instrument;

import java.util.function.Function;

public class InstrumentToResponseFunction implements Function<Instrument, GetInstrumentResponse> {
    @Override
    public GetInstrumentResponse apply(Instrument instrument) {
        return GetInstrumentResponse.builder()
                .id(instrument.getId())
                .name(instrument.getName())
                .type(instrument.getType())
                .description(instrument.getDescription())
                .skills(instrument.getSkills().stream()
                        .map(skill -> GetInstrumentResponse.Skill.builder()
                                .id(skill.getId())
                                .level(skill.getLevel())
                                .build())
                        .toList())
                .build();
    }
}
