package com.disaster.earthquake.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;

@Data
@Entity
@Validated
@AllArgsConstructor
public class Coordinates {

    private float longitude;

    private float latitude;


}
