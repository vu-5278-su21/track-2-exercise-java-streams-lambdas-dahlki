package edu.vanderbilt.cs.streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class BikeStatsTest {

    @Test
    public void testAveragedDataFrameStream(){
        BikeRide ride = BikeRideTest.loadSampleRide();
        BikeStats stats = new BikeStats(BikeRideTest.loadSampleRide());

        // Consistency checks
        double velocityAverage = stats.averagedDataFrameStream(1)
                                    .mapToDouble(BikeRide.DataFrame::getVelocity)
                                    .average()
                                    .getAsDouble();
        double expectedVelocityAverage = ride.velocityStream().average().getAsDouble();
        assertEquals(expectedVelocityAverage, velocityAverage, 0.1);

        // The coordinate should be set by the first item in the window
        assertEquals(ride.coordinateStream().findFirst().get(),
                     stats.averagedDataFrameStream(10).findFirst().get().coordinate);
        assertEquals(ride.coordinateStream().skip(1).findFirst().get(),
                stats.averagedDataFrameStream(10).skip(1).findFirst().get().coordinate);

        assertEquals(
                ride.velocityStream().limit(5).average().getAsDouble(),
                stats.averagedDataFrameStream(5)
                        .findFirst().get().velocity);

        assertEquals(
                ride.velocityStream().limit(25).average().getAsDouble(),
                stats.averagedDataFrameStream(25)
                        .findFirst().get().velocity);


        // This is a slow test and possibly not worth the bang for the buck.
        //
        int windowSize = Math.max(
                1,
                (int)Math.rint(Math.random() * ride.velocityStream().count()));

        assertEquals(
                ride.velocityStream()
                        .skip(ride.velocityStream().count() - windowSize)
                        .sum(),
                stats.averagedDataFrameStream(windowSize)
                        .skip(stats.averagedDataFrameStream(windowSize).count() - 1)
                        .mapToDouble(f -> f.getVelocity() * windowSize)
                        .sum());

        // Checks to see if the sum of the values in each sliding window
        // equals the average for the corresponding data frame times the
        // number of items in the window
        IntStream.range(0, (int) ride.velocityStream().count() - windowSize)
                    .forEach( i -> {
                        assertEquals(
                                ride.velocityStream()
                                        .skip(i)
                                        .limit(windowSize)
                                        .sum(),
                                stats.averagedDataFrameStream(windowSize)
                                        .skip(i)
                                        .limit(1)
                                        .mapToDouble(f -> f.getVelocity() * windowSize)
                                        .sum(),
                                0.01);
                    });

        // Consistency checks
        double altAverage = stats.averagedDataFrameStream(1)
                .mapToDouble(BikeRide.DataFrame::getAltitude)
                .average()
                .getAsDouble();
        double expectedAltAverage = ride.altitudeStream().average().getAsDouble();
        assertEquals(expectedAltAverage, altAverage, 0.1);


        // Consistency checks
        double heartAvg = stats.averagedDataFrameStream(1)
                .mapToDouble(BikeRide.DataFrame::getHeartRate)
                .average()
                .getAsDouble();
        double expectedHeartAvg = ride.heartRateStream().average().getAsDouble();
        assertEquals(expectedHeartAvg, heartAvg, 0.1);

        // Consistency checks
        double gradeAvg = stats.averagedDataFrameStream(1)
                .mapToDouble(BikeRide.DataFrame::getGrade)
                .average()
                .getAsDouble();
        double expectedGradeAvg = ride.gradeStream().average().getAsDouble();
        assertEquals(expectedGradeAvg, gradeAvg, 0.1);
    }

    @Test
    public void testLocationsOfStops(){
        BikeStats stats = new BikeStats(BikeRideTest.loadSampleRide());

        assertEquals(9, stats.locationsOfStops().count());

        assertEquals( 36.13368,
                stats.locationsOfStops().mapToDouble(l -> l.latitude).average().getAsDouble(),
                0.00001);

        assertEquals( -86.84714,
                stats.locationsOfStops().mapToDouble(l -> l.longitude).average().getAsDouble(),
                0.00001);
    }
}
