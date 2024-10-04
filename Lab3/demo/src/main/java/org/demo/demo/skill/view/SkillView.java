package org.demo.demo.skill.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.demo.demo.component.ModelFunctionFactory;
import org.demo.demo.skill.entity.Skill;
import org.demo.demo.skill.model.SkillModel;
import org.demo.demo.skill.service.SkillService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class SkillView implements Serializable {
    private final SkillService skillService;

    private final ModelFunctionFactory modelFunctionFactory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private SkillModel skill;

    @Inject
    public SkillView(SkillService skillService, ModelFunctionFactory modelFunctionFactory) {
        this.skillService = skillService;
        this.modelFunctionFactory = modelFunctionFactory;
    }

    public void init() throws IOException {
        Optional<Skill> skill1 = skillService.find(id);
        if(skill1.isPresent()) {
            this.skill = modelFunctionFactory.skillToModel().apply(skill1.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Skill not found");
        }
    }
}
