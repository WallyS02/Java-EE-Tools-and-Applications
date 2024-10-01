package org.demo.demo.instrument.controller.api;

import org.demo.demo.instrument.dto.GetInstrumentResponse;
import org.demo.demo.instrument.dto.GetInstrumentsResponse;
import org.demo.demo.instrument.dto.PatchInstrumentRequest;
import org.demo.demo.instrument.dto.PutInstrumentRequest;

import java.util.UUID;

public interface InstrumentController {
    GetInstrumentsResponse getInstruments();
    GetInstrumentsResponse getInstrumentsByName(String name);
    GetInstrumentsResponse getInstrumentsByType(String type);
    GetInstrumentResponse getInstrument(UUID id);
    void create(UUID id, PutInstrumentRequest request);
    void update(UUID id, PatchInstrumentRequest request);
    void delete(UUID id);
}
