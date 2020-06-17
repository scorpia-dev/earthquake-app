package com.disaster.earthquake;

import com.disaster.earthquake.util.JsonApiUtil;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = EarthquakeApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class JsonApiUtilTest {

    @Autowired
    JsonApiUtil jsonApiUtil;

    @Test
    public void getJsonFromApiTest() throws IOException {

        JSONArray jsonArrayAllEarthquakes = jsonApiUtil.getJsonFromApi();


        assertTrue(jsonArrayAllEarthquakes.length() > 1000);

        System.out.println("There are " + jsonArrayAllEarthquakes.length() + " Earthquakes in the API");

    }
}
