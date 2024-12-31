package net.laurus.data.enums.ilo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloTemperatureUnit implements Serializable {
	
	CELSIUS("C"),
	FAHRENHEIT("F"),
	KELVIN("K"),
	UNKNOWN("?");

    String symbol;
}
