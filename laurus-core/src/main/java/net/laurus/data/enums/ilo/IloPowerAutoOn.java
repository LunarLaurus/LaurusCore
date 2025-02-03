package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloPowerAutoOn {
	
    UNKNOWN("Unknown"),
    REMAIN_OFF("RemainOff"),
    POWER_ON("PowerOn"),
    RESTORE("Restore");

    private final String value;

    public static IloPowerAutoOn get(String x) {
        for (IloPowerAutoOn autoOn : values()) {
            if (autoOn.value.equalsIgnoreCase(x)) {
                return autoOn;
            }
        }
        return UNKNOWN;
    }
}
