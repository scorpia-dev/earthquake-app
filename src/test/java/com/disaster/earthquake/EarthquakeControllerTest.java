package com.disaster.earthquake;

import com.disaster.earthquake.service.EarthquakeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = EarthquakeApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class EarthquakeControllerTest {

    @Autowired
    EarthquakeService earthquakeService;
    String url = "/earthquakes/{latitude}/{longitude}";
    String invalidPrecisionError = "Invalid input, latitude and longitude must be in format +-00.000000, +-00.000000";
    String invalidLongitudeError = "Invalid input, The longitude must be between -180 and 180.";
    String invalidLatitudeError = "Invalid input, The latitude must be a number between -90 and 90.";
    String invalidInputTypeError = "Invalid input, must be numerical only";
    String latitude = "40.730610";
    String longitude = "-73.935242";
    @Autowired
    private MockMvc mvc;

    @Test
    public void standardInputTest() throws Exception {
        mvc.perform(get(url, latitude, longitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void verifyOutputResultTest() throws Exception {
        MvcResult result = mvc.perform(get(url, latitude, longitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String[] earthquakes = getEachEarthquake(result);
        assertEquals(10, earthquakes.length);
        assertTrue(isListInClosestDistanceOrder(earthquakes));
    }

    private String[] getEachEarthquake(MvcResult result) throws UnsupportedEncodingException {
        String content = result.getResponse().getContentAsString();
        return content.split("\\r?\\n");
    }

    private boolean isListInClosestDistanceOrder(String[] lines) {
        List<Integer> distances = new ArrayList<>();

        for (String line : lines) {
            distances.add(Integer.parseInt(line.substring(line.lastIndexOf('|') + 2)));
        }
        assertEquals(10, distances.size());

        for (int i = 0; i < distances.size() - 1; i++) {
            if (distances.get(i) <= distances.get(i + 1)) {
                continue;
            }
            return false;
        }
        return true;
    }

    @Test
    public void invalidLatitudeRangeTest() throws Exception {
        String invalidLatitude = "2240.730610";

        mvc.perform(get(url, invalidLatitude, longitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400)).
                andDo(print()).andExpect(content().string(containsString(
                invalidLatitudeError)));
    }

    @Test
    public void invalidLongitudeRangeTest() throws Exception {
        String invalidLongitude = "-773.935242";

        mvc.perform(get(url, latitude, invalidLongitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400)).
                andDo(print()).andExpect(content().string(containsString(
                invalidLongitudeError)));
    }

    @Test
    public void invalidPrecisionLongitudeTest() throws Exception {
        String invalidLongitude = "-73.9352422";

        mvc.perform(get(url, latitude, invalidLongitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400)).
                andDo(print()).andExpect(content().string(containsString(
                invalidPrecisionError)));
    }

    @Test
    public void invalidPrecisionLatitudeTest() throws Exception {
        String invalidLatitude = "40.7306100";

        mvc.perform(get(url, latitude, invalidLatitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400)).
                andDo(print()).andExpect(content().string(containsString(
                invalidPrecisionError)));
    }

    @Test
    public void invalidInputTypeLatitudeTest() throws Exception {
        String invalidLatitude = "40.730610F";

        mvc.perform(get(url, invalidLatitude, longitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400)).
                andDo(print()).andExpect(content().string(containsString(
                invalidInputTypeError)));
    }

    @Test
    public void invalidInputTypeLongitudeTest() throws Exception {
        String invalidLongitude = "-73.33413F";

        mvc.perform(get(url, latitude, invalidLongitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400)).
                andDo(print()).andExpect(content().string(containsString(
                invalidInputTypeError)));
    }

}
