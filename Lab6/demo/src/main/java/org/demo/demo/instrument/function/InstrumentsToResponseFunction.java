package org.demo.demo.instrument.function;

import org.demo.demo.instrument.dto.GetInstrumentsResponse;
import org.demo.demo.instrument.entity.Instrument;

import java.util.List;
import java.util.function.Function;

public class InstrumentsToResponseFunction implements Function<List<Instrument>, GetInstrumentsResponse> {
    @Override
    public GetInstrumentsResponse apply(List<Instrument> instruments) {
        return GetInstrumentsResponse.builder()
                .instruments(instruments.stream()
                        .map(instrument -> GetInstrumentsResponse.Instrument.builder()
                                .id(instrument.getId())
                                .name(instrument.getName())
                                .build())
                        .toList())
                .build();
    }
}
