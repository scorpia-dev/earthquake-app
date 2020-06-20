package com.disaster.earthquake;

import com.disaster.earthquake.model.Earthquake;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

import static com.disaster.earthquake.utils.EarthquakeApiUtil.getAlLEarthquakes;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = EarthquakeApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class JsonApiUtilTest {


    @Test
    public void getJsonFromApiTest() throws IOException {
        List<Earthquake> allEarthquakeFromApi = getAlLEarthquakes("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson");
        assertTrue(allEarthquakeFromApi.size() > 1000);
        System.out.println("There are " + allEarthquakeFromApi.size() + " Earthquakes in the API");
    }
}
