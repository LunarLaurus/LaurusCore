package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing different push button actions on an HP iLO device.
 */
@AllArgsConstructor
@Getter
public enum IloPushType {

    /** A single press action */
    PRESS("Press"),

    /** Press and hold action */
    PRESS_AND_HOLD("PressAndHold");

    private final String value;

    /**
     * Retrieves the corresponding enum value for a given string.
     *
     * @param x The string representation of the push type.
     * @return The matching {@link IloPushType} value, or {@code PRESS} as the default.
     */
    public static IloPushType get(String x) {
        for (IloPushType type : values()) {
            if (type.value.equalsIgnoreCase(x)) {
                return type;
            }
        }
        return PRESS;
    }
}
