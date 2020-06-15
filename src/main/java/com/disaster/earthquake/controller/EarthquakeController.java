package com.disaster.earthquake.controller;

import com.disaster.earthquake.service.EarthquakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Validated
public class EarthquakeController {

    @Autowired
    EarthquakeService earthquakeService;

    @GetMapping("/earthquakes/{latitude}/{longitude}")
    public String getEarthquakes(@PathVariable String latitude, @PathVariable String longitude) throws IOException {
        return earthquakeService.getClosestTenEarthquakes(latitude, longitude);
    }
}
