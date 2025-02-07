package net.laurus.data.enums.ilo;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing the power regulation mode for an HP iLO device.
 */
@AllArgsConstructor
@Getter
public enum IloPowerRegulatorMode {

    /** Power regulation is controlled by the operating system */
    OS_CONTROL("OSControl"),

    /** Dynamic power regulation based on system load */
    DYNAMIC("Dynamic"),

    /** Maximum power consumption mode */
    MAX("Max"),

    /** Minimum power consumption mode */
    MIN("Min"),

    /** Unknown power regulation mode */
    UNKNOWN("Unknown");

    private final String name;

    /**
     * Parses a JSON node to retrieve a single power regulator mode.
     *
     * @param path The JSON node containing the power mode.
     * @return The matching {@link IloPowerRegulatorMode}, or {@code UNKNOWN} if not found.
     */
    public static IloPowerRegulatorMode from(JsonNode path) {
        for (IloPowerRegulatorMode mode : IloPowerRegulatorMode.values()) {
            if (mode.name.equalsIgnoreCase(path.asText("N/A"))) {
                return mode;
            }
        }
        return UNKNOWN;
    }

    /**
     * Parses a JSON node to retrieve a list of supported power regulator modes.
     *
     * @param path The JSON node containing multiple power modes.
     * @return A list of {@link IloPowerRegulatorMode} values.
     */
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
