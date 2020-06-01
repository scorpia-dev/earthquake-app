package com.disaster.earthquake.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;

@Data
@Entity
@Validated
@AllArgsConstructor
public class Earthquake {

    String title;

    Integer distance;

    @Override
    public String toString() {
        return title + " || " + distance + System.lineSeparator();
    }



}
