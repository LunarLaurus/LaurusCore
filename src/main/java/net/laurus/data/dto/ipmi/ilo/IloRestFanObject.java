package net.laurus.data.dto.ipmi.ilo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.laurus.data.enums.ilo.Health;
import net.laurus.data.enums.ilo.State;
import net.laurus.data.enums.ilo.Unit;
import net.laurus.interfaces.IloUpdatableFeature;
import net.laurus.network.IPv4Address;
import net.laurus.util.JsonUtil;

@Data
@AllArgsConstructor
public class IloRestFanObject implements IloUpdatableFeature, Serializable {

    private static final long serialVersionUID = -2035233593894700047L;

    String id;
    int currentReading;
    Unit unit;
    State statusState;
    Health statusHealth;
    long lastUpdateTime;

    public static IloRestFanObject from(JsonNode node) {
        ((ObjectNode) node).remove("Oem");
        int currentSpeed = node.path("CurrentReading").asInt();
        String fanId = node.path("FanName").asText("Bad Fan Name").trim();

        JsonNode statusNode = node.path("Status");
        Health health;
        if (statusNode.has("Health")) {
            health = Health.valueOf(JsonUtil.getSafeTextValueFromNode(statusNode, "Health").toUpperCase());
        } else {
            health = Health.UNKNOWN;
        }
        State state = State.valueOf(JsonUtil.getSafeTextValueFromNode(statusNode, "State").toUpperCase());
        Unit unit = Unit.valueOf(JsonUtil.getSafeTextValueFromNode(node, "Units").toUpperCase());
        return new IloRestFanObject(fanId, currentSpeed, unit, state, health, System.currentTimeMillis());
    }

    @Override
    public void update(IPv4Address ip, String authData, JsonNode node) {
        ((ObjectNode) node).remove("Oem");
        this.setCurrentReading(node.path("CurrentReading").asInt());
        JsonNode statusNode = node.path("Status");
        Health health;
        if (statusNode.has("Health")) {
            health = Health.valueOf(JsonUtil.getSafeTextValueFromNode(statusNode, "Health").toUpperCase());
        } else {
            health = Health.UNKNOWN;
        }
        this.setStatusHealth(health);
        this.setStatusState(State.valueOf(JsonUtil.getSafeTextValueFromNode(statusNode, "State").toUpperCase()));
        this.setLastUpdateTime(System.currentTimeMillis());
    }

    @Override
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    @Override
    public int getTimeBetweenUpdates() {
        return 2;
    }

    public Integer getSlotId() {
        return Integer.parseInt(id.toLowerCase().replace("fan", "").trim());
    }

    public boolean isValidForUpdate() {

        if (statusHealth.equals(Health.OK) && statusState.equals(State.ENABLED)) {
            return true;
        }

        return false;
    }

}
