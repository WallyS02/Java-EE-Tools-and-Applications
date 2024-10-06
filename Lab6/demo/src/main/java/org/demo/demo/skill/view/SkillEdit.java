package org.demo.demo.skill.view;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
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

    @Inject
    public SkillEdit(ModelFunctionFactory modelFunctionFactory) {
        this.modelFunctionFactory = modelFunctionFactory;
    }

    @EJB
    public void setSkillService(SkillService skillService) {
        this.skillService = skillService;
    }

    public void init() throws IOException {
        Optional<Skill> skill1 = skillService.find(id);
        if(skill1.isPresent()) {
            this.skill = modelFunctionFactory.skillToEditModel().apply(skill1.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Skill not found");
        }
    }

    public String save() {
        skillService.update(modelFunctionFactory.updateSkillWithModel().apply(skillService.find(id).orElseThrow(), skill));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

    public Level[] getLevels() {
        return Level.values();
    }
}
