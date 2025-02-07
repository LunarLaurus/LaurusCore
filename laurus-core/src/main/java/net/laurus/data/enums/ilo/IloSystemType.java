package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing the system type of an HP iLO device.
 */
@AllArgsConstructor
@Getter
public enum IloSystemType {

    /** A physical system */
    PHYSICAL("Physical"),

    /** A virtualized system */
    VIRTUAL("Virtual"),

    /** An operating system (OS) instance */
    OS("OS"),

    /** A physically partitioned system */
    PHYSICALLY_PARTITIONED("PhysicallyPartitioned"),

    /** A virtually partitioned system */
    VIRTUALLY_PARTITIONED("VirtuallyPartitioned"),

    /** The system type is unknown */
    UNKNOWN("Unknown");

    private final String value;

    /**
     * Retrieves the corresponding enum value for a given string.
     *
     * @param x The string representation of the system type.
     * @return The matching {@link IloSystemType} value, or {@code UNKNOWN} if not found.
     */
    public static IloSystemType get(String x) {
        for (IloSystemType type : values()) {
            if (type.value.equalsIgnoreCase(x)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
