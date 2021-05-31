package edu.vanderbilt.cs.streams;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class StreamUtilsTest {

    public static BikeRide loadSampleRide() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new FileInputStream("src/main/resources/data.json"), BikeRide.class);
    }

    @Test
    public void testSlidingWindow(){
        List<Double> vals = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0);

        assertEquals(0, StreamUtils.slidingWindow(vals, Integer.MIN_VALUE).count());
        assertEquals(0, StreamUtils.slidingWindow(vals, -1).count());
        assertEquals(0, StreamUtils.slidingWindow(vals, 0).count());
        assertEquals(8, StreamUtils.slidingWindow(vals, 1).count());
        assertEquals(7, StreamUtils.slidingWindow(vals, 2).count());
        assertEquals(6, StreamUtils.slidingWindow(vals, 3).count());
        assertEquals(5, StreamUtils.slidingWindow(vals, 4).count());
        assertEquals(4, StreamUtils.slidingWindow(vals, 5).count());
        assertEquals(3, StreamUtils.slidingWindow(vals, 6).count());
        assertEquals(2, StreamUtils.slidingWindow(vals, 7).count());
        assertEquals(1, StreamUtils.slidingWindow(vals, 8).count());
        assertEquals(0, StreamUtils.slidingWindow(vals, 9).count());
        assertEquals(0, StreamUtils.slidingWindow(vals, Integer.MAX_VALUE).count());

        List<Double> sublists = StreamUtils.slidingWindow(vals, 2)
                                        .mapToDouble(v -> v.stream()
                                                .mapToDouble(d -> d)
                                                .average()
                                                .getAsDouble())
                                        .boxed()
                                        .collect(Collectors.toList());

        assertEquals(1.5, sublists.get(0));
        assertEquals(2.5, sublists.get(1));
        assertEquals(3.5, sublists.get(2));
        assertEquals(4.5, sublists.get(3));
        assertEquals(5.5, sublists.get(4));
        assertEquals(6.5, sublists.get(5));
        assertEquals(7.5, sublists.get(6));

        List<Double> sublists2 = StreamUtils.slidingWindow(vals, 3)
                .mapToDouble(v -> v.stream()
                        .mapToDouble(d -> d)
                        .average()
                        .getAsDouble())
                .boxed()
                .collect(Collectors.toList());

        assertEquals(2.0, sublists2.get(0));
        assertEquals(3.0, sublists2.get(1));
        assertEquals(4.0, sublists2.get(2));
        assertEquals(5.0, sublists2.get(3));
        assertEquals(6.0, sublists2.get(4));
        assertEquals(7.0, sublists2.get(5));
    }

    @Test
    public void testAverageOfProperty(){
        List<String> data = Arrays.asList("a","ab","abc","abcd","abcde");
        assertEquals(3, StreamUtils.averageOfProperty(String::length).apply(data));

        List<UUID> data2 = IntStream.range(0, 10).mapToObj(i -> UUID.randomUUID()).collect(Collectors.toList());
        assertEquals(36.0, StreamUtils.averageOfProperty((UUID v) -> v.toString().length()).apply(data2));
    }
}
