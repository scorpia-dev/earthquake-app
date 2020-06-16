package com.disaster.earthquake.service;

import com.disaster.earthquake.model.Earthquake;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Service
public class EarthquakeService {

    private final JsonApiService jsonApiService;
    private final EarthquakeListService earthquakeListService;

    public String getClosestTenEarthquakes(String latitude, String longitude) throws IOException {
        JSONArray jsonArray = jsonApiService.getEarthquakesJson();
        List<Earthquake> allEarthquakes = earthquakeListService.createEarthquakeList(latitude, longitude, jsonArray);
        List<Earthquake> tenClosestEarthquakes = earthquakeListService.getFinalListOfEarthquakes(allEarthquakes);
        return getStringOutput(tenClosestEarthquakes);
    }

    private String getStringOutput(List<Earthquake> earthquakes) {
        StringBuilder sb = new StringBuilder();
        earthquakes.forEach(eq -> sb.append(eq).append(System.lineSeparator()));
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
