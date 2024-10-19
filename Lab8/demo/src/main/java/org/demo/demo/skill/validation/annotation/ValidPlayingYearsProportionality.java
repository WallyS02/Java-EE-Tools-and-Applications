package org.demo.demo.skill.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.demo.demo.skill.validation.validator.PlayingYearsProportionalityValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PlayingYearsProportionalityValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPlayingYearsProportionality {
    String message() default "Number of playing years must be proportional to skill level.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
