package org.demo.demo.instrument.function;

import org.demo.demo.instrument.dto.PatchInstrumentRequest;
import org.demo.demo.instrument.entity.Instrument;

import java.util.function.BiFunction;

public class UpdateInstrumentWithRequestFunction implements BiFunction<Instrument, PatchInstrumentRequest, Instrument> {
    @Override
    public Instrument apply(Instrument instrument, PatchInstrumentRequest patchInstrumentRequest) {
        return Instrument.builder()
                .id(instrument.getId())
                .name(patchInstrumentRequest.getName())
                .type(patchInstrumentRequest.getType())
                .description(patchInstrumentRequest.getDescription())
                .skills(instrument.getSkills())
                .build();
    }
}
