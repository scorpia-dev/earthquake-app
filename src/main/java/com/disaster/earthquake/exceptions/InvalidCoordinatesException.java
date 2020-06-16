package com.disaster.earthquake.exceptions;

import javax.validation.ValidationException;

public class InvalidCoordinatesException extends ValidationException {

    public InvalidCoordinatesException(String msg) {
        super(msg);
    }
}
