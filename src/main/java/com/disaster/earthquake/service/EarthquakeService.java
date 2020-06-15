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

    private final JsonApi jsonApi;
    private final Validation validation;
    private final EarthquakeList earthquakeList;

    public String getClosestTenEarthquakes(String latitudeAsString, String longitudeAsString) throws IOException {

        float latitude = validation.stringToFloat(latitudeAsString);
        float longitude = validation.stringToFloat(longitudeAsString);

        if (validation.isValidInput(latitude, longitude)) {

            JSONArray jsonArray = jsonApi.getEarthquakesJson();

            List<Earthquake> allEarthQuakes = earthquakeList.createEarthquakeList(latitude, longitude, jsonArray);

            List<Earthquake> tenClosestEarthquakes = earthquakeList.getFinalListOfEarthquakes(allEarthQuakes);

            return getStringOutput(tenClosestEarthquakes);

        } else {
            throw new IllegalArgumentException("Invalid input, The latitude must be a number between -90 and 90 and the longitude between -180 and 180.");
        }
    }

    private String getStringOutput(List<Earthquake> earthquakes) {
        StringBuilder sb = new StringBuilder();
        earthquakes.forEach(eq -> sb.append(eq).append(System.lineSeparator()));
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
