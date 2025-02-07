package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing the delay before powering on an HP iLO device.
 */
@AllArgsConstructor
@Getter
public enum IloPowerOnDelay {
    
    /** Unknown power-on delay setting */
    UNKNOWN("Unknown"),

    /** Minimum delay before powering on */
    MINIMUM("Minimum"),

    /** 15-second delay before powering on */
    SEC_15("15Sec"),

    /** 30-second delay before powering on */
    SEC_30("30Sec"),

    /** 45-second delay before powering on */
    SEC_45("45Sec"),

    /** 60-second delay before powering on */
    SEC_60("60Sec"),

    /** Random delay up to 120 seconds before powering on */
    RANDOM_UP_TO_120_SEC("RandomUpTo120Sec");

    private final String value;

    /**
     * Retrieves the corresponding enum value for a given string.
     *
     * @param x The string representation of the power-on delay.
     * @return The matching {@link IloPowerOnDelay} value, or {@code UNKNOWN} if not found.
     */
    public static IloPowerOnDelay get(String x) {
        for (IloPowerOnDelay delay : values()) {
            if (delay.value.equalsIgnoreCase(x)) {
                return delay;
            }
        }
        return UNKNOWN;
    }
}
