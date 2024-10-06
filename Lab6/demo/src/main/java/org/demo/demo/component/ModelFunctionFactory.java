package org.demo.demo.component;

import jakarta.enterprise.context.ApplicationScoped;
import org.demo.demo.instrument.model.function.InstrumentToModelFunction;
import org.demo.demo.instrument.model.function.InstrumentsToModelFunction;
import org.demo.demo.skill.model.function.*;

@ApplicationScoped
public class ModelFunctionFactory {
    public InstrumentToModelFunction instrumentToModel() {
        return new InstrumentToModelFunction();
    }

    public InstrumentsToModelFunction instrumentsToModel() {
        return new InstrumentsToModelFunction();
    }

    public SkillToModelFunction skillToModel() {
        return new SkillToModelFunction();
    }

    public SkillsToModelFunction skillsToModel() {
        return new SkillsToModelFunction();
    }

    public SkillToEditModelFunction skillToEditModel() {
        return new SkillToEditModelFunction();
    }

    public ModelToSkillFunction modelToSkill() {
        return new ModelToSkillFunction();
    }

    public UpdateSkillWithModelFunction updateSkillWithModel() {
        return new UpdateSkillWithModelFunction();
    }
}
