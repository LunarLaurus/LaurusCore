package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloSystemType {

    PHYSICAL("Physical"),
    VIRTUAL("Virtual"),
    OS("OS"),
    PHYSICALLY_PARTITIONED("PhysicallyPartitioned"),
    VIRTUALLY_PARTITIONED("VirtuallyPartitioned"),
    UNKNOWN("Unknown");

    private final String value;

    public static IloSystemType get(String x) {
        for (IloSystemType type : values()) {
            if (type.value.equalsIgnoreCase(x)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}

