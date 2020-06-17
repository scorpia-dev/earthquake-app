package com.disaster.earthquake.util;

import com.disaster.earthquake.model.Coordinates;
import com.disaster.earthquake.model.Earthquake;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class JsonApiUtil {

    @Autowired
    EarthquakeFieldsCalculator earthquakeFieldsCalculator;

    public List<Earthquake> getEarthquakes() throws IOException {
        JSONArray jsonArray = getJsonFromApi();

        return IntStream.range(0, jsonArray.length()).mapToObj(i ->
        {
            Coordinates coords = earthquakeFieldsCalculator.getCoords(jsonArray.getJSONObject(i));
            String title = jsonArray.getJSONObject(i).getJSONObject("properties").getString("title");
            return new Earthquake(title, coords, 0);
        }).collect(Collectors.toList());
    }

    public JSONArray getJsonFromApi() throws IOException {
        InputStream is = new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson").openStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String jsonText = readAll(rd);

        return new JSONObject(jsonText).getJSONArray("features");
    }

    String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}