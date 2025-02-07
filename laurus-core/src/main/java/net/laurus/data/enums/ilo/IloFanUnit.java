package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing the different measurement units for fan speed in HP iLO.
 */
@AllArgsConstructor
@Getter
public enum IloFanUnit {

    /** Revolutions per minute */
    RPM("RPM"),

    /** Fan speed as a percentage */
    PERCENT("%"),

    /** Unknown fan speed unit */
    UNKNOWN("unknown");

    private final String name;
}
