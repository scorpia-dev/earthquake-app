package earthquakeService;

import com.disaster.earthquake.EarthquakeApplication;
import com.disaster.earthquake.model.Coordinates;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;

//@SpringBootTest(classes = EarthquakeApplication.class)
//@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc
//public class EarthquakeServiceTest {
//
//
////    @Test
////
////
////}
