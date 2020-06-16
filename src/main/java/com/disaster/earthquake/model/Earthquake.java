package com.disaster.earthquake.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Earthquake {

    String title;

    Coordinates coords;

    @Setter
    Integer distance;

    @Override
    public String toString() {
        return title + " || " + distance;
    }

}
