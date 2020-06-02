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

    @Autowired
    private MockMvc mvc;

    String url = "/earthquakes/{latitude}/{longitude}";

    @Test
    public void standardInputTest() throws Exception {
        String latitude = "40.730610";
        String longitude = "-73.935242";

        mvc.perform(get(url, latitude, longitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void verifyOutputResultTest() throws Exception {
        String latitude = "40.730610";
        String longitude = "-73.935242";

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
    public void invalidLatitudeValueTest() throws Exception {
        String latitude = "2240.730610";
        String longitude = "-73.935242";

        mvc.perform(get(url, latitude, longitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400)).
                andDo(print()).andExpect(content().string(containsString(
                "some parameters are invalid: Invalid input, The latitude must be a number between -90 and 90 and the longitude between -180 and 180.")));
    }

    @Test
    public void invalidLongitudeValueTest() throws Exception {
        String latitude = "40.730610";
        String longitude = "-773.935242";

        mvc.perform(get(url, latitude, longitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400)).
                andDo(print()).andExpect(content().string(containsString(
                "some parameters are invalid: Invalid input, The latitude must be a number between -90 and 90 and the longitude between -180 and 180.")));
    }

    @Test
    public void invalidPrecisionValueTest() throws Exception {
        String latitude = "40.7306100";
        String longitude = "-73.9352422";

        mvc.perform(get(url, latitude, longitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400)).
                andDo(print()).andExpect(content().string(containsString(
                "some parameters are invalid: Invalid input, must be in format +-00.000000, +-00.000000")));
    }

    @Test
    public void invalidInputTypeLatitudeTest() throws Exception {
        float latitude = 40.730610F;
        String longitude = "-73.9352422";

        mvc.perform(get(url, latitude, longitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400)).
                andDo(print()).andExpect(content().string(containsString(
                "some parameters are invalid: Invalid input, must be in format +-00.000000, +-00.000000")));
    }

    @Test
    public void invalidInputTypeLongitudeTest() throws Exception {
//        String latitude = "40.730610";
//        float longitude = -73.935242F;
        float latitude = 40.730610F;
        String longitude = "-73.9352422";
        mvc.perform(get(url, latitude, longitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400)).
                andDo(print()).andExpect(content().string(containsString(
                "some parameters are invalid: Invalid input, must be in format +-00.000000, +-00.000000")));
    }

}
