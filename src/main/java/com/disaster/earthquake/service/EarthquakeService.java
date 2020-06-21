package com.disaster.earthquake.service;

import com.disaster.earthquake.model.Earthquake;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.disaster.earthquake.utils.EarthquakeApiUtil.getAlLEarthquakes;
import static com.disaster.earthquake.utils.EarthquakeListUtil.*;

@Service
public class EarthquakeService {


    public String getClosestTenEarthquakes(String latitude, String longitude) throws IOException {
        List<Earthquake> tenClosestEarthquakes =
                getTenClosestEarthquakes(
                        calculateDistanceForEachEarthquake(
                                latitude,
                                longitude,
                                getAlLEarthquakes("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson")));
        return getStringOutput(tenClosestEarthquakes);
    }


}
