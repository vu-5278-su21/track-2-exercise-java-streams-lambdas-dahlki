package edu.vanderbilt.cs.streams;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import edu.vanderbilt.cs.streams.BikeRide.LatLng;

public class BikeStats {

    private BikeRide ride;

    public BikeStats(BikeRide ride) {
        this.ride = ride;
    }

    /**
     * @ToDo:
     *
     * Create a stream of DataFrames representing the average of the
     * sliding windows generated from the given window size.
     *
     * For example, if a windowSize of 3 was provided, the BikeRide.DataFrames
     * would be fetched with the BikeRide.fusedFramesStream() method. These
     * frames would be divided into sliding windows of size 3 using the
     * StreamUtils.slidingWindow() method. Each sliding window would be a
     * list of 3 DataFrame objects. You would produce a new DataFrame for
     * each window by averaging the grade, altitude, velocity, and heart
     * rate for the 3 DataFrame objects.
     *
     * For each window, you should use the coordinate of the first DataFrame in the window
     * for the location.
     *
     * @param windowSize
     * @return
     */
    public Stream<BikeRide.DataFrame> averagedDataFrameStream(int windowSize){
        return StreamUtils.slidingWindow(this.ride.fusedFramesStream()
                .collect(Collectors.toList()), windowSize)
                .map(this::getAverage);
    }

    private BikeRide.DataFrame getAverage(List<BikeRide.DataFrame> dataFrames) {
        Double velocityAverage = StreamUtils.averageOfProperty(BikeRide.DataFrame::getVelocity).apply(dataFrames);
        Double heartRateAverage = StreamUtils.averageOfProperty(BikeRide.DataFrame::getHeartRate).apply(dataFrames);
        Double gradeAverage = StreamUtils.averageOfProperty(BikeRide.DataFrame::getGrade).apply(dataFrames);
        Double altitudeAverage = StreamUtils.averageOfProperty(BikeRide.DataFrame::getAltitude).apply(dataFrames);
        LatLng coordinate = dataFrames.get(0).getCoordinate();

        return new BikeRide.DataFrame(coordinate, gradeAverage, altitudeAverage, velocityAverage, heartRateAverage);
    }

    // @ToDo:
    //
    // Determine the stream of unique locations that the
    // rider stopped. A location is unique if there are no
    // other stops at the same latitude / longitude.
    // The rider is stopped if velocity = 0.
    //
    // For the purposes of this assignment, you should use
    // LatLng.equals() to determine if two locations are
    // the same.
    //
    public Stream<LatLng> locationsOfStops() {
        Set<LatLng> latLngsSet = new HashSet<>();

        this.ride.fusedFramesStream().filter(dataFrame -> dataFrame.getVelocity() == 0).collect(Collectors.toList()).removeIf(dataFrame -> !latLngsSet.add(dataFrame.getCoordinate()));
        this.ride.fusedFramesStream()
                .filter(dataFrame -> dataFrame.getVelocity() == 0)
                .filter(distinctBy(dataFrame -> createLatLng(dataFrame.getCoordinate())))
                .forEach(dataFrame -> latLngsSet.add(dataFrame.getCoordinate()));
        return latLngsSet.stream();
    }

    public static <T> Predicate<T> distinctBy(Function<? super T, ?> f) {
        Set<Object> unique = new HashSet<>();
        return t -> unique.add(f.apply(t));
    }

    private static String createLatLng(LatLng latLng) {
        return String.valueOf(latLng.latitude) + latLng.longitude;
    }

}
