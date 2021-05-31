package edu.vanderbilt.cs.streams;

import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamUtils {

    // @ToDo:
    //
    // Create a method that returns a stream of sliding windows of <windowSize>
    // data points. You should only return complete windows. Each window should be
    // a list of the data points present at that step of the window sliding.
    public static <T> Stream<List<T>> slidingWindow(List<T> data, int windowSize){

        // Let's assume that we have
        // data = a, b, c, d, e, f
        //
        // We denote sliding windows as |x, y, z|
        //
        // With a windowSize of 3, the windows for this
        // data would be:

        // |a, b, c| d, e, f

        // a, |b, c, d| e f

        // a, b, |c, d, e| f

        // a, b, c, |d, e, f|

        // Hints:
        //
        // 1. This is solvable in ~3-4 lines of code
        // 2. Think of how you could use IntStream.range()
        // 3. List.subLIst will be useful to you
        // 4. A windowSize < 1 should return an empty stream

        return Stream.empty();
    }

    /**
     * @ToDo:
     *
     * This is a higher-order function that produces an averaging function that
     * operates on a List of objects.
     *
     * The function takes a "property function" as a parameter. The property function should
     * return the property value associated with the object that should contribute to the
     * average. For example, you could provide a function that extracts the heart
     * rate data from a DataFrame.
     *
     * The function returns a new function that computes the average of a list
     * of objects using the provided property function to extract a property
     * value for each object.
     *
     * You should look at StreamUtilsTest.testAverageOfProperty() to better
     * understand what this needs to do.
     *
     *
     * @param f
     * @param <T>
     * @return
     */
    public static <T> Function<List<T>, Double> averageOfProperty(ToDoubleFunction<T> f){
        return (List<T> window) -> {
            // You need to update this code here to
            // return the average of the property that
            // is extracted with the function `f`
            return 0.0;
        };
    }

}
