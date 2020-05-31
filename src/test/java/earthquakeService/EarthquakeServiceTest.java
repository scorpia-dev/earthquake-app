package earthquakeService;

import com.disaster.earthquake.EarthquakeApplication;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.mapbox.geojson.Feature;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
@SpringBootTest(classes = EarthquakeApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class EarthquakeServiceTest {

    @Test
    public void getUserByIdTest() throws IOException, ParseException, JSONException {


        URL url = new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson");
        URLConnection request = url.openConnection();
        request.connect();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(
                new InputStreamReader(((InputStream) request.getContent()), StandardCharsets.UTF_8));

        Feature feature = Feature.fromJson(jsonObject.toString());
        JSONArray jsonArray= (JSONArray) jsonObject.get("features");
        JSONArray jsonArray= (JSONArray) jsonObject.get("features").get;

        jsonArray.get()



        assertNotNull(jsonArray);

        assertNotNull(jsonObject);

        assertNotNull(feature);

    }

    private List<String> convertJsonArrayToList(JSONArray jsonArray) {
        List<String> subStringProductList = new ArrayList<String>();
        if (jsonArray != null) {

            TypeToken<ArrayList<String>> token = new TypeToken<ArrayList<String>>() {
            };
            jsonArray.
            subStringProductList = new Gson().fromJson(jsonArray.get(1), token.getType());

        }
        return subStringProductList;
    }

}
