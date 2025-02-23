package org.demo.demo.skill.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.demo.demo.component.ModelFunctionFactory;
import org.demo.demo.instrument.model.InstrumentModel;
import org.demo.demo.instrument.service.InstrumentService;
import org.demo.demo.skill.model.SkillCreateModel;
import org.demo.demo.skill.service.SkillService;
import org.demo.demo.util.Level;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ViewScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class SkillCreate implements Serializable {
    private final SkillService skillService;

    private final InstrumentService instrumentService;

    private final ModelFunctionFactory modelFunctionFactory;

    @Getter
    private SkillCreateModel skill;

    @Getter
    private List<InstrumentModel> instruments;

    @Inject
    public SkillCreate(InstrumentService instrumentService, SkillService skillService, ModelFunctionFactory modelFunctionFactory) {
        this.instrumentService = instrumentService;
        this.skillService = skillService;
        this.modelFunctionFactory = modelFunctionFactory;
    }

    public void init() {
        instruments = instrumentService.findAll().stream()
                .map(modelFunctionFactory.instrumentToModel())
                .toList();
        skill = SkillCreateModel.builder()
                .id(UUID.randomUUID())
                .build();
    }

    public String cancel() {
        return "/instrument/instrument_list.xhtml?faces-redirect=true";
    }

    public String save() {
        skillService.create(modelFunctionFactory.modelToSkill().apply(skill));
        return "/instrument/instrument_list.xhtml?faces-redirect=true";
    }

    public Level[] getLevels() {
        return Level.values();
    }
}
