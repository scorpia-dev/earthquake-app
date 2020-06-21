package com.disaster.earthquake.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Earthquake {

    private final String title;

    @Getter
    private final Coordinates coords;

    @Setter
    @Getter
    Integer distance;

    @Override
    public String toString() {
        return title + " || " + distance;
    }

}
