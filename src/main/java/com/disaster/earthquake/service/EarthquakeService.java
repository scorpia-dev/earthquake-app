package com.disaster.earthquake.service;

import com.disaster.earthquake.model.Coordinates;
import com.disaster.earthquake.model.Earthquake;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class EarthquakeService {

    public String getClosestTenEarthquakes(String latitudeAsString, String longitudeAsString) throws IOException {

        float latitude = stringToFloat(latitudeAsString);
        float longitude = stringToFloat(longitudeAsString);

        if (isValidInput(latitude, longitude)) {

            JSONArray jsonArray = getEarthquakesJson();

            Map<JSONObject, Integer> distanceMap = calculateDistanceFromEachEarthquake(latitude, longitude, jsonArray);

            LinkedHashMap<JSONObject, Integer> tenClosestEarthquakesJsonMap = getClosestTen(distanceMap);

          List<Earthquake> earthquakes = getFinalListOfEarthquakes(tenClosestEarthquakesJsonMap);

          return getStringOutput(earthquakes);

      } else {
          throw new IllegalArgumentException("Invalid input, The latitude must be a number between -90 and 90 and the longitude between -180 and 180.");
      }
       }

    private String getStringOutput(List<Earthquake> earthquakes) {
        StringBuilder sb = new StringBuilder();
        for (Earthquake eq:earthquakes){
            sb.append(eq.toString());
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private Float stringToFloat(String s){
    if (NumberUtils.isCreatable(s) && !s.contains("F")) {
        int i = s.indexOf('.');
        String precision = s.substring(i + 1);
        if (precision.length() != 6) {
            throw new IllegalArgumentException("Invalid input, latitude and longitude must be in format +-00.000000, +-00.000000");
        } else {
            return Float.parseFloat(s);
        }
    }
        throw new IllegalArgumentException("Invalid input, must be numerical only");
    }


    private boolean isValidInput(float latitude, float longitude) {
        return (latitude >= -90 && latitude <= 90) && (longitude >= -180 && longitude <= 180);
    }

    private Map<JSONObject, Integer> calculateDistanceFromEachEarthquake(float latitude, float longitude, JSONArray jsonArray) {
        Map<JSONObject, Integer> distanceMap = new HashMap<>();

        Map<Coordinates, Map<JSONObject, Integer>> uniqueLocationMap = new HashMap<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            Coordinates coords = getCoords(jsonArray, i);

            if (!uniqueLocationMap.containsKey(coords)) {

                int dist = getDistance(latitude, longitude, coords.getLatitude(), coords.getLongitude());
                distanceMap.put(jsonArray.getJSONObject(i), dist);

                uniqueLocationMap.put(coords, distanceMap);
            }
        }
        return distanceMap;
    }

    private List<Earthquake> getFinalListOfEarthquakes(LinkedHashMap<JSONObject, Integer> topTenClosestEarthquakes) {
        List<Earthquake> closestTenEarthquakesList = new ArrayList<>();
        for (Map.Entry<JSONObject, Integer> entry : topTenClosestEarthquakes.entrySet()) {
            JSONObject key = entry.getKey();
            closestTenEarthquakesList.add(new Earthquake(key.getJSONObject("properties").getString("title"), entry.getValue()));
        }
        return closestTenEarthquakesList;
    }

    private LinkedHashMap<JSONObject, Integer> getClosestTen(Map<JSONObject, Integer> distanceMap) {
        return distanceMap.entrySet().stream().
                sorted(Map.Entry.comparingByValue()).limit(10).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    private JSONArray getEarthquakesJson() throws IOException {
        InputStream is = new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson").openStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String jsonText = readAll(rd);

        return new JSONObject(jsonText).getJSONArray("features");
    }

    private Coordinates getCoords(JSONArray jsonArray, int i)  {
        JSONArray jo = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");

        Number lat = (Number) jo.get(0);
        float latNew = lat.floatValue();

        Number lng = (Number) jo.get(1);
        float lngNew = lng.floatValue();

        return new Coordinates(latNew, lngNew);
    }


    private int getDistance(float lat1, float lng1, float lat2, float lng2) {

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
