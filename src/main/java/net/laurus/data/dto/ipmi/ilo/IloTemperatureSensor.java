package net.laurus.data.dto.ipmi.ilo;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import net.laurus.data.enums.ilo.Health;
import net.laurus.data.enums.ilo.HpSensorPhysicalContext;
import net.laurus.data.enums.ilo.State;
import net.laurus.data.enums.ilo.TemperatureUnit;
import net.laurus.interfaces.NetworkData;
import net.laurus.interfaces.update.ilo.IloUpdatableFeatureWithoutAuth;
import net.laurus.util.JsonUtil;

@Data
@AllArgsConstructor
public class IloTemperatureSensor implements IloUpdatableFeatureWithoutAuth {

	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

	final private String name;
	final private int number;
	final private int locationXmm;
	final private int locationYmm;
	final private HpSensorPhysicalContext physicalContext;

	private int currentReading;
	private int readingCelsius;

	private int upperThresholdCritical;
	private int upperThresholdFatal;
	private int upperThresholdUser;

	private Health health;
	private State state;
	private TemperatureUnit unit;

	long lastUpdateTime;

	public static IloTemperatureSensor from(@NonNull JsonNode node) {
		int currentReading = node.path("CurrentReading").asInt();
		int readingCelcius = node.path("ReadingCelsius").asInt();

		String sensorName = node.path("Name").asText("Bad Sensor Name").trim();

		int sensorId = node.path("Number").asInt();
		int UpperThresholdCritical = node.path("UpperThresholdCritical").asInt();
		int UpperThresholdFatal = node.path("UpperThresholdFatal").asInt();
		int UpperThresholdUser = node.path("UpperThresholdUser").asInt(0);
		JsonNode locationNode = node.path("Oem").path("Hp");
		int xPos = locationNode.path("LocationXmm").asInt();
		int yPos = locationNode.path("LocationYmm").asInt();

		JsonNode statusNode = node.path("Status");
		Health health;
		if (statusNode.has("Health")) {
			health = Health.valueOf(JsonUtil.getSafeTextValueFromNode(statusNode, "Health").toUpperCase());
		} else {
			health = Health.UNKNOWN;
		}
		State state = State.valueOf(JsonUtil.getSafeTextValueFromNode(statusNode, "State").toUpperCase());
		TemperatureUnit unit = TemperatureUnit.valueOf(JsonUtil.getSafeTextValueFromNode(node, "Units").toUpperCase());
		HpSensorPhysicalContext physicalContext = HpSensorPhysicalContext
				.fromString(JsonUtil.getSafeTextValueFromNode(node, "PhysicalContext").toUpperCase());

		return new IloTemperatureSensor(sensorName, sensorId, xPos, yPos, physicalContext, currentReading,
				readingCelcius, UpperThresholdCritical, UpperThresholdFatal, UpperThresholdUser, health, state, unit,
				System.currentTimeMillis());
	}

	@Override
	public void update(@NonNull JsonNode node) {
		this.currentReading = node.path("CurrentReading").asInt(this.currentReading);
		this.readingCelsius = node.path("ReadingCelsius").asInt(this.readingCelsius);
		this.upperThresholdCritical = node.path("UpperThresholdCritical").asInt(this.upperThresholdCritical);
		this.upperThresholdFatal = node.path("UpperThresholdFatal").asInt(this.upperThresholdFatal);
		this.upperThresholdUser = node.path("UpperThresholdUser").asInt(this.upperThresholdUser);
		JsonNode statusNode = node.path("Status");
		if (statusNode.has("Health")) {
			this.health = Health.valueOf(JsonUtil.getSafeTextValueFromNode(statusNode, "Health").toUpperCase());
		}
		this.state = State.valueOf(JsonUtil.getSafeTextValueFromNode(statusNode, "State").toUpperCase());
		this.lastUpdateTime = System.currentTimeMillis();
	}

	@Override
	public int getTimeBetweenUpdates() {
		return 10;
	}

}
