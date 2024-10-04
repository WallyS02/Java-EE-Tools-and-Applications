package org.demo.demo.instrument.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import org.demo.demo.component.ModelFunctionFactory;
import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.instrument.model.InstrumentModel;
import org.demo.demo.instrument.service.InstrumentService;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(forClass = InstrumentModel.class, managed = true)
public class InstrumentModelConverter implements Converter<InstrumentModel> {
    private final InstrumentService instrumentService;

    private final ModelFunctionFactory modelFunctionFactory;

    @Inject
    public InstrumentModelConverter(InstrumentService instrumentService, ModelFunctionFactory modelFunctionFactory) {
        this.instrumentService = instrumentService;
        this.modelFunctionFactory = modelFunctionFactory;
    }

    @Override
    public InstrumentModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Instrument> instrument = instrumentService.find(UUID.fromString(value));
        return instrument.map(modelFunctionFactory.instrumentToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, InstrumentModel value) {
        return value == null ? "" : value.getId().toString();
    }
}
