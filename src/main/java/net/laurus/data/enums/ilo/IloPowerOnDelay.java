package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloPowerOnDelay {
	
    UNKNOWN("Unknown"),
    MINIMUM("Minimum"),
    SEC_15("15Sec"),
    SEC_30("30Sec"),
    SEC_45("45Sec"),
    SEC_60("60Sec"),
    RANDOM_UP_TO_120_SEC("RandomUpTo120Sec");

    private final String value;

    public static IloPowerOnDelay get(String x) {
        for (IloPowerOnDelay delay : values()) {
            if (delay.value.equalsIgnoreCase(x)) {
                return delay;
            }
        }
        return UNKNOWN;
    }
}