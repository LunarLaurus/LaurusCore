package net.laurus.data.enums.ilo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing different physical sensor contexts in HP iLO.
 */
@Getter
@RequiredArgsConstructor
public enum IloSensorPhysicalContext {

    ROOM("Room"),
    INTAKE("Intake"),
    EXHAUST("Exhaust"),
    FRONT("Front"),
    BACK("Back"),
    UPPER("Upper"),
    LOWER("Lower"),
    CPU("CPU"),
    GPU("GPU"),
    BACKPLANE("Backplane"),
    SYSTEM_BOARD("SystemBoard"),
    POWER_SUPPLY("PowerSupply"),
    POWER_SUPPLY_BAY("PowerSupplyBay"),
    STORAGE_DEVICE("StorageDevice"),
    NETWORKING_DEVICE("NetworkingDevice"),
    COMPUTE_BAY("ComputeBay"),
    STORAGE_BAY("StorageBay"),
    NETWORK_BAY("NetworkBay"),
    EXPANSION_BAY("ExpansionBay"),
    VOLTAGE_REGULATOR("VoltageRegulator"),
    UNKNOWN("Unknown");

    private final String expectedJsonValue;

    /**
     * Retrieves the corresponding enum value for a given string.
     *
     * @param input The string representation of the sensor physical context.
     * @return The matching {@link IloSensorPhysicalContext} value, or {@code UNKNOWN} if not found.
     */
    public static IloSensorPhysicalContext fromString(String input) {
        if (input == null || input.isEmpty()) {
            return UNKNOWN;
        }
        for (IloSensorPhysicalContext context : IloSensorPhysicalContext.values()) {
            if (context.expectedJsonValue.equalsIgnoreCase(input)) {
                return context;
            }
        }
        return UNKNOWN;
    }
}
