package org.demo.demo.instrument.model.function;

import lombok.SneakyThrows;
import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.instrument.model.InstrumentsModel;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

public class InstrumentsToModelFunction implements Function<List<Instrument>, InstrumentsModel>, Serializable {

    @Override
    @SneakyThrows
    public InstrumentsModel apply(List<Instrument> instruments) {
        return InstrumentsModel.builder()
                .instruments(instruments.stream()
                        .map(instrument -> InstrumentsModel.Instrument.builder()
                                .id(instrument.getId())
                                .name(instrument.getName())
                                .build())
                        .toList())
                .build();
    }
}
