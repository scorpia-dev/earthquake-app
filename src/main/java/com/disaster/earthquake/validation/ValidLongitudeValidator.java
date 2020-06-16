package com.disaster.earthquake.validation;

import com.disaster.earthquake.exceptions.InvalidCoordinatesException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidLongitudeValidator implements ConstraintValidator<ValidLongitude, String> {

    public boolean isValid(String longitude, ConstraintValidatorContext context) {
        if (
                (Float.parseFloat(longitude) >= -180 && Float.parseFloat(longitude) <= 180))
            return true;
        else
            throw new InvalidCoordinatesException("Invalid input, The longitude must be between -180 and 180.");
    }
}
