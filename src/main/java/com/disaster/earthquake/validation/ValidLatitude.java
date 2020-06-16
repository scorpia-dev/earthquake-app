package com.disaster.earthquake.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ValidLatitudeValidator.class})
public @interface ValidLatitude {
    String message() default "Invalid latitude";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}