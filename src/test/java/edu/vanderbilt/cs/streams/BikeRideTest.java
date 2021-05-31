package edu.vanderbilt.cs.streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;

public class BikeRideTest {

    public static final int EXPECTED_DATA_ITEMS = 1404;
    public static final double EXPECTED_AVG_ALTITUDE = 138.85;
    public static final double EXPECTED_AVG_HEART_RATE = 122.92;
    public static final double EXPECTED_AVG_GRADE = 0.163;
    public static final double EXPECTED_AVG_VELOCITY = 3.47;
    public static final double EXPECTED_AVG_LATITUDE = 36.13;
    public static final double EXPECTED_AVG_LONGITUDE = -86.84;

    public static BikeRide loadSampleRide() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new FileInputStream("src/main/resources/data.json"), BikeRide.class);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testVelocityStream(){
        BikeRide ride = loadSampleRide();
        assertEquals(EXPECTED_DATA_ITEMS, ride.velocityStream().count());
        assertEquals(EXPECTED_AVG_VELOCITY, ride.velocityStream().average().getAsDouble(), 0.1);
    }

    @Test
    public void testAltitudeStream(){
        BikeRide ride = loadSampleRide();
        assertEquals(EXPECTED_DATA_ITEMS, ride.altitudeStream().count());
        assertEquals(EXPECTED_AVG_ALTITUDE, ride.altitudeStream().average().getAsDouble(), 0.1);
    }

    @Test
    public void testHeartRateStream(){
        BikeRide ride = loadSampleRide();
        assertEquals(EXPECTED_DATA_ITEMS, ride.heartRateStream().count());
        assertEquals(EXPECTED_AVG_HEART_RATE, ride.heartRateStream().average().getAsDouble(), 0.1);
    }

    @Test
    public void testGradeStream(){
        BikeRide ride = loadSampleRide();
        assertEquals(EXPECTED_DATA_ITEMS, ride.gradeStream().count());
        assertEquals(EXPECTED_AVG_GRADE, ride.gradeStream().average().getAsDouble(), 0.1);
    }

    @Test
    public void testCoordinateStream(){
        BikeRide ride = loadSampleRide();
        assertEquals(EXPECTED_DATA_ITEMS, ride.coordinateStream().count());
        assertEquals(EXPECTED_AVG_LATITUDE, ride.coordinateStream().mapToDouble(l -> l.latitude).average().getAsDouble(), 0.1);
        assertEquals(EXPECTED_AVG_LONGITUDE, ride.coordinateStream().mapToDouble(l -> l.longitude).average().getAsDouble(), 0.1);
    }

    @Test
    public void testFusedDataFrameStream(){
        BikeRide ride = loadSampleRide();
        assertEquals(EXPECTED_DATA_ITEMS, ride.fusedFramesStream().count());

        assertEquals(EXPECTED_AVG_ALTITUDE,
                ride.fusedFramesStream().mapToDouble(f -> f.altitude).average().getAsDouble(),
                0.1);

        assertEquals(EXPECTED_AVG_GRADE,
                ride.fusedFramesStream().mapToDouble(f -> f.grade).average().getAsDouble(),
                0.1);

        assertEquals(EXPECTED_AVG_HEART_RATE,
                ride.fusedFramesStream().mapToDouble(f -> f.heartRate).average().getAsDouble(),
                0.1);

        assertEquals(EXPECTED_AVG_VELOCITY,
                ride.fusedFramesStream().mapToDouble(f -> f.velocity).average().getAsDouble(),
                0.1);

        assertEquals(EXPECTED_AVG_LATITUDE,
                ride.fusedFramesStream().mapToDouble(f -> f.coordinate.latitude).average().getAsDouble(),
                0.1);

        assertEquals(EXPECTED_AVG_LONGITUDE,
                ride.fusedFramesStream().mapToDouble(f -> f.coordinate.longitude).average().getAsDouble(),
                0.1);
    }

}
