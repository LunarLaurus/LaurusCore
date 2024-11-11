package net.laurus.data.dto.ipmi.ilo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Builder;
import lombok.Data;
import net.laurus.data.enums.ilo.Health;
import net.laurus.data.enums.ilo.State;
import net.laurus.interfaces.IloUpdatableFeature;
import net.laurus.network.IPv4Address;
import net.laurus.util.JsonUtil;

@Data
@Builder
@SuppressWarnings("serial")
public class IloPowerSupplyObject implements IloUpdatableFeature, Serializable {
    
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
    Health health;
    State state;
    long lastUpdateTime;

    public static IloPowerSupplyObject from(JsonNode node) {

        JsonNode oem_hp = node.path("Oem").path("Hp");
        int bayNumber = oem_hp.path("BayNumber").asInt();
        boolean hotPlug = oem_hp.path("HotplugCapable").asBoolean();

        JsonNode statusNode = node.path("Status");
        Health health = Health.valueOf(statusNode.path("Health").asText().toUpperCase());
        State state = State.valueOf(statusNode.path("State").asText().toUpperCase());

        String fwVersion = JsonUtil.getSafeTextValueFromNode(node, "FirmwareVersion");
        int lastOutput = JsonUtil.getSafeIntValueFromNode(node, "LastPowerOutputWatts");
        int inputVoltage = JsonUtil.getSafeIntValueFromNode(node, "LineInputVoltage");
        String inputType = JsonUtil.getSafeTextValueFromNode(node, "LineInputVoltageType");
        String model = JsonUtil.getSafeTextValueFromNode(node, "Model");

        int capacity = JsonUtil.getSafeIntValueFromNode(node, "PowerCapacityWatts");
        String type = JsonUtil.getSafeTextValueFromNode(node, "PowerSupplyType");
        String serial = JsonUtil.getSafeTextValueFromNode(node, "SerialNumber");
        String spare = JsonUtil.getSafeTextValueFromNode(node, "SparePartNumber");

        boolean plugged = (NA.equals(fwVersion) || NA.equals(model) || NA.equals(serial) || state.equals(State.ABSENT)) ? false : true;        
        
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
    public void update(IPv4Address ip, String authData, JsonNode node) {
        JsonNode statusNode = node.path("Status");
        this.setHealth(Health.valueOf(statusNode.path("Health").asText().toUpperCase()));
        this.setState(State.valueOf(statusNode.path("State").asText().toUpperCase()));
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
