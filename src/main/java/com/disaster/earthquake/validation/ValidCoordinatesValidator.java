package com.disaster.earthquake.validation;

import com.disaster.earthquake.exceptions.InvalidCoordinatesException;
import org.apache.commons.lang3.math.NumberUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidCoordinatesValidator implements ConstraintValidator<ValidCoordinates, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if (NumberUtils.isCreatable(s) && !s.contains("F")) {
            int i = s.indexOf('.');
            String precision = s.substring(i + 1);
            if (precision.length() != 6) {
                throw new InvalidCoordinatesException("Invalid input, latitude and longitude must be in format +-00.000000, +-00.000000");
            }
            return true;
        }
        throw new InvalidCoordinatesException("Invalid input, must be numerical only");
    }
}
