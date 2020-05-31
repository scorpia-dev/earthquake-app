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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;

@SpringBootTest(classes = EarthquakeApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class EarthquakeServiceTest {


    @Test
    public void getUserByIdTest2() throws IOException, JSONException {

        InputStream is = new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson").openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);

            JSONArray jsonArray = new JSONObject(jsonText).getJSONArray("features");
             int length = jsonArray.length();

        ArrayList<Coordinates> locations = new ArrayList<>();

        for(int i=0; i<length; i++){
            JSONArray jo = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");

            Number lat = (Number) jo.get(0);
            float latNew = lat.floatValue();

            Number lng = (Number) jo.get(1);
            float lngNew = lng.floatValue();

            locations.add(new Coordinates(latNew, lngNew));
        }

        Map<Coordinates,Double> topTen = new HashMap<>();
            for (int i = 0;i<length;i++){

                double lat =locations.get(i).getLatitude();
                double lng = locations.get(i).getLongitude();
                double dist = distance(40.730610, -73.935242, lat, lng);
                if(!topTen.containsKey(locations.get(i))){
                    topTen.put(locations.get(i),dist);
                }
        }

        LinkedHashMap<Coordinates, Double> sortedMap =
                topTen.entrySet().stream().
                        sorted(Map.Entry.comparingByValue()).limit(10).
                        collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));




        assertNotNull(locations);
        assertNotNull(sortedMap);

        assertNotNull(topTen);

    }


    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return earthRadius * c; // output distance, in MILES
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
