package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloChassisType {

    RACK("Rack"),
    BLADE("Blade"),
    ENCLOSURE("Enclosure"),
    STAND_ALONE("StandAlone"),
    RACK_MOUNT("RackMount"),
    CARD("Card"),
    CARTRIDGE("Cartridge"),
    ROW("Row"),
    POD("Pod"),
    EXPANSION("Expansion"),
    SIDECAR("Sidecar"),
    ZONE("Zone"),
    SLED("Sled"),
    SHELF("Shelf"),
    OTHER("Other"),
    UNKNOWN("Unknown");

    private final String value;

    public static IloChassisType get(String x) {
        for (IloChassisType type : values()) {
            if (type.value.equalsIgnoreCase(x)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}

