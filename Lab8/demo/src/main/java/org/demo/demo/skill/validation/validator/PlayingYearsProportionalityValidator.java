package org.demo.demo.skill.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.demo.demo.skill.entity.Skill;
import org.demo.demo.skill.validation.annotation.ValidPlayingYearsProportionality;
import org.demo.demo.util.Level;

public class PlayingYearsProportionalityValidator implements ConstraintValidator<ValidPlayingYearsProportionality, Skill> {

    @Override
    public boolean isValid(Skill skill, ConstraintValidatorContext constraintValidatorContext) {
        if (skill == null) {
            return true;
        }

        Integer numberOfPlayingYears = skill.getNumberOfPlayingYears();
        Level level = skill.getLevel();

        if (level != null) {
            return switch (level) {
                case BEGINNER -> numberOfPlayingYears >= 0 && numberOfPlayingYears <= 3;
                case INTERMEDIATE -> numberOfPlayingYears >= 3 && numberOfPlayingYears <= 5;
                case ADVANCED -> numberOfPlayingYears >= 5;
            };
        }
        return true;
    }
}
