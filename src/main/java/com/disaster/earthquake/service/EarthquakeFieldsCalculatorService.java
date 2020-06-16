package com.disaster.earthquake.service;

import com.disaster.earthquake.model.Coordinates;
import com.disaster.earthquake.model.Earthquake;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EarthquakeFieldsCalculatorService {

    Coordinates getCoords(JSONArray jsonArray, int i) {
        JSONArray jo = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");

        Number lat = (Number) jo.get(0);
        float latNew = lat.floatValue();

        Number lng = (Number) jo.get(1);
        float lngNew = lng.floatValue();

        return new Coordinates(latNew, lngNew);
    }

    int getDistance(float lat1, float lng1, float lat2, float lng2) {

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

    Earthquake newEarthquake(int i, JSONArray jsonArray, int dist, Coordinates coords) {
        return new Earthquake(jsonArray.getJSONObject(i).getJSONObject("properties").getString("title"), coords, dist);
    }
}