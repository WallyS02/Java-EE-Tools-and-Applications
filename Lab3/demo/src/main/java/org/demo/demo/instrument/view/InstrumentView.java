package org.demo.demo.instrument.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.demo.demo.component.ModelFunctionFactory;
import org.demo.demo.instrument.entity.Instrument;
import org.demo.demo.instrument.model.InstrumentModel;
import org.demo.demo.instrument.service.InstrumentService;
import org.demo.demo.skill.entity.Skill;
import org.demo.demo.skill.model.SkillsModel;
import org.demo.demo.skill.service.SkillService;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class InstrumentView implements Serializable {
    private final InstrumentService instrumentService;

    private final SkillService skillService;

    private final ModelFunctionFactory modelFunctionFactory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private InstrumentModel instrument;

    @Getter
    private SkillsModel skills;

    @Inject
    public InstrumentView(InstrumentService instrumentService, ModelFunctionFactory modelFunctionFactory, SkillService skillService) {
        this.instrumentService = instrumentService;
        this.modelFunctionFactory = modelFunctionFactory;
        this.skillService = skillService;
    }

    public void init() throws IOException {
        Optional<Instrument> instrument1 = instrumentService.find(id);
        if(instrument1.isPresent()) {
            this.instrument = modelFunctionFactory.instrumentToModel().apply(instrument1.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Instrument not found");
        }
        Optional<List<Skill>> skills1 = skillService.findAllByInstrument(instrument.getId());
        if(skills1.isPresent()) {
            this.skills = modelFunctionFactory.skillsToModel().apply(skills1.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Skills not found");
        }
    }

    public String deleteSkill(SkillsModel.Skill skill) {
        skillService.delete(skill.getId());
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
