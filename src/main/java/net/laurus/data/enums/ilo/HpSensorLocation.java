package net.laurus.data.enums.ilo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HpSensorLocation {
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

    public static HpSensorLocation fromString(String input) {
        if (input == null || input.isEmpty()) {
            return UNKNOWN;
        }
        for (HpSensorLocation location : HpSensorLocation.values()) {
            if (location.expectedJsonValue.equalsIgnoreCase(input)) {
                return location;
            }
        }
        return UNKNOWN;
    }
}