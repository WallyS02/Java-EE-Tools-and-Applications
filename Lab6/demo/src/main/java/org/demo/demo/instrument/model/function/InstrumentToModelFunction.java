package org.demo.demo.instrument.model.function;

import lombok.SneakyThrows;
import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.instrument.model.InstrumentModel;

import java.io.Serializable;
import java.util.function.Function;

public class InstrumentToModelFunction implements Function<Instrument, InstrumentModel>, Serializable {

    @Override
    @SneakyThrows
    public InstrumentModel apply(Instrument instrument) {
        return InstrumentModel.builder()
                .id(instrument.getId())
                .name(instrument.getName())
                .type(instrument.getType())
                .typicalPrice(instrument.getTypicalPrice())
                .tuningFrequency(instrument.getTuningFrequency())
                .build();
    }
}
