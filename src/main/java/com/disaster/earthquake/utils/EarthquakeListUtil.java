package com.disaster.earthquake.utils;

import com.disaster.earthquake.model.Earthquake;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class EarthquakeListUtil {

    private EarthquakeListUtil() {
    }

    public static List<Earthquake> getTenClosestEarthquakes(List<Earthquake> earthquakeList) {
        return earthquakeList.stream()
                .collect(Collectors.toMap(Earthquake::getCoords, Function.identity(), (e1, e2) -> e2, LinkedHashMap::new))
                .values().stream()
                .sorted(Comparator.comparing(Earthquake::getDistance))
                .limit(10)
                .collect(Collectors.toList());
    }

    public static List<Earthquake> calculateDistanceForEachEarthquake(String latitude, String longitude, List<Earthquake> earthquakes) {

        earthquakes.forEach(x -> x.setDistance(getDistance(Float.parseFloat(latitude), Float.parseFloat(longitude),
                x.getCoords().getLatitude(), x.getCoords().getLongitude())));

        return earthquakes;
    }

    private static int getDistance(float lat1, float lng1, float lat2, float lng2) {

        double earthRadius = 6371; // in Kms

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double result = Math.round(earthRadius * c);
        return (int) result;
    }


}