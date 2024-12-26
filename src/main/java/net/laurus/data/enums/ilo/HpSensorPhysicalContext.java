package net.laurus.data.enums.ilo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HpSensorPhysicalContext {
	
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

    public static HpSensorPhysicalContext fromString(String input) {
        if (input == null || input.isEmpty()) {
            return UNKNOWN;
        }
        for (HpSensorPhysicalContext context : HpSensorPhysicalContext.values()) {
            if (context.expectedJsonValue.equalsIgnoreCase(input)) {
                return context;
            }
        }
        return UNKNOWN;
    }
}

