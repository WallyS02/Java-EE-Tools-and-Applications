package org.demo.demo.instrument.controller.simple;

import org.demo.demo.controller.servlet.exception.NotFoundException;
import org.demo.demo.controller.servlet.exception.BadRequestException;
import org.demo.demo.component.DtoFunctionFactory;
import org.demo.demo.instrument.controller.api.InstrumentController;
import org.demo.demo.instrument.dto.GetInstrumentResponse;
import org.demo.demo.instrument.dto.GetInstrumentsResponse;
import org.demo.demo.instrument.dto.PatchInstrumentRequest;
import org.demo.demo.instrument.dto.PutInstrumentRequest;
import org.demo.demo.instrument.service.InstrumentService;

import java.util.UUID;

public class InstrumentSimpleController implements InstrumentController {
    private final InstrumentService instrumentService;

    private final DtoFunctionFactory dtoFunctionFactory;

    public InstrumentSimpleController(InstrumentService instrumentService, DtoFunctionFactory dtoFunctionFactory) {
        this.instrumentService = instrumentService;
        this.dtoFunctionFactory = dtoFunctionFactory;
    }

    @Override
    public GetInstrumentsResponse getInstruments() {
        return dtoFunctionFactory.instrumentsToResponseFunction().apply(instrumentService.findAll());
    }

    @Override
    public GetInstrumentsResponse getInstrumentsByName(String name) {
        return dtoFunctionFactory.instrumentsToResponseFunction().apply(instrumentService.findAllByName(name));
    }

    @Override
    public GetInstrumentsResponse getInstrumentsByType(String type) {
        return dtoFunctionFactory.instrumentsToResponseFunction().apply(instrumentService.findAllByType(type));
    }

    @Override
    public GetInstrumentResponse getInstrument(UUID id) {
        return dtoFunctionFactory.instrumentToResponseFunction().apply(instrumentService.find(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public void create(UUID id, PutInstrumentRequest request) {
        try {
            instrumentService.create(dtoFunctionFactory.requestToInstrumentFunction().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void update(UUID id, PatchInstrumentRequest request) {
        instrumentService.find(id).ifPresentOrElse(
                entity -> instrumentService.update(dtoFunctionFactory.updateInstrumentWithRequestFunction().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void delete(UUID id) {
        instrumentService.find(id).ifPresentOrElse(
                entity -> instrumentService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
