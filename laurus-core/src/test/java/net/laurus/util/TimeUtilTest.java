package net.laurus.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.ZonedDateTime;

class TimeUtilTest {

    @Test
    void testFormatUptime() {
        assertEquals("01:00:00", TimeUtil.formatUptime(Duration.ofHours(1)));
    }

    @Test
    void testFormatUptimeDetailed() {
        assertEquals("1 days, 01 hours, 00 minutes, 00 seconds", 
            TimeUtil.formatUptimeDetailed(Duration.ofDays(1).plusHours(1)));
    }

    @Test
    void testFormatUptimePrecise() {
        assertEquals("01:00:00.123", TimeUtil.formatUptimePrecise(3600123L));
    }

    @Test
    void testGetSystemBootTime() {
        ZonedDateTime bootTime = TimeUtil.getSystemBootTime(1000L);
        assertNotNull(bootTime, "Boot time should be calculated.");
    }
}

