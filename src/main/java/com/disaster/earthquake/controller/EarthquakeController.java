package com.disaster.earthquake.controller;

import com.disaster.earthquake.model.Coordinates;
import com.disaster.earthquake.service.EarthquakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class EarthquakeController {

    @Autowired
    EarthquakeService earthquakeService;

//    @GetMapping("/earthquakes/{longitude}/{latitude}")
//    public String getEarthquakes(@PathVariable float longitude, @PathVariable float latitude) {
//        return earthquakeService.getEarthquakes(longitude, latitude);
//    }


}
