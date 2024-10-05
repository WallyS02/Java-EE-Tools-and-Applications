package org.demo.demo.instrument.controller.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.demo.demo.component.DtoFunctionFactory;
import org.demo.demo.instrument.controller.api.InstrumentController;
import org.demo.demo.instrument.dto.GetInstrumentResponse;
import org.demo.demo.instrument.dto.GetInstrumentsResponse;
import org.demo.demo.instrument.dto.PatchInstrumentRequest;
import org.demo.demo.instrument.dto.PutInstrumentRequest;
import org.demo.demo.instrument.service.InstrumentService;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class InstrumentRestController implements InstrumentController {
    private final InstrumentService instrumentService;

    private final DtoFunctionFactory dtoFunctionFactory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public InstrumentRestController(InstrumentService instrumentService, DtoFunctionFactory dtoFunctionFactory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.instrumentService = instrumentService;
        this.dtoFunctionFactory = dtoFunctionFactory;
        this.uriInfo = uriInfo;
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
    @SneakyThrows
    public void create(UUID id, PutInstrumentRequest request) {
        try {
            instrumentService.create(dtoFunctionFactory.requestToInstrumentFunction().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(InstrumentController.class, "getInstrument")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (TransactionalException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
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
