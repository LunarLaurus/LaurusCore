package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing different POST (Power-On Self-Test) modes in HP iLO.
 */
@AllArgsConstructor
@Getter
public enum IloPostMode {

    /** Normal POST mode */
    NORMAL("Normal"),

    /** POST proceeds to system shutdown */
    POST_TO_SHUTDOWN("PostToShutdown"),

    /** POST proceeds to system reboot */
    POST_TO_REBOOT("PostToReboot"),

    /** Unknown POST mode */
    UNKNOWN("Unknown");

    private final String value;

    /**
     * Retrieves the corresponding enum value for a given string.
     *
     * @param x The string representation of the POST mode.
     * @return The matching {@link IloPostMode} value, or {@code UNKNOWN} if not found.
     */
    public static IloPostMode get(String x) {
        for (IloPostMode mode : values()) {
            if (mode.value.equalsIgnoreCase(x)) {
                return mode;
            }
        }
        return UNKNOWN;
    }
}
