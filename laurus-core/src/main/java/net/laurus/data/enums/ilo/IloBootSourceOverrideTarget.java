package net.laurus.data.enums.ilo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloBootSourceOverrideTarget {

    NONE("None"),
    PXE("Pxe"),
    FLOPPY("Floppy"),
    CD("Cd"),
    USB("Usb"),
    HDD("Hdd"),
    BIOS_SETUP("BiosSetup"),
    UTILITIES("Utilities"),
    DIAGS("Diags"),
    UEFI_SHELL("UefiShell"),
    UEFI_TARGET("UefiTarget"),
    UNKNOWN("Unknown");

    private final String name;

    public static IloBootSourceOverrideTarget get(String x) {
        for (IloBootSourceOverrideTarget target : values()) {
            if (target.name.equalsIgnoreCase(x)) {
                return target;
            }
        }
        return UNKNOWN;
    }

	public static List<IloBootSourceOverrideTarget> getSupported(JsonNode path) {
		List<IloBootSourceOverrideTarget> list = new ArrayList<>();
		if (path.isArray()) {
			for (JsonNode arr : path) {
				for (IloBootSourceOverrideTarget mode : IloBootSourceOverrideTarget.values()) {
					if (mode.name.equalsIgnoreCase(arr.asText("N/A"))) {
						list.add(mode);
					}
				}				
			}
		}
		return list.isEmpty() ? List.of(UNKNOWN) : list;
	}
}
