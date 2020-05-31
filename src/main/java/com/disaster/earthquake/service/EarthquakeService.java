package com.disaster.earthquake.service;

import com.disaster.earthquake.model.Coordinates;
import com.google.gson.JsonObject;
import org.codehaus.jettison.json.JSONTokener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


@Service
public class EarthquakeService {

    public Coordinates getEarthquakes(double longitude, double latitude) {

        return null;

    }

    private void getProductList(String keyword) throws MalformedURLException, IOException, ParseException {



        URL url = new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php");
        URLConnection request = url.openConnection();
        request.connect();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(
                new InputStreamReader(((InputStream) request.getContent()), "UTF-8"));


//
//        JsonElement jsonElement = new JsonParser().parse(new InputStreamReader((InputStream) request.getContent()));
//        return jsonElement.getAsJsonArray();
    }

}
