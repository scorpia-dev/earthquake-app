package com.disaster.earthquake.utils;

import com.disaster.earthquake.model.Coordinates;
import com.disaster.earthquake.model.Earthquake;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public final class EarthquakeApiUtil {

    private EarthquakeApiUtil() {
    }

    public static List<Earthquake> getAlLEarthquakes(String url) throws IOException {
        JSONArray jsonArray = getJsonFromApi(url);
        return IntStream.range(0, jsonArray.length()).mapToObj(i ->
        {
            Coordinates coords = getCoords(jsonArray.getJSONObject(i));
            String title = jsonArray.getJSONObject(i).getJSONObject("properties").getString("title");
            return new Earthquake(title, coords, 0);
        }).collect(Collectors.toList());
    }

    private static Coordinates getCoords(JSONObject jsonObject) {
        JSONArray jo = jsonObject.getJSONObject("geometry").getJSONArray("coordinates");

        Number lat = (Number) jo.get(0);
        float latNew = lat.floatValue();

        Number lng = (Number) jo.get(1);
        float lngNew = lng.floatValue();
        return new Coordinates(latNew, lngNew);
    }

    private static JSONArray getJsonFromApi(String url) throws IOException {

        InputStream is = new URL(url).openStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String jsonText = readAll(rd);

        return new JSONObject(jsonText).getJSONArray("features");
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}