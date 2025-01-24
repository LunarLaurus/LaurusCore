package net.laurus.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MathUtilTest {

    @Test
    void testGetFanSpeedFromPercentageOf100() {
        assertEquals(128, MathUtil.getFanSpeedFromPercentageOf100(50));
        assertThrows(IllegalArgumentException.class, () -> MathUtil.getFanSpeedFromPercentageOf100(101));
    }

    @Test
    void testClamp() {
        assertEquals(10, MathUtil.clamp(10, 0, 20));
        assertEquals(20, MathUtil.clamp(25, 0, 20));
    }
}
