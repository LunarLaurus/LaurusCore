package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloDeviceDiscovery {

    BUSY("Busy"),
    AUX_DEVICE_DISCOVERY_COMPLETE("vAuxDeviceDiscoveryComplete"),
    MAIN_DEVICE_DISCOVERY_COMPLETE("vMainDeviceDiscoveryComplete"),
    DATA_INCOMPLETE("DataIncomplete"),
    INITIAL("Initial"),
    UNKNOWN("Unknown");

    private final String value;

    public static IloDeviceDiscovery get(String x) {
        for (IloDeviceDiscovery status : values()) {
            if (status.value.equalsIgnoreCase(x)) {
                return status;
            }
        }
        return UNKNOWN;
    }
}