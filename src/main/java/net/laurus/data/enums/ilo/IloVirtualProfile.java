package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloVirtualProfile {
	
    ACTIVE("Active"),
    BUSY("Busy"),
    INACTIVE("Inactive"),
    UNKNOWN("Unknown");

    private final String value;

    public static IloVirtualProfile get(String x) {
        for (IloVirtualProfile profile : values()) {
            if (profile.value.equalsIgnoreCase(x)) {
                return profile;
            }
        }
        return UNKNOWN;
    }
}
