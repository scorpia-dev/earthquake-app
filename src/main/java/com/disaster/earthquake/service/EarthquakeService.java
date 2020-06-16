package com.disaster.earthquake.service;

import com.disaster.earthquake.model.Earthquake;
import com.disaster.earthquake.util.EarthquakeListUtil;
import com.disaster.earthquake.util.JsonApiUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Service
public class EarthquakeService {

    private final JsonApiUtil jsonApiUtil;
    private final EarthquakeListUtil earthquakeListUtil;

    public String getClosestTenEarthquakes(String latitude, String longitude) throws IOException {
        List<Earthquake> earthquakesWithoutDistance = jsonApiUtil.getEarthquakes();
        List<Earthquake> calculatedDistanceList = earthquakeListUtil.calculateDistance(latitude, longitude, earthquakesWithoutDistance);
        List<Earthquake> tenClosestEarthquakes = earthquakeListUtil.getFinalListOfEarthquakes(calculatedDistanceList);
        return getStringOutput(tenClosestEarthquakes);
    }

    public String getStringOutput(List<Earthquake> earthquakes) {
        StringBuilder sb = new StringBuilder();
        earthquakes.forEach(eq -> sb.append(eq).append(System.lineSeparator()));
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
