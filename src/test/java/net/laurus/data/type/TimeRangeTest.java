package net.laurus.data.type;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

public class TimeRangeTest {

    @Test
    void testConstructorWithPeriodAndScale() {
        TimeRange timeRange = new TimeRange(5, ChronoUnit.DAYS);

        assertEquals(5, timeRange.getPeriod());
        assertEquals(ChronoUnit.DAYS, timeRange.getScale());
        assertEquals(Duration.ofDays(5), timeRange.getDuration());
    }

    @Test
    void testConstructorWithStartAndEnd() {
        LocalDateTime start = LocalDateTime.now().minusDays(5);
        LocalDateTime end = LocalDateTime.now();

        TimeRange timeRange = new TimeRange(start, end);

        assertEquals(ChronoUnit.SECONDS, timeRange.getScale());
        assertEquals(Duration.between(start, end), timeRange.getDuration());
    }

    @Test
    void testGetStartTimeAndEndTime() {
        TimeRange timeRange = new TimeRange(2, ChronoUnit.DAYS);

        assertNotNull(timeRange.getStartTime());
        assertNotNull(timeRange.getEndTime());
        assertTrue(timeRange.getStartTime().isBefore(timeRange.getEndTime()));
    }

    @Test
    void testCalculateDurationUnsupportedChronoUnit() {
        TimeRange timeRange = new TimeRange(1, ChronoUnit.ERAS);

        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, timeRange::getDuration);
        assertEquals("ChronoUnit.ERAS is not supported for calculating duration.", exception.getMessage());
    }
}
