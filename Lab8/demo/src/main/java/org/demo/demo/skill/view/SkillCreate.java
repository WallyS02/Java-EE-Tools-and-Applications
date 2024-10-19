package org.demo.demo.skill.view;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.demo.demo.component.ModelFunctionFactory;
import org.demo.demo.instrument.model.InstrumentModel;
import org.demo.demo.instrument.service.InstrumentService;
import org.demo.demo.skill.entity.Skill;
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
    private SkillService skillService;

    private InstrumentService instrumentService;

    private final ModelFunctionFactory modelFunctionFactory;

    @Getter
    private SkillCreateModel skill;

    @Getter
    private List<InstrumentModel> instruments;

    private final FacesContext facesContext;

    @Inject
    public SkillCreate(ModelFunctionFactory modelFunctionFactory, FacesContext facesContext) {
        this.modelFunctionFactory = modelFunctionFactory;
        this.facesContext = facesContext;
    }

    @EJB
    public void setInstrumentService(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    @EJB
    public void setSkillService(SkillService skillService) {
        this.skillService = skillService;
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
        try {
            skillService.createForCallerPrincipal(modelFunctionFactory.modelToSkill().apply(skill));
            return "/instrument/instrument_list.xhtml?faces-redirect=true";
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            }

            return null;
        }
    }

    public Level[] getLevels() {
        return Level.values();
    }
}
