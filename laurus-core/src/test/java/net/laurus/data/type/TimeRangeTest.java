package net.laurus.data.type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

class TimeRangeTest {

    @Test
    void testConstructorWithDefaultScale() {
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 1, 2, 0, 0);

        TimeRange timeRange = new TimeRange(start, end);

        assertEquals(start, timeRange.getStartTime(), "Start time should match the input.");
        assertEquals(end, timeRange.getEndTime(), "End time should match the input.");
        assertEquals(Duration.ofDays(1), timeRange.getDuration(), "Duration should be 1 day.");
        assertEquals(ChronoUnit.SECONDS, timeRange.getScale(), "Default scale should be SECONDS.");
    }

    @Test
    void testConstructorWithCustomScale() {
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 1, 2, 0, 0);

        TimeRange timeRange = new TimeRange(start, end, ChronoUnit.HOURS);

        assertEquals(24, timeRange.getDurationInScale(), "Duration in hours should be 24.");
        assertEquals(ChronoUnit.HOURS, timeRange.getScale(), "Scale should match the specified ChronoUnit.");
    }

    @Test
    void testDurationInScale_Seconds() {
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 1, 1, 0, 1);

        TimeRange timeRange = new TimeRange(start, end);

        assertEquals(60, timeRange.getDurationInScale(), "Duration in seconds should be 60.");
    }

    @Test
    void testDurationInScale_Minutes() {
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 1, 1, 1, 0);

        TimeRange timeRange = new TimeRange(start, end, ChronoUnit.MINUTES);

        assertEquals(60, timeRange.getDurationInScale(), "Duration in minutes should be 60.");
    }

    @Test
    void testInstantConversion() {
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 1, 2, 0, 0);

        TimeRange timeRange = new TimeRange(start, end);

        assertEquals(start.atZone(ZoneId.systemDefault()).toInstant(), timeRange.toStartInstant(), "Start instant should match.");
        assertEquals(end.atZone(ZoneId.systemDefault()).toInstant(), timeRange.toEndInstant(), "End instant should match.");
    }

    @Test
    void testToString() {
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 1, 2, 0, 0);

        TimeRange timeRange = new TimeRange(start, end);

        String expected = "TimeRange [2023-01-01T00:00 to 2023-01-02T00:00, duration: PT24H, scale: SECONDS]";
        assertEquals(expected, timeRange.toString(), "String representation should match.");
    }

    @Test
    void testInvalidConstructor_NullStart() {
        LocalDateTime end = LocalDateTime.of(2023, 1, 2, 0, 0);

        assertThrows(NullPointerException.class, () -> new TimeRange(null, end), "NullPointerException should be thrown for null start time.");
    }

    @Test
    void testInvalidConstructor_NullEnd() {
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);

        assertThrows(NullPointerException.class, () -> new TimeRange(start, null), "NullPointerException should be thrown for null end time.");
    }

    @Test
    void testInvalidConstructor_StartAfterEnd() {
        LocalDateTime start = LocalDateTime.of(2023, 1, 2, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 1, 1, 0, 0);

        assertThrows(IllegalArgumentException.class, () -> new TimeRange(start, end), "IllegalArgumentException should be thrown when start time is after end time.");
    }

    @Test
    void testInvalidConstructor_NullScale() {
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 1, 2, 0, 0);

        assertThrows(NullPointerException.class, () -> new TimeRange(start, end, null), "NullPointerException should be thrown for null scale.");
    }

    @Test
    void testInvalidConstructor_NonTimeBasedScale() {
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 1, 2, 0, 0);

        assertThrows(IllegalArgumentException.class, () -> new TimeRange(start, end, ChronoUnit.ERAS), "IllegalArgumentException should be thrown for non-time-based scale.");
    }
}
