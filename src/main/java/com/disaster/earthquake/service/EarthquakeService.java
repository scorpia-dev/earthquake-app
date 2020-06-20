package com.disaster.earthquake.service;

import com.disaster.earthquake.model.Earthquake;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.disaster.earthquake.utils.EarthquakeApiUtil.getAlLEarthquakes;
import static com.disaster.earthquake.utils.EarthquakeListUtil.calculateDistanceForEachEarthquake;
import static com.disaster.earthquake.utils.EarthquakeListUtil.getFinalListOfEarthquakes;

@Service
public class EarthquakeService {


    public String getClosestTenEarthquakes(String latitude, String longitude) throws IOException {
        List<Earthquake> earthquakesWithoutDistance = getAlLEarthquakes("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson");
        List<Earthquake> calculatedDistanceList = calculateDistanceForEachEarthquake(latitude, longitude, earthquakesWithoutDistance);
        List<Earthquake> tenClosestEarthquakes = getFinalListOfEarthquakes(calculatedDistanceList);
        return getStringOutput(tenClosestEarthquakes);
    }

    public String getStringOutput(List<Earthquake> earthquakes) {
        StringBuilder sb = new StringBuilder();
        earthquakes.forEach(eq -> sb.append(eq).append(System.lineSeparator()));
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
