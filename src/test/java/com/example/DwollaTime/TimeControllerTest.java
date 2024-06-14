package com.example.DwollaTime;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TimeControllerTest {

    private static ObjectMapper objectMapper;
    private static Time withoutOffset;
    private static Time withPlusTwoOffset;
    private static Time withMinusTwoOffset;
    private static Instant testInstant;
    private static Instant plusTwoHours;
    private static Instant minusTwoHours;

    @BeforeAll
    public static void setUp() throws IOException {
        objectMapper = new ObjectMapper();

        objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();

        withoutOffset = objectMapper
                .readValue(
                        new File("src/test/resources/withoutOffset.json"),
                        Time.class);

        withPlusTwoOffset = objectMapper
                .readValue(
                        new File("src/test/resources/withPlusTwoOffset.json"),
                        Time.class);

        withMinusTwoOffset = objectMapper
                .readValue(
                        new File("src/test/resources/withMinusTwoOffset.json"),
                        Time.class);

        //testInstant = java.time.Instant.parse("2024-06-13T22:40:30.867725Z");
        plusTwoHours = java.time.Instant.parse("2024-06-14T00:40:30.867725Z");

        testInstant = java.time.Instant.parse(withoutOffset.getCurrentTime());
        plusTwoHours = java.time.Instant.parse(withPlusTwoOffset.getAdjustedTime().get() +"Z"); //TODO FIX This
        minusTwoHours = java.time.Instant.parse(withMinusTwoOffset.getAdjustedTime().get() +"Z"); //TODO FIX This
    }

    @Test
    public void test_withBlankPostiveOffset()
    {
        Optional<String> empty = Optional.empty();
        Time testTime = new Time(testInstant, String.valueOf(" 02:00")); //Plus stripped off in browser

        assertEquals(testTime.getCurrentTime(), testInstant.toString());
        assertEquals(testTime.getAdjustedTime().isPresent(), true);
        assertEquals(testTime.getAdjustedTime().get() + "Z", plusTwoHours.toString());
    }

    @Test
    public void test_withPostiveOffset()
    {
        Time testTime = new Time(testInstant, String.valueOf("+02:00")); //Plus stripped off in browser

        assertEquals(testTime.getCurrentTime(), testInstant.toString());
        assertEquals(testTime.getAdjustedTime().isPresent(), true);
        assertEquals(testTime.getAdjustedTime().get() + "Z", plusTwoHours.toString());
    }

    @Test
    public void test_withNegativeOffset()
    {
        Time testTime = new Time(testInstant, String.valueOf("-02:00"));

        assertEquals(testTime.getCurrentTime(), testInstant.toString());
        assertEquals(testTime.getAdjustedTime().isPresent(), true);
        assertEquals(testTime.getAdjustedTime().get()  + "Z", minusTwoHours.toString());
    }

    @Test
    public void test_withOutsideRangeOffset()
    {
        Instant minusTwoHours = Instant.parse("2024-06-13T20:40:30.867725Z");
        Time testTime = new Time(testInstant, String.valueOf("+19:00"));

        assertEquals(testTime.getCurrentTime(), testInstant.toString());
        assertEquals(testTime.getAdjustedTime().isPresent(), false);
    }

    @Test
    public void test_withOutOffset()
    {
        Time testTime = new Time(testInstant, String.valueOf(""));

        assertEquals(testTime.getCurrentTime(), testInstant.toString());
        assertEquals(testTime.getAdjustedTime().isPresent(), false);
    }

    @Test
    public void test_withBadParamaterDataSign()
    {
        Time testTime = new Time(testInstant, String.valueOf("$02:00"));

        assertEquals(testTime.getCurrentTime(), testInstant.toString());
        assertEquals(testTime.getAdjustedTime().isPresent(), false);
    }

    @Test
    public void test_withBadParamaterData()
    {
        Time testTime = new Time(testInstant, String.valueOf("$03429!7#8*92879"));

        assertEquals(testTime.getCurrentTime(), testInstant.toString());
        assertEquals(testTime.getAdjustedTime().isPresent(), false);
    }
}
