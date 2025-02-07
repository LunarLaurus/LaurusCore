package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing the different states of HP iLO device discovery.
 */
@AllArgsConstructor
@Getter
public enum IloDeviceDiscovery {

    /** Discovery process is ongoing */
    BUSY("Busy"),

    /** Auxiliary device discovery is complete */
    AUX_DEVICE_DISCOVERY_COMPLETE("vAuxDeviceDiscoveryComplete"),

    /** Main device discovery is complete */
    MAIN_DEVICE_DISCOVERY_COMPLETE("vMainDeviceDiscoveryComplete"),

    /** Data is incomplete due to partial discovery */
    DATA_INCOMPLETE("DataIncomplete"),

    /** Initial state before discovery starts */
    INITIAL("Initial"),

    /** Unknown discovery state */
    UNKNOWN("Unknown");

    private final String value;

    /**
     * Retrieves the corresponding enum value for a given device discovery state string.
     *
     * @param x The string representation of the discovery status.
     * @return The matching {@link IloDeviceDiscovery} value, or {@code UNKNOWN} if not found.
     */
    public static IloDeviceDiscovery get(String x) {
        for (IloDeviceDiscovery status : values()) {
            if (status.value.equalsIgnoreCase(x)) {
                return status;
            }
        }
        return UNKNOWN;
    }
}
