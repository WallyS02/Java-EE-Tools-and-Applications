package org.demo.demo.instrument.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.demo.demo.component.ModelFunctionFactory;
import org.demo.demo.instrument.model.InstrumentsModel;
import org.demo.demo.instrument.service.InstrumentService;

@RequestScoped
@Named
public class InstrumentList {
    private InstrumentService instrumentService;

    private InstrumentsModel instruments;

    private final ModelFunctionFactory modelFunctionFactory;

    @Inject
    public InstrumentList(ModelFunctionFactory modelFunctionFactory) {
        this.modelFunctionFactory = modelFunctionFactory;
    }

    @EJB
    public void setInstrumentService(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    public InstrumentsModel getInstruments() {
        if(instruments == null) {
            instruments = modelFunctionFactory.instrumentsToModel().apply(instrumentService.findAll());
        }
        return instruments;
    }

    public void deleteInstrument(InstrumentsModel.Instrument instrument) {
        instrumentService.delete(instrument.getId());
        this.instruments = modelFunctionFactory.instrumentsToModel().apply(instrumentService.findAll());
    }
}
