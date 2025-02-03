package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloSmartArrayDiscovery {

    BUSY("Busy"),
    PENDING_SOFTWARE_RAID("PendingSoftwareRAID"),
    COMPLETE("Complete"),
    INITIAL("Initial"),
    UNKNOWN("Unknown");

    private final String value;

    public static IloSmartArrayDiscovery get(String x) {
        for (IloSmartArrayDiscovery status : values()) {
            if (status.value.equalsIgnoreCase(x)) {
                return status;
            }
        }
        return UNKNOWN;
    }
}
