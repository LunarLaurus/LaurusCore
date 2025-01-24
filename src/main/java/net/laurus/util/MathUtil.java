package net.laurus.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MathUtil {

	public static int getFanSpeedFromPercentageOf100(float input) {
        if (input < 0 || input > 100) {
            throw new IllegalArgumentException("Input must be between 0 and 100. Input: "+input);
        }
        return Math.round((input / 100) * 255);
    }
	
    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }

    public static long clamp(long value, long min, long max) {
        return Math.max(min, Math.min(value, max));
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }
	
}
