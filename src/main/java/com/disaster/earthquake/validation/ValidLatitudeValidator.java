package com.disaster.earthquake.validation;

import com.disaster.earthquake.exceptions.InvalidCoordinatesException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidLatitudeValidator implements ConstraintValidator<ValidLatitude, String> {

    @Override
    public boolean isValid(String latitude, ConstraintValidatorContext context) {
        if (
                (Float.parseFloat(latitude) >= -90 && Float.parseFloat(latitude) <= 90))
            return true;
        else
            throw new InvalidCoordinatesException("Invalid input, The latitude must be a number between -90 and 90.");

    }
}

