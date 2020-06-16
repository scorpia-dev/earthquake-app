package com.disaster.earthquake.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class Coordinates {

    private final float longitude;

    private final float latitude;


}
