package com.disaster.earthquake.service;

import com.disaster.earthquake.model.Coordinates;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class EarthquakeService {

    public List<String> getEarthquakes(double latitude, double longitude) throws IOException {

        JSONArray jsonArray = getEarthquakes();
        int length = jsonArray.length();

        Map<Coordinates, Integer> uniqueStore = new HashMap<>();
        Map<JSONObject, Integer> distanceMap = new HashMap<>();

        for (int i = 0; i < length; i++) {
            Coordinates co = getCoords(jsonArray, i);

            if (!uniqueStore.containsKey(co)) {
                uniqueStore.put(co, i);

                int dist = distance(latitude, longitude, co.getLatitude(), co.getLongitude());
                distanceMap.put(jsonArray.getJSONObject(i), dist);

            }
        }

        LinkedHashMap<JSONObject, Integer> topTenClosestDistance =
                distanceMap.entrySet().stream().
                        sorted(Map.Entry.comparingByValue()).limit(10).
                        collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));


        List<String> output = new ArrayList<>();
        for (Map.Entry<JSONObject, Integer> entry : topTenClosestDistance.entrySet()) {
            JSONObject key = entry.getKey();
            output.add(key.getJSONObject("properties").getString("title") + " || " + entry.getValue());
        }
        return output;
    }

    private JSONArray getEarthquakes() throws IOException {
        InputStream is = new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson").openStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String jsonText = readAll(rd);

        return new JSONObject(jsonText).getJSONArray("features");
    }

    private Coordinates getCoords(JSONArray jsonArray, int i) throws JSONException {
        JSONArray jo = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");

        Number lat = (Number) jo.get(0);
        float latNew = lat.floatValue();

        Number lng = (Number) jo.get(1);
        float lngNew = lng.floatValue();

        return new Coordinates(latNew, lngNew);
    }


    private int distance(double lat1, double lng1, double lat2, double lng2) {

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


    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


}
