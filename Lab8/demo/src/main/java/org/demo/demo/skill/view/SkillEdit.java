package org.demo.demo.skill.view;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.demo.demo.component.ModelFunctionFactory;
import org.demo.demo.skill.entity.Skill;
import org.demo.demo.skill.model.SkillEditModel;
import org.demo.demo.skill.service.SkillService;
import org.demo.demo.util.Level;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class SkillEdit implements Serializable {
    private SkillService skillService;

    private final ModelFunctionFactory modelFunctionFactory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private SkillEditModel skill;

    private final FacesContext facesContext;

    @Inject
    public SkillEdit(ModelFunctionFactory modelFunctionFactory, FacesContext facesContext) {
        this.modelFunctionFactory = modelFunctionFactory;
        this.facesContext = facesContext;
    }

    @EJB
    public void setSkillService(SkillService skillService) {
        this.skillService = skillService;
    }

    public void init() throws IOException {
        Optional<Skill> skill1 = skillService.findForCallerPrincipal(id);
        if(skill1.isPresent()) {
            this.skill = modelFunctionFactory.skillToEditModel().apply(skill1.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Skill not found");
        }
    }

    public String save() {
        try {
            skillService.update(modelFunctionFactory.updateSkillWithModel().apply(skillService.find(id).orElseThrow(), skill));
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return viewId + "?faces-redirect=true&includeViewParams=true";
        } catch (Exception e) {
            if (e.getCause() instanceof OptimisticLockException) {
                Skill updatedSkill = skillService.find(id).orElseThrow();

                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getConflictSummary(updatedSkill), null));

                skill.setLevel(updatedSkill.getLevel());
                skill.setFavouriteModelName(updatedSkill.getFavouriteModelName());
                skill.setNumberOfPlayingYears(updatedSkill.getNumberOfPlayingYears());
            }

            return null;
        }
    }

    private String getConflictSummary(Skill updatedSkill) {
        String conflictSummary = "Version conflict: Skill updated by other musician.";
        if(!updatedSkill.getLevel().equals(skill.getLevel())) {
            conflictSummary += "\n level changed: " + skill.getLevel() + " -> " + updatedSkill.getLevel();
        }
        if(!updatedSkill.getNumberOfPlayingYears().equals(skill.getNumberOfPlayingYears())) {
            conflictSummary += "\n number of playing years changed: " + skill.getLevel() + " -> " + updatedSkill.getLevel();
        }
        if(!updatedSkill.getFavouriteModelName().equals(skill.getFavouriteModelName())) {
            conflictSummary += "\n favourite model changed: " + skill.getLevel() + " -> " + updatedSkill.getLevel();
        }
        return conflictSummary;
    }

    public Level[] getLevels() {
        return Level.values();
    }
}
