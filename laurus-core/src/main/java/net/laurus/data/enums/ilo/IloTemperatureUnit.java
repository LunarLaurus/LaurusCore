package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing temperature measurement units in HP iLO.
 */
@AllArgsConstructor
@Getter
public enum IloTemperatureUnit {

    /** Temperature measured in Celsius (°C) */
    CELSIUS("C"),

    /** Temperature measured in Fahrenheit (°F) */
    FAHRENHEIT("F"),

    /** Temperature measured in Kelvin (K) */
    KELVIN("K"),

    /** Unknown temperature unit */
    UNKNOWN("?");

    private final String symbol;
}
