package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing different states of the Power-On Self-Test (POST) process in HP iLO.
 */
@AllArgsConstructor
@Getter
public enum IloPostState {

    /** The POST state is unknown */
    UNKNOWN("Unknown"),

    /** The system is in a reset state */
    RESET("Reset"),

    /** The system is powered off */
    POWER_OFF("PowerOff"),

    /** The system is currently running POST */
    IN_POST("InPost"),

    /** POST discovery phase has been completed */
    IN_POST_DISCOVERY_COMPLETE("InPostDiscoveryComplete"),

    /** POST has been completed successfully */
    FINISHED_POST("FinishedPost");

    private final String value;

    /**
     * Retrieves the corresponding enum value for a given string.
     *
     * @param x The string representation of the POST state.
     * @return The matching {@link IloPostState} value, or {@code UNKNOWN} if not found.
     */
    public static IloPostState get(String x) {
        for (IloPostState state : values()) {
            if (state.value.equalsIgnoreCase(x)) {
                return state;
            }
        }
        return UNKNOWN;
    }
}
