package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing the virtual profile state in HP iLO.
 */
@AllArgsConstructor
@Getter
public enum IloVirtualProfile {

    /** The virtual profile is active */
    ACTIVE("Active"),

    /** The virtual profile is currently in a busy state */
    BUSY("Busy"),

    /** The virtual profile is inactive */
    INACTIVE("Inactive"),

    /** The virtual profile state is unknown */
    UNKNOWN("Unknown");

    private final String value;

    /**
     * Retrieves the corresponding enum value for a given string.
     *
     * @param x The string representation of the virtual profile state.
     * @return The matching {@link IloVirtualProfile} value, or {@code UNKNOWN} if not found.
     */
    public static IloVirtualProfile get(String x) {
        for (IloVirtualProfile profile : values()) {
            if (profile.value.equalsIgnoreCase(x)) {
                return profile;
            }
        }
        return UNKNOWN;
    }
}
