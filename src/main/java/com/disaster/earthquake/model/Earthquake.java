package com.disaster.earthquake.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Earthquake {

    String title;

    Coordinates coords;

    Integer distance;

    @Override
    public String toString() {
        return title + " || " + distance;
    }


}
