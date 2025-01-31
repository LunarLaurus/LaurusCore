package net.laurus.data.dto.ipmi.ilo;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.laurus.data.enums.ilo.IloObjectHealth;
import net.laurus.data.enums.ilo.IloSensorLocation;
import net.laurus.data.enums.ilo.IloObjectState;
import net.laurus.data.enums.ilo.IloFanUnit;
import net.laurus.interfaces.NetworkData;
import net.laurus.interfaces.update.ilo.IloUpdatableFeatureWithoutAuth;
import net.laurus.util.JsonUtil;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class IloRestFanObject implements IloUpdatableFeatureWithoutAuth {

	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

	String fanName;
	Integer slotId;
	int currentReading;
	IloFanUnit unit;
	IloObjectState statusState;
	IloObjectHealth statusHealth;
	IloSensorLocation location;
	long lastUpdateTime;

	public IloRestFanObject(String fanName, int currentReading, IloFanUnit unit, IloObjectState statusState, IloObjectHealth statusHealth,
			IloSensorLocation location) {
		this.fanName = fanName;
		this.slotId = extractSlotId(fanName);
		this.currentReading = currentReading;
		this.unit = unit;
		this.statusState = statusState;
		this.statusHealth = statusHealth;
		this.location = location;
		this.lastUpdateTime = System.currentTimeMillis();
	}

	public static IloRestFanObject from(@NonNull JsonNode node) {
		int currentSpeed = node.path("CurrentReading").asInt();
		String fanId = node.path("FanName").asText("Bad Fan Name").trim();
		JsonNode statusNode = node.path("Status");
		JsonNode locationNode = node.path("Oem").path("Hp");
		IloObjectHealth health;
		if (statusNode.has("Health")) {
			health = IloObjectHealth.valueOf(JsonUtil.getSafeTextValueFromNode(statusNode, "Health").toUpperCase());
		} else {
			health = IloObjectHealth.UNKNOWN;
		}
		IloObjectState state = IloObjectState.valueOf(JsonUtil.getSafeTextValueFromNode(statusNode, "State").toUpperCase());
		IloFanUnit unit = IloFanUnit.valueOf(JsonUtil.getSafeTextValueFromNode(node, "Units").toUpperCase());
		IloSensorLocation location = IloSensorLocation
				.fromString(JsonUtil.getSafeTextValueFromNode(locationNode, "Location"));

		return new IloRestFanObject(fanId, currentSpeed, unit, state, health, location);
	}

	@Override
	public void update(@NonNull JsonNode node) {
		this.setCurrentReading(node.path("CurrentReading").asInt());
		JsonNode statusNode = node.path("Status");
		IloObjectHealth health;
		if (statusNode.has("Health")) {
			health = IloObjectHealth.valueOf(JsonUtil.getSafeTextValueFromNode(statusNode, "Health").toUpperCase());
		} else {
			health = IloObjectHealth.UNKNOWN;
		}
		this.setStatusHealth(health);
		this.setStatusState(IloObjectState.valueOf(JsonUtil.getSafeTextValueFromNode(statusNode, "State").toUpperCase()));
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
			String cleanedId = id.toLowerCase().replaceAll("fan( block)?", "") // Matches "Fan" or "Fan Block"
					.trim();

			// Attempt to parse the cleaned string as an integer
			return Integer.parseInt(cleanedId);
		} catch (NumberFormatException e) {
			// Fallback: Remove all non-numeric characters and try again
			log.warn("Fallback: Remove all non-numeric characters: " + id);
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
		if (statusHealth.equals(IloObjectHealth.OK) && statusState.equals(IloObjectState.ENABLED)) {
			return true;
		}
		return false;
	}

}
