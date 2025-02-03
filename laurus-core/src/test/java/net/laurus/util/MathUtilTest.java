package net.laurus.util;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MathUtilTest {

    @Test
    void testGetFanSpeedFromPercentageOf100_ValidInput() {
        // Edge cases
        assertEquals(0, MathUtil.getFanSpeedFromPercentageOf100(0), "Fan speed for 0% should be 0.");
        assertEquals(255, MathUtil.getFanSpeedFromPercentageOf100(100), "Fan speed for 100% should be 255.");

        // Middle cases
        assertEquals(128, MathUtil.getFanSpeedFromPercentageOf100(50), "Fan speed for 50% should be 128.");
        assertEquals(191, MathUtil.getFanSpeedFromPercentageOf100(75), "Fan speed for 75% should be 191.");
    }

    @Test
    void testGetFanSpeedFromPercentageOf100_InvalidInput() {
        // Below valid range
        assertThrows(IllegalArgumentException.class, () -> MathUtil.getFanSpeedFromPercentageOf100(-1),
                "Should throw for percentages below 0.");

        // Above valid range
        assertThrows(IllegalArgumentException.class, () -> MathUtil.getFanSpeedFromPercentageOf100(101),
                "Should throw for percentages above 100.");
    }

    @Test
    void testClamp_Int() {
        // Within bounds
        assertEquals(10, MathUtil.clamp(10, 0, 20), "Should return the value when it's within bounds.");

        // Below bounds
        assertEquals(0, MathUtil.clamp(-5, 0, 20), "Should return min when the value is below bounds.");

        // Above bounds
        assertEquals(20, MathUtil.clamp(25, 0, 20), "Should return max when the value is above bounds.");
    }

    @Test
    void testClamp_Long() {
        // Within bounds
        assertEquals(10L, MathUtil.clamp(10L, 0L, 20L), "Should return the value when it's within bounds.");

        // Below bounds
        assertEquals(0L, MathUtil.clamp(-5L, 0L, 20L), "Should return min when the value is below bounds.");

        // Above bounds
        assertEquals(20L, MathUtil.clamp(25L, 0L, 20L), "Should return max when the value is above bounds.");
    }

    @Test
    void testClamp_Float() {
        // Within bounds
        assertEquals(10.5f, MathUtil.clamp(10.5f, 0f, 20f), "Should return the value when it's within bounds.");

        // Below bounds
        assertEquals(0f, MathUtil.clamp(-5f, 0f, 20f), "Should return min when the value is below bounds.");

        // Above bounds
        assertEquals(20f, MathUtil.clamp(25f, 0f, 20f), "Should return max when the value is above bounds.");
    }

    @Test
    void testClamp_Double() {
        // Within bounds
        assertEquals(10.5, MathUtil.clamp(10.5, 0.0, 20.0), "Should return the value when it's within bounds.");

        // Below bounds
        assertEquals(0.0, MathUtil.clamp(-5.0, 0.0, 20.0), "Should return min when the value is below bounds.");

        // Above bounds
        assertEquals(20.0, MathUtil.clamp(25.0, 0.0, 20.0), "Should return max when the value is above bounds.");
    }
}
