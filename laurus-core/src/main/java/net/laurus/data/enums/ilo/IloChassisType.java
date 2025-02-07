package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing different chassis types for HP iLO (Integrated Lights-Out).
 * <p>
 * Chassis type describes the form factor and physical configuration of the device.
 */
@AllArgsConstructor
@Getter
public enum IloChassisType {

    /** Rack-mounted server chassis */
    RACK("Rack"),

    /** Blade server chassis */
    BLADE("Blade"),

    /** Enclosure chassis that houses multiple blade servers */
    ENCLOSURE("Enclosure"),

    /** Standalone server chassis */
    STAND_ALONE("StandAlone"),

    /** Rack-mounted server that is not a blade */
    RACK_MOUNT("RackMount"),

    /** Expansion card chassis */
    CARD("Card"),

    /** Cartridge-based server chassis */
    CARTRIDGE("Cartridge"),

    /** Row of chassis units */
    ROW("Row"),

    /** Data center pod containing multiple rows of servers */
    POD("Pod"),

    /** Expansion chassis */
    EXPANSION("Expansion"),

    /** Sidecar chassis for additional components */
    SIDECAR("Sidecar"),

    /** Zone-based chassis classification */
    ZONE("Zone"),

    /** Sled-based server chassis */
    SLED("Sled"),

    /** Shelf-based chassis for modular components */
    SHELF("Shelf"),

    /** Other unknown chassis type */
    OTHER("Other"),

    /** Unrecognized chassis type */
    UNKNOWN("Unknown");

    private final String value;

    /**
     * Retrieves the corresponding enum value for a given chassis type string.
     *
     * @param x The string representation of the chassis type.
     * @return The matching {@link IloChassisType} value, or {@code UNKNOWN} if not found.
     */
    public static IloChassisType get(String x) {
        for (IloChassisType type : values()) {
            if (type.value.equalsIgnoreCase(x)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
