package net.laurus.data.dto.ipmi.ilo;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.laurus.data.enums.ilo.IloObjectHealth;
import net.laurus.data.enums.ilo.IloObjectState;
import net.laurus.interfaces.NetworkData;
import net.laurus.interfaces.update.ilo.IloUpdatableFeatureWithoutAuth;
import net.laurus.util.JsonUtil;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IloPowerSupplyObject implements IloUpdatableFeatureWithoutAuth {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();
    private static final String NA = "N/A";

    boolean pluggedIn;
    String firmwareVersion;
    int lastPowerOutputWatts;
    int lineInputVoltage;
    String lineInputVoltageType;
    String model;
    int powerCapacity;
    int bayNumber;
    boolean hotplugCapable;
    String type;
    String serialNumber;
    String sparePartNumber;
    IloObjectHealth health;
    IloObjectState state;
    long lastUpdateTime;

    public static IloPowerSupplyObject from(@NonNull JsonNode node) {

        JsonNode oem_hp = node.path("Oem").path("Hp");
        int bayNumber = oem_hp.path("BayNumber").asInt();
        boolean hotPlug = oem_hp.path("HotplugCapable").asBoolean();

        JsonNode statusNode = node.path("Status");
        IloObjectHealth health = IloObjectHealth.valueOf(statusNode.path("Health").asText().toUpperCase());
        IloObjectState state = IloObjectState.valueOf(statusNode.path("State").asText().toUpperCase());

        String fwVersion = JsonUtil.getSafeTextValueFromNode(node, "FirmwareVersion");
        int lastOutput = JsonUtil.getSafeIntValueFromNode(node, "LastPowerOutputWatts");
        int inputVoltage = JsonUtil.getSafeIntValueFromNode(node, "LineInputVoltage");
        String inputType = JsonUtil.getSafeTextValueFromNode(node, "LineInputVoltageType");
        String model = JsonUtil.getSafeTextValueFromNode(node, "Model");

        int capacity = JsonUtil.getSafeIntValueFromNode(node, "PowerCapacityWatts");
        String type = JsonUtil.getSafeTextValueFromNode(node, "PowerSupplyType");
        String serial = JsonUtil.getSafeTextValueFromNode(node, "SerialNumber");
        String spare = JsonUtil.getSafeTextValueFromNode(node, "SparePartNumber");

        boolean plugged = (NA.equals(fwVersion) || NA.equals(model) || NA.equals(serial) || state.equals(IloObjectState.ABSENT)) ? false : true;        
        
        return IloPowerSupplyObject.builder()
                .bayNumber(bayNumber)
                .firmwareVersion(fwVersion)
                .health(health)
                .hotplugCapable(hotPlug)
                .lastPowerOutputWatts(lastOutput)
                .lastUpdateTime(System.currentTimeMillis())
                .lineInputVoltage(inputVoltage)
                .lineInputVoltageType(inputType)
                .model(model)
                .pluggedIn(plugged)
                .powerCapacity(capacity)
                .serialNumber(serial)
                .sparePartNumber(spare)
                .state(state)
                .type(type)
                .build();

    }

    @Override
    public void update(@NonNull JsonNode node) {
        JsonNode statusNode = node.path("Status");
        this.setHealth(IloObjectHealth.valueOf(statusNode.path("Health").asText().toUpperCase()));
        this.setState(IloObjectState.valueOf(statusNode.path("State").asText().toUpperCase()));
        this.setLastPowerOutputWatts(JsonUtil.getSafeIntValueFromNode(node, "LastPowerOutputWatts"));  
        this.setLastUpdateTime(System.currentTimeMillis());      
    }

    @Override
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    @Override
    public int getTimeBetweenUpdates() {
        return 4;
    }

}
