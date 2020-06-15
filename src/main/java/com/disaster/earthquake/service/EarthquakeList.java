package com.disaster.earthquake.service;

import com.disaster.earthquake.model.Coordinates;
import com.disaster.earthquake.model.Earthquake;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class EarthquakeList {

    EarthquakeFields earthquakeFields;

    List<Earthquake> getFinalListOfEarthquakes(List<Earthquake> earthquakeList) {
        Comparator<Earthquake> comparator = Comparator.comparing(Earthquake::getDistance);
        return new ArrayList<>(earthquakeList.stream().sorted(comparator)
                .limit(10)
                .collect(Collectors.toMap(Earthquake::getCoords, Function.identity(), (e1, e2) -> e2, LinkedHashMap::new))
                .values());
    }

    List<Earthquake> createEarthquakeList(float latitude, float longitude, JSONArray jsonArray) {
        List<Earthquake> earthquakeList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Coordinates coords = earthquakeFields.getCoords(jsonArray, i);
            Earthquake eq = earthquakeFields.newEarthquake(i, jsonArray, earthquakeFields.getDistance(latitude, longitude, coords.getLatitude(), coords.getLongitude()), coords);
            earthquakeList.add(eq);
        }
        return earthquakeList;
    }
}