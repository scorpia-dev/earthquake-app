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
public class EarthquakeListService {

    EarthquakeFieldsCalculatorService earthquakeFieldsCalculatorService;

    List<Earthquake> getFinalListOfEarthquakes(List<Earthquake> earthquakeList) {
        Comparator<Earthquake> comparator = Comparator.comparing(Earthquake::getDistance);
        return new ArrayList<>(earthquakeList.stream().sorted(comparator)
                .limit(10)
                .collect(Collectors.toMap(Earthquake::getCoords, Function.identity(), (e1, e2) -> e2, LinkedHashMap::new))
                .values());
    }

    List<Earthquake> createEarthquakeList(String latitude, String longitude, JSONArray jsonArray) {
        List<Earthquake> earthquakeList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Coordinates coords = earthquakeFieldsCalculatorService.getCoords(jsonArray, i);

            Earthquake eq = earthquakeFieldsCalculatorService.newEarthquake(i, jsonArray, earthquakeFieldsCalculatorService.getDistance
                    (Float.parseFloat(latitude), Float.parseFloat(longitude), coords.getLatitude(), coords.getLongitude()), coords);

            earthquakeList.add(eq);
        }
        return earthquakeList;
    }
}