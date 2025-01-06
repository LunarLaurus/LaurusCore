package net.laurus.data.dto.ipmi.ilo;

import static net.laurus.util.JsonUtil.getSafeIntValueFromNode;
import static net.laurus.util.JsonUtil.getSafeTextValueFromNode;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import net.laurus.data.enums.ilo.IloObjectHealth;
import net.laurus.data.enums.ilo.IloObjectState;
import net.laurus.data.enums.ilo.IloSensorPhysicalContext;
import net.laurus.data.enums.ilo.IloTemperatureUnit;
import net.laurus.interfaces.NetworkData;
import net.laurus.interfaces.update.ilo.IloUpdatableFeatureWithoutAuth;

@Data
@AllArgsConstructor
public class IloTemperatureSensor implements IloUpdatableFeatureWithoutAuth {

	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

	final private String name;
	final private int number;
	final private int locationXmm;
	final private int locationYmm;
	final private IloSensorPhysicalContext physicalContext;

	private int currentReading;
	private int readingCelsius;

	private int upperThresholdCritical;
	private int upperThresholdFatal;
	private int upperThresholdUser;

	private IloObjectHealth health;
	private IloObjectState state;
	private IloTemperatureUnit unit;

	long lastUpdateTime;

	public static IloTemperatureSensor from(@NonNull JsonNode node) {
		
		int currentReading = getSafeIntValueFromNode(node, "CurrentReading");
		int readingCelcius = getSafeIntValueFromNode(node, "ReadingCelcius");		
		String sensorName = getSafeTextValueFromNode(node, "Name");
		int sensorId = getSafeIntValueFromNode(node, "Number");	
		int UpperThresholdCritical = getSafeIntValueFromNode(node, "UpperThresholdCritical");		
		int UpperThresholdFatal = getSafeIntValueFromNode(node, "UpperThresholdFatal");		
		int UpperThresholdUser = getSafeIntValueFromNode(node, "UpperThresholdUser", 0);
		
		JsonNode locationNode = node.path("Oem").path("Hp");
		int xPos = getSafeIntValueFromNode(locationNode, "LocationXmm");		
		int yPos = getSafeIntValueFromNode(locationNode, "LocationYmm");		

		JsonNode statusNode = node.path("Status");
		IloObjectHealth health;
		if (statusNode.has("Health")) {
			health = IloObjectHealth.valueOf(getSafeTextValueFromNode(statusNode, "Health").toUpperCase());
		} else {
			health = IloObjectHealth.UNKNOWN;
		}
		IloObjectState state = IloObjectState.valueOf(getSafeTextValueFromNode(statusNode, "State").toUpperCase());
		IloTemperatureUnit unit = IloTemperatureUnit.valueOf(getSafeTextValueFromNode(node, "Units").toUpperCase());
		IloSensorPhysicalContext physicalContext = IloSensorPhysicalContext
				.fromString(getSafeTextValueFromNode(node, "PhysicalContext").toUpperCase());

		return new IloTemperatureSensor(sensorName, sensorId, xPos, yPos, physicalContext, currentReading,
				readingCelcius, UpperThresholdCritical, UpperThresholdFatal, UpperThresholdUser, health, state, unit,
				System.currentTimeMillis());
	}

	@Override
	public void update(@NonNull JsonNode node) {
		this.currentReading = getSafeIntValueFromNode(node, "CurrentReading", this.currentReading);
		this.readingCelsius = getSafeIntValueFromNode(node, "ReadingCelsius", this.readingCelsius);
		this.upperThresholdCritical = getSafeIntValueFromNode(node, "UpperThresholdCritical", this.upperThresholdCritical);
		this.upperThresholdFatal = getSafeIntValueFromNode(node, "UpperThresholdFatal", this.upperThresholdFatal);
		this.upperThresholdUser = getSafeIntValueFromNode(node, "UpperThresholdUser", this.upperThresholdUser);
		JsonNode statusNode = node.path("Status");
		if (statusNode.has("Health")) {
			this.health = IloObjectHealth.valueOf(getSafeTextValueFromNode(statusNode, "Health").toUpperCase());
		}
		this.state = IloObjectState.valueOf(getSafeTextValueFromNode(statusNode, "State").toUpperCase());
		this.lastUpdateTime = System.currentTimeMillis();
	}

	@Override
	public int getTimeBetweenUpdates() {
		return 10;
	}

}
