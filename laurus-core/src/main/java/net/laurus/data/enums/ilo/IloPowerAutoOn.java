package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing the automatic power-on behavior for HP iLO.
 */
@AllArgsConstructor
@Getter
public enum IloPowerAutoOn {

    /** Power auto-on setting is unknown */
    UNKNOWN("Unknown"),

    /** The system remains off when power is restored */
    REMAIN_OFF("RemainOff"),

    /** The system automatically powers on when power is restored */
    POWER_ON("PowerOn"),

    /** The system restores its previous power state upon power restoration */
    RESTORE("Restore");

    private final String value;

    /**
     * Retrieves the corresponding enum value for a given string.
     *
     * @param x The string representation of the power auto-on setting.
     * @return The matching {@link IloPowerAutoOn} value, or {@code UNKNOWN} if not found.
     */
    public static IloPowerAutoOn get(String x) {
        for (IloPowerAutoOn autoOn : values()) {
            if (autoOn.value.equalsIgnoreCase(x)) {
                return autoOn;
            }
        }
        return UNKNOWN;
    }
}
