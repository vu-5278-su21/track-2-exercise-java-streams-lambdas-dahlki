package edu.vanderbilt.cs.streams;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BikeRide {

    public static class DataFrame {
        public final double velocity;
        public final double heartRate;
        public final double grade;
        public final double altitude;
        public final LatLng coordinate;

        public DataFrame(LatLng coordinate, double grade, double altitude, double velocity, double heartRate) {
            super();
            this.velocity = velocity;
            this.heartRate = heartRate;
            this.grade = grade;
            this.altitude = altitude;
            this.coordinate = coordinate;
        }

        public double getVelocity() {
            return velocity;
        }

        public double getHeartRate() {
            return heartRate;
        }

        public double getGrade() {
            return grade;
        }

        public double getAltitude() {
            return altitude;
        }

        public LatLng getCoordinate() {
            return coordinate;
        }
    }

    public static class LatLng {
        public final double latitude;
        public final double longitude;

        @JsonCreator
        public LatLng(double[] latlng) {
            this.latitude = latlng[0];
            this.longitude = latlng[1];
        }

        public boolean equals(Object o) {
            return (o instanceof LatLng)
                    && ((LatLng)o).latitude == this.latitude
                    && ((LatLng)o).longitude == this.longitude;
        }
    }

    public static class LatLngStream {
        public final LatLng[] data;

        @JsonCreator
        public LatLngStream(@JsonProperty("data") LatLng[] data) {
            this.data = data;
        }

        @JsonAnySetter
        public void setOther(String key, Object v) {}
    }

    public static class DataStream {

        public final double[] data;

        @JsonCreator
        public DataStream(@JsonProperty("data") double[] data) {
            this.data = data;
        }

        @JsonAnySetter
        public void setOther(String key, Object v) {}

    }

    public final double[] heartRate;
    public final double[] velocity;
    public final double[] grade;
    public final double[] altitude;
    public final LatLng[] coordinates;

    @JsonCreator
    public BikeRide(@JsonProperty("heartrate") DataStream heartRate,
                    @JsonProperty("velocity_smooth") DataStream velocity,
                    @JsonProperty("grade_smooth") DataStream grade,
                    @JsonProperty("altitude") DataStream altitude,
                    @JsonProperty("latlng") LatLngStream coordinates) {

        super();
        this.heartRate = heartRate.data;
        this.velocity = velocity.data;
        this.grade = grade.data;
        this.altitude = altitude.data;
        this.coordinates = coordinates.data;
    }

    // @ToDo:
    //
    // Implement this method so it returns a
    // stream of the specified values
    //
    // Hint: see Arrays.stream(...)
    //
    public DoubleStream heartRateStream() {
        return DoubleStream.empty();
    }

    // @ToDo:
    //
    // Implement this method so it returns a
    // stream of the specified values
    //
    public DoubleStream velocityStream() {
        return DoubleStream.empty();
    }

    // @ToDo:
    //
    // Implement this method so it returns a
    // stream of the specified values
    public DoubleStream gradeStream() {
        return DoubleStream.empty();
    }

    // @ToDo:
    //
    // Implement this method so it returns a
    // stream of the specified values
    public DoubleStream altitudeStream() {
        return DoubleStream.empty();
    }

    // @ToDo:
    //
    // Implement this method so it returns a
    // stream of the specified values
    public Stream<LatLng> coordinateStream() {
        return Stream.empty();
    }


    // @ToDo:
    //
    // Create a method that returns a stream of
    // DataFrame objects, where each frame is built from
    // the coordinate, heart rate, grade, etc. that
    // occurs at each index in each of the corresponding
    // data arrays (e.g., heartRate, velocity, etc.)
    //
    public Stream<DataFrame> fusedFramesStream() {
        return Stream.empty();
    }


    // Don't change me!
    //
    // There is nothing to see here, move along.
    @JsonAnySetter
    public void setOther(String key, Object v) {
    }



}