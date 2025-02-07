package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing the different states of an indicator LED in HP iLO.
 */
@AllArgsConstructor
@Getter
public enum IloIndicatorLED {

    /** LED state is unknown */
    UNKNOWN("Unknown"),

    /** LED is on */
    LIT("Lit"),

    /** LED is blinking */
    BLINKING("Blinking"),

    /** LED is off */
    OFF("Off");

    private final String value;

    /**
     * Retrieves the corresponding enum value for a given LED state string.
     *
     * @param x The string representation of the LED state.
     * @return The matching {@link IloIndicatorLED} value, or {@code UNKNOWN} if not found.
     */
    public static IloIndicatorLED get(String x) {
        for (IloIndicatorLED state : values()) {
            if (state.value.equalsIgnoreCase(x)) {
                return state;
            }
        }
        return UNKNOWN;
    }
}
