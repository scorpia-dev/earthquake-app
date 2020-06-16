package com.disaster.earthquake.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidLongitudeValidator.class})
public @interface ValidLongitude {
    String message() default "Invalid longitude";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}