package edu.vanderbilt.cs.streams;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses( { BikeRideTest.class, StreamUtilsTest.class, BikeStatsTest.class} )
public class AutograderSuite {

    // Note, this will only run correctly when you commit / push to GitHub

}
