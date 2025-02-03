package net.laurus.data.enums.ilo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloPowerRegulatorMode {

	OS_CONTROL("OSControl"),
	DYNAMIC("Dynamic"),
	MAX("Max"),
	MIN("Min"),
	UNKNOWN("Unknown");
	
	private final String name;

	public static IloPowerRegulatorMode from(JsonNode path) {
		for (IloPowerRegulatorMode mode : IloPowerRegulatorMode.values()) {
			if (mode.name.equalsIgnoreCase(path.asText("N/A"))) {
				return mode;
			}
		}
		return UNKNOWN;
	}

	public static List<IloPowerRegulatorMode> getSupported(JsonNode path) {
		List<IloPowerRegulatorMode> list = new ArrayList<>();
		if (path.isArray()) {
			for (JsonNode arr : path) {
				for (IloPowerRegulatorMode mode : IloPowerRegulatorMode.values()) {
					if (mode.name.equalsIgnoreCase(arr.asText("N/A"))) {
						list.add(mode);
					}
				}				
			}
		}
		return list.isEmpty() ? List.of(UNKNOWN) : list;
	}
	
}
