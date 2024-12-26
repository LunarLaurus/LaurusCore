package net.laurus.data.dto.ipmi.ilo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.laurus.data.enums.ilo.Health;
import net.laurus.data.enums.ilo.HpSensorLocation;
import net.laurus.data.enums.ilo.State;
import net.laurus.data.enums.ilo.Unit;
import net.laurus.interfaces.IloUpdatableFeature;
import net.laurus.interfaces.NetworkData;
import net.laurus.network.IPv4Address;
import net.laurus.util.JsonUtil;

@Data
@Slf4j
public class IloRestFanObject implements IloUpdatableFeature, NetworkData {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

    final String fanName;
    final Integer slotId;
    int currentReading;
    Unit unit;
    State statusState;
    Health statusHealth;
    final HpSensorLocation location;
    long lastUpdateTime;

	public IloRestFanObject(String fanName, int currentReading, Unit unit, State statusState,
			Health statusHealth, HpSensorLocation location) {
		this.fanName = fanName;
		this.slotId = extractSlotId(fanName);
		this.currentReading = currentReading;
		this.unit = unit;
		this.statusState = statusState;
		this.statusHealth = statusHealth;
		this.location = location;
		this.lastUpdateTime = System.currentTimeMillis();
	}
    
    

    public static IloRestFanObject from(JsonNode node) {
        int currentSpeed = node.path("CurrentReading").asInt();
        String fanId = node.path("FanName").asText("Bad Fan Name").trim();
        JsonNode statusNode = node.path("Status");
        JsonNode locationNode = node.path("Oem").path("Hp");
        Health health;
        if (statusNode.has("Health")) {
            health = Health.valueOf(JsonUtil.getSafeTextValueFromNode(statusNode, "Health").toUpperCase());
        } else {
            health = Health.UNKNOWN;
        }
        State state = State.valueOf(JsonUtil.getSafeTextValueFromNode(statusNode, "State").toUpperCase());
        Unit unit = Unit.valueOf(JsonUtil.getSafeTextValueFromNode(node, "Units").toUpperCase());        
        HpSensorLocation location = HpSensorLocation.fromString(JsonUtil.getSafeTextValueFromNode(locationNode, "Location"));
        
        return new IloRestFanObject(fanId, currentSpeed, unit, state, health, location);
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

    private final Integer extractSlotId(String id) {
        try {
            // Handle both "Fan Block" and "Fan" prefixes
            String cleanedId = id.toLowerCase()
                                 .replaceAll("fan( block)?", "") // Matches "Fan" or "Fan Block"
                                 .trim();

            // Attempt to parse the cleaned string as an integer
            return Integer.parseInt(cleanedId);
        } catch (NumberFormatException e) {
            // Fallback: Remove all non-numeric characters and try again
        	log.warn("Fallback: Remove all non-numeric characters: "+id);
            String fallbackId = id.replaceAll("\\D", ""); // Remove non-digit characters
            try {
                return fallbackId.isEmpty() ? -1 : Integer.parseInt(fallbackId); // Default to -1 if no digits
            } catch (NumberFormatException fallbackException) {
                log.warn("Fallback also failed for id: {}", id, fallbackException);
                return -1; // Final fallback value
            }
        }
    }

    public boolean isValidForUpdate() {
        if (statusHealth.equals(Health.OK) && statusState.equals(State.ENABLED)) {
            return true;
        }
        return false;
    }

}
