package net.laurus.data.enums.ilo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing different sensor locations within an HP iLO system.
 */
@Getter
@RequiredArgsConstructor
public enum IloSensorLocation {

    SYSTEM("System"),
    SYSTEM_BOARD("System Board"),
    IO_BOARD("I/O Board"),
    CPU("CPU"),
    MEMORY("Memory"),
    STORAGE("Storage"),
    REMOVABLE_MEDIA("Removable Media"),
    POWER_SUPPLY("Power Supply"),
    AMBIENT("Ambient"),
    CHASSIS("Chassis"),
    BRIDGE_BOARD("Bridge Board"),
    EXHAUST("Exhaust"),
    PROCESSOR_BAY("Processor Bay"),
    IO_BAY("IO Bay"),
    BLADE_SLOT("Blade Slot"),
    VIRTUAL("Virtual"),
    UNKNOWN("Unknown");

    private final String expectedJsonValue;

    /**
     * Retrieves the corresponding enum value for a given string.
     *
     * @param input The string representation of the sensor location.
     * @return The matching {@link IloSensorLocation} value, or {@code UNKNOWN} if not found.
     */
    public static IloSensorLocation fromString(String input) {
        if (input == null || input.isEmpty()) {
            return UNKNOWN;
        }
        for (IloSensorLocation location : IloSensorLocation.values()) {
            if (location.expectedJsonValue.equalsIgnoreCase(input)) {
                return location;
            }
        }
        return UNKNOWN;
    }
}
