package com.disaster.earthquake.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

@AllArgsConstructor
public class Validation {

    Float stringToFloat(String s) {
        if (NumberUtils.isCreatable(s) && !s.contains("F")) {
            int i = s.indexOf('.');
            String precision = s.substring(i + 1);
            if (precision.length() != 6) {
                throw new IllegalArgumentException("Invalid input, latitude and longitude must be in format +-00.000000, +-00.000000");
            } else {
                return Float.parseFloat(s);
            }
        }
        throw new IllegalArgumentException("Invalid input, must be numerical only");
    }

    boolean isValidInput(float latitude, float longitude) {
        return (latitude >= -90 && latitude <= 90) && (longitude >= -180 && longitude <= 180);
    }
}