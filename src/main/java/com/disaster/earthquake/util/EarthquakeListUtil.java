package com.disaster.earthquake.util;

import com.disaster.earthquake.model.Earthquake;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class EarthquakeListUtil {

    EarthquakeFieldsCalculator earthquakeFieldsCalculator;

    public List<Earthquake> getFinalListOfEarthquakes(List<Earthquake> earthquakeList) {
        return earthquakeList.stream()
                .collect(Collectors.toMap(Earthquake::getCoords, Function.identity(), (e1, e2) -> e2, LinkedHashMap::new))
                .values().stream()
                .sorted(Comparator.comparing(Earthquake::getDistance))
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Earthquake> calculateDistance(String latitude, String longitude, List<Earthquake> earthquakes) {
        float floatLatitude = Float.parseFloat(latitude);
        float floatLongitude = Float.parseFloat(longitude);

        earthquakes.forEach(x -> x.setDistance(earthquakeFieldsCalculator.getDistance(floatLatitude, floatLongitude,
                x.getCoords().getLatitude(), x.getCoords().getLongitude())));

        return earthquakes;
    }
}