package net.laurus.data.enums.ilo;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing possible boot sources for HP iLO.
 */
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

    /**
     * Retrieves the corresponding enum value for a given string.
     *
     * @param x The string representation of the boot source.
     * @return The corresponding {@link IloBootSourceOverrideTarget} value, or {@code UNKNOWN} if not found.
     */
    public static IloBootSourceOverrideTarget get(String x) {
        for (IloBootSourceOverrideTarget target : values()) {
            if (target.name.equalsIgnoreCase(x)) {
                return target;
            }
        }
        return UNKNOWN;
    }

    /**
     * Parses a JSON node to retrieve supported boot sources.
     *
     * @param path The JSON node containing supported sources.
     * @return A list of {@link IloBootSourceOverrideTarget} values.
     */
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
