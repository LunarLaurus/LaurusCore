package net.laurus.data.enums.ilo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PowerRegulatorMode {

	OS_CONTROL("OSControl"),
	DYNAMIC("Dynamic"),
	MAX("Max"),
	MIN("Min"),
	UNKNOWN("Unknown");
	
	private final String name;

	public static PowerRegulatorMode from(JsonNode path) {
		for (PowerRegulatorMode mode : PowerRegulatorMode.values()) {
			if (mode.name.equalsIgnoreCase(path.asText("N/A"))) {
				return mode;
			}
		}
		return UNKNOWN;
	}

	public static List<PowerRegulatorMode> getSupported(JsonNode path) {
		List<PowerRegulatorMode> list = new ArrayList<>();
		if (path.isArray()) {
			for (JsonNode arr : path) {
				for (PowerRegulatorMode mode : PowerRegulatorMode.values()) {
					if (mode.name.equalsIgnoreCase(arr.asText("N/A"))) {
						list.add(mode);
					}
				}				
			}
		}
		return list.isEmpty() ? List.of(UNKNOWN) : list;
	}
	
}
