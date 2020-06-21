package com.disaster.earthquake;

import com.disaster.earthquake.model.Coordinates;
import com.disaster.earthquake.model.Earthquake;
import com.disaster.earthquake.service.EarthquakeService;
import com.sun.tools.javac.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.disaster.earthquake.utils.EarthquakeListUtil.calculateDistanceForEachEarthquake;
import static com.disaster.earthquake.utils.EarthquakeListUtil.getTenClosestEarthquakes;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = EarthquakeApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class EarthquakeListUtilTest {


    @Autowired
    EarthquakeService earthquakeService;

    List<Coordinates> earthquakeCoords = List.of(
            new Coordinates(9.306100F, 69.330610F),
            new Coordinates(-65.306100F, 10.330610F),
            new Coordinates(160.306100F, 30.330610F),
            new Coordinates(50.306100F, 40.330610F),
            new Coordinates(140.306100F, 70.330610F),
            new Coordinates(30.306100F, 80.330610F),
            new Coordinates(2.306100F, -20.330610F),
            new Coordinates(10.306100F, -60.330610F),
            new Coordinates(-10.306100F, -30.330610F),
            new Coordinates(-130.306100F, -60.330610F),
            new Coordinates(-140.306100F, -10.330610F),
            new Coordinates(-50.306100F, -50.330610F),
            new Coordinates(-60.306100F, 6.330610F),
            new Coordinates(-70.306100F, 35.330610F));

    @Test
    public void getFinalListOfEarthquakesTest() {
        String longitude = "100.123456";
        String latitude = "10.123456";

        java.util.List<Earthquake> earthquakes = IntStream.range(0, earthquakeCoords.size()).mapToObj(i -> {
            return new Earthquake("Earthquake " + i, earthquakeCoords.get(i), 0);
        }).collect(Collectors.toList());

        java.util.List<Earthquake> calculatedDistanceList = calculateDistanceForEachEarthquake(latitude, longitude, earthquakes);
        java.util.List<Earthquake> finalList = getTenClosestEarthquakes(calculatedDistanceList);

        System.out.println(earthquakeService.getStringOutput(finalList));

        assertEquals(10, finalList.size());
    }

    @Test
    public void checkForDuplicatesTest() {

        List<Coordinates> earthquakeCoordsDuplicates = List.of(
                new Coordinates(9.306100F, 69.330610F),
                new Coordinates(9.306100F, 69.330610F),
                new Coordinates(-65.306100F, 10.330610F),
                new Coordinates(-65.306100F, 10.330610F),
                new Coordinates(160.306100F, 30.330610F),
                new Coordinates(160.306100F, 30.330610F),
                new Coordinates(50.306100F, 40.330610F),
                new Coordinates(50.306100F, 40.330610F),
                new Coordinates(140.306100F, 70.330610F),
                new Coordinates(140.306100F, 70.330610F),
                new Coordinates(30.306100F, 80.330610F),
                new Coordinates(30.306100F, 80.330610F),
                new Coordinates(2.306100F, -20.330610F),
                new Coordinates(2.306100F, -20.330610F),
                new Coordinates(10.306100F, -60.330610F),
                new Coordinates(10.306100F, -60.330610F),
                new Coordinates(-10.306100F, -30.330610F),
                new Coordinates(-10.306100F, -30.330610F),
                new Coordinates(-130.306100F, -60.330610F),
                new Coordinates(-130.306100F, -60.330610F),
                new Coordinates(-140.306100F, -10.330610F),
                new Coordinates(-50.306100F, -50.330610F),
                new Coordinates(-60.306100F, 6.330610F),
                new Coordinates(-70.306100F, 35.330610F));

        String longitude = "100.123456";
        String latitude = "10.123456";

        java.util.List<Earthquake> earthquakes = IntStream.range(0, earthquakeCoordsDuplicates.size())
                .mapToObj(i -> new Earthquake("Earthquake " + i, earthquakeCoordsDuplicates.get(i), 0))
                .collect(Collectors.toList());

        java.util.List<Earthquake> calculatedDistanceList = calculateDistanceForEachEarthquake(latitude, longitude, earthquakes);
        java.util.List<Earthquake> finalList = getTenClosestEarthquakes(calculatedDistanceList);


        java.util.List<Coordinates> coordsList = finalList.stream().map(Earthquake::getCoords).collect(Collectors.toList());
        Assertions.assertThat(coordsList).doesNotHaveDuplicates();

        System.out.println(earthquakeService.getStringOutput(finalList));
        assertEquals(10, finalList.size());
    }

}
