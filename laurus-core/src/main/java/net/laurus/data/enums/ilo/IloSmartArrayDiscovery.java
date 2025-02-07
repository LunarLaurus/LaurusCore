package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing the discovery process status of HP Smart Array controllers.
 */
@AllArgsConstructor
@Getter
public enum IloSmartArrayDiscovery {

    /** The discovery process is ongoing */
    BUSY("Busy"),

    /** Pending discovery for software RAID configuration */
    PENDING_SOFTWARE_RAID("PendingSoftwareRAID"),

    /** Discovery process has been completed */
    COMPLETE("Complete"),

    /** Initial state before discovery begins */
    INITIAL("Initial"),

    /** Discovery status is unknown */
    UNKNOWN("Unknown");

    private final String value;

    /**
     * Retrieves the corresponding enum value for a given string.
     *
     * @param x The string representation of the Smart Array discovery status.
     * @return The matching {@link IloSmartArrayDiscovery} value, or {@code UNKNOWN} if not found.
     */
    public static IloSmartArrayDiscovery get(String x) {
        for (IloSmartArrayDiscovery status : values()) {
            if (status.value.equalsIgnoreCase(x)) {
                return status;
            }
        }
        return UNKNOWN;
    }
}
