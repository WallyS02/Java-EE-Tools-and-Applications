package org.demo.demo.instrument.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.demo.demo.component.ModelFunctionFactory;
import org.demo.demo.instrument.model.InstrumentsModel;
import org.demo.demo.instrument.service.InstrumentService;
import org.demo.demo.skill.entity.Skill;
import org.demo.demo.skill.service.SkillService;

import java.util.List;
import java.util.Optional;

@RequestScoped
@Named
public class InstrumentList {
    private final InstrumentService instrumentService;

    private final SkillService skillService;

    private InstrumentsModel instruments;

    private final ModelFunctionFactory modelFunctionFactory;

    @Inject
    public InstrumentList(InstrumentService instrumentService, SkillService skillService, ModelFunctionFactory modelFunctionFactory) {
        this.instrumentService = instrumentService;
        this.skillService = skillService;
        this.modelFunctionFactory = modelFunctionFactory;
    }

    public InstrumentsModel getInstruments() {
        if(instruments == null) {
            instruments = modelFunctionFactory.instrumentsToModel().apply(instrumentService.findAll());
        }
        return instruments;
    }

    public String deleteInstrument(InstrumentsModel.Instrument instrument) {
        Optional<List<Skill>> skills1 = skillService.findAllByInstrument(instrument.getId());
        if(skills1.isPresent()) {
            for(Skill skill : skills1.get()) {
                skillService.delete(skill.getId());
            }
        }
        instrumentService.delete(instrument.getId());
        return "instrument_list?faces-redirect=true";
    }
}
