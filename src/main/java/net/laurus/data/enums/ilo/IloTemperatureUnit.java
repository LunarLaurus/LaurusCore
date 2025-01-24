package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloTemperatureUnit {
	
	CELSIUS("C"),
	FAHRENHEIT("F"),
	KELVIN("K"),
	UNKNOWN("?");

    String symbol;
}
