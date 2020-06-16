package com.disaster.earthquake.controller;

import com.disaster.earthquake.service.EarthquakeService;
import com.disaster.earthquake.validation.ValidCoordinates;
import com.disaster.earthquake.validation.ValidLatitude;
import com.disaster.earthquake.validation.ValidLongitude;
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
    public String getEarthquakes(@ValidCoordinates @ValidLatitude @PathVariable String latitude, @ValidCoordinates @ValidLongitude @PathVariable String longitude) throws IOException {
        return earthquakeService.getClosestTenEarthquakes(latitude, longitude);
    }
}
