package org.demo.demo.instrument.function;

import org.demo.demo.instrument.dto.PutInstrumentRequest;
import org.demo.demo.instrument.entity.Instrument;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToInstrumentFunction implements BiFunction<UUID, PutInstrumentRequest, Instrument> {
    @Override
    public Instrument apply(UUID uuid, PutInstrumentRequest putInstrumentRequest) {
        return Instrument.builder()
                .id(uuid)
                .name(putInstrumentRequest.getName())
                .type(putInstrumentRequest.getType())
                .description(putInstrumentRequest.getDescription())
                .build();
    }
}
