package earthquake_service;

import com.disaster.earthquake.EarthquakeApplication;
import com.disaster.earthquake.service.EarthquakeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
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
    float latitude = 40.730610F;
    float longitude = -73.935242F;

        mvc.perform(get(url, latitude, longitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void invalidLatitudeValueTest() throws Exception {
        float latitude = 2240.730610F;
        float longitude = -73.935242F;

        mvc.perform(get(url, latitude, longitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(status().is(400)).
        andDo(print()).andExpect(content().string(containsString(
                "some parameters are invalid: Invalid input, The latitude must be a number between -90 and 90 and the longitude between -180 and 180.")));
    }

    @Test
    public void invalidLongitudeValueTest() throws Exception {
        float latitude = 40.730610F;
        float longitude = -773.935242F;

        mvc.perform(get(url, latitude, longitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400)).
                andDo(print()).andExpect(content().string(containsString(
                "some parameters are invalid: Invalid input, The latitude must be a number between -90 and 90 and the longitude between -180 and 180.")));
    }

    @Test
    public void invalidInputTypeLatitudeTest() throws Exception {
        String latitude = "test";
        float longitude = -73.935242F;

        mvc.perform(get(url, latitude, longitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400)).
                andDo(print()).andExpect(content().string(containsString(
                "not valid due to validation error: latitude should be of type float")));
    }

    @Test
    public void invalidInputTypeLongitudeTest() throws Exception {
        float latitude = 40.730610F;
        String longitude = "test";

        mvc.perform(get(url, latitude, longitude).accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400)).
                andDo(print()).andExpect(content().string(containsString(
                "not valid due to validation error: longitude should be of type float")));
    }

}
