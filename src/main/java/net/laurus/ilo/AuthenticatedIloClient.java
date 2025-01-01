package net.laurus.ilo;

import static net.laurus.Constant.JSON_MAPPER;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.java.Log;
import net.laurus.data.dto.ipmi.ilo.IloBootObject;
import net.laurus.data.dto.ipmi.ilo.IloLicenseObject;
import net.laurus.data.dto.ipmi.ilo.IloMemoryObject;
import net.laurus.data.dto.ipmi.ilo.IloOemInformation;
import net.laurus.data.dto.ipmi.ilo.IloPowerObject;
import net.laurus.data.dto.ipmi.ilo.IloProcessorSummary;
import net.laurus.data.dto.ipmi.ilo.IloRestFanObject;
import net.laurus.data.dto.ipmi.ilo.IloTemperatureSensor;
import net.laurus.data.enums.ilo.IloIndicatorLED;
import net.laurus.data.enums.ilo.IloSystemType;
import net.laurus.ilo.UnauthenticatedEndpoint.IloNicObject;
import net.laurus.interfaces.IloDataClient;
import net.laurus.interfaces.NetworkData;
import net.laurus.interfaces.update.ilo.IloUpdatableFeature;
import net.laurus.network.IPv4Address;
import net.laurus.network.IloUser;
import net.laurus.util.GeneralUtil;
import net.laurus.util.NetworkUtil;

@Data
@Builder
@Log
public class AuthenticatedIloClient implements IloDataClient, IloUpdatableFeature {

	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

	@NonNull
	final IPv4Address iloAddress;
	@NonNull
	final String iloUuid;
	@NonNull
	final IloUser iloUser;
	@NonNull
	final String serialNumber;
	@NonNull
	final String serverModel;
	@NonNull
	final String serverId;
	@NonNull
	final String serverUuid;
	@NonNull
	final String serverAssetTag;
	@NonNull
	String serverHostname;
	@NonNull
	final String productId;
	@NonNull
	String iloText;
	@NonNull
	String iloVersion;
	@NonNull
	String iloFwBuildDate;
	@NonNull
	final String iloSerialNumber;
	@NonNull
	final IloProcessorSummary cpuData;
	@NonNull
	final IloMemoryObject memory;
	@NonNull
	final IloPowerObject powerData;
	@NonNull
	final IloLicenseObject iloLicense;
	@NonNull
	final IloOemInformation oemInformation;
	@NonNull
	final IloSystemType systemType;
	@NonNull
	final IloBootObject iloBootInformation;
	@NonNull
	List<IloNicObject> nics;
	@NonNull
	final List<IloRestFanObject> fans;
	@NonNull
	final List<IloTemperatureSensor> thermalSensors;
	
	

	int healthStatus;
	IloIndicatorLED indicatorLed;
	

	long lastUpdateTime;

	public static AuthenticatedIloClient from(IloUser iloUser, IPv4Address iloAddress,
			UnauthenticatedIloClient client) {

		if (iloUser == null || iloAddress == null) {
			return null;
		}

		try {
			JsonNode systemNode = getSystemNode(iloUser, iloAddress);
			String hostnameNodeValue = systemNode.path("HostName").asText("N/A");
			String assetTagValue = systemNode.path("AssetTag").asText("N/A");
			String indicatorLedString = systemNode.path("IndicatorLED").asText("Off");
			JsonNode oemHpNode = systemNode.path("Oem").path("Hp");
			IloOemInformation oem = IloOemInformation.from(oemHpNode);
			
			IloSystemType systemType = IloSystemType.get(systemNode.path("SystemType").asText("Unknown"));
			IloBootObject bootInfo = IloBootObject.from(systemNode.path("Boot"));
			
			JsonNode processorSummaryNode = systemNode.path("ProcessorSummary");
			IloProcessorSummary cpuData = IloProcessorSummary.from(processorSummaryNode);
			JsonNode thermalNode = getThermalNode(iloUser, iloAddress);
			JsonNode powerNode = getPowerNode(iloUser, iloAddress);
			IloPowerObject iloPower = IloPowerObject.from(powerNode);
			JsonNode fanNode = thermalNode.path("Fans");
			List<IloRestFanObject> fans = new LinkedList<>();
			if (fanNode.isArray()) {
				for (JsonNode fanObjectNode : fanNode) {
					IloRestFanObject fan = IloRestFanObject.from(fanObjectNode);
					fans.add(fan);
				}
			}
			JsonNode temperaturesNode = thermalNode.path("Temperatures");
			List<IloTemperatureSensor> tempSensors = new LinkedList<>();
			if (temperaturesNode.isArray()) {
				for (JsonNode tempSensorNode : temperaturesNode) {
					IloTemperatureSensor sensor = IloTemperatureSensor.from(tempSensorNode);
					tempSensors.add(sensor);
				}
			}
			IloLicenseObject licenseObj = IloLicenseObject.from(getLicenseNode(iloUser, iloAddress));
			IloMemoryObject memory = IloMemoryObject.from(iloAddress, iloUser.getAuthData());

			AuthenticatedIloClient obj = AuthenticatedIloClient.builder().cpuData(cpuData).oemInformation(oem)
					.fans(fans).thermalSensors(tempSensors).healthStatus(client.getHealthStatus())
					.iloAddress(iloAddress).iloBootInformation(bootInfo).iloFwBuildDate(client.getIloFwBuildDate()).iloLicense(licenseObj)
					.iloSerialNumber(client.getIloSerialNumber()).iloText(client.getIloText()).iloUser(iloUser)
					.iloUuid(client.iloUuid).iloVersion(client.getIloVersion()).indicatorLed(IloIndicatorLED.get(indicatorLedString))
					.lastUpdateTime(System.currentTimeMillis()).memory(memory).nics(client.getNics())
					.powerData(iloPower).productId(client.getProductId()).serialNumber(client.getSerialNumber())
					.serverAssetTag(assetTagValue)
					.serverHostname(hostnameNodeValue).serverId(client.getServerId())
					.serverModel(client.getServerModel()).serverUuid(client.getServerUuid())
					.systemType(systemType).build();

			log.info("Created AuthenticatedIloClient: " + obj.toString());
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private static JsonNode getJsonNode(IloUser iloUser, IPv4Address iloAddress, String address) throws Exception {
		return JSON_MAPPER.readTree(NetworkUtil.fetchDataFromEndpoint(address, iloUser.getWrappedAuthData()));
	}

	private static JsonNode getSystemNode(IloUser iloUser, IPv4Address iloAddress) throws Exception {
		return getJsonNode(iloUser, iloAddress, "https://" + iloAddress.toString() + "/rest/v1/Systems/1");
	}

	private static JsonNode getPowerNode(IloUser iloUser, IPv4Address iloAddress) throws Exception {
		return getJsonNode(iloUser, iloAddress, "https://" + iloAddress.toString() + "/rest/v1/Chassis/1/Power");
	}

	private static JsonNode getThermalNode(IloUser iloUser, IPv4Address iloAddress) throws Exception {
		return getJsonNode(iloUser, iloAddress, "https://" + iloAddress.toString() + "/rest/v1/Chassis/1/Thermal");
	}

	private static JsonNode getLicenseNode(IloUser iloUser, IPv4Address iloAddress) throws Exception {
		return getJsonNode(iloUser, iloAddress,
				"https://" + iloAddress.toString() + "/rest/v1/Managers/1/LicenseService/1");
	}

	public void updateFieldsFromUnauthenticatedIloClient(UnauthenticatedIloClient client) {
		healthStatus = client.getHealthStatus();
		nics = client.getNics();
	}

	@Override
	public int getTimeBetweenUpdates() {
		return 1;
	}

	public void update() {
		if (!GeneralUtil.timeDifference(System.currentTimeMillis(), lastUpdateTime, 2)) {
			return;
		}
		try {
			// Update Power
			if (powerData.canUpdate()) {
				JsonNode powerNode = getPowerNode(iloUser, iloAddress);
				powerData.update(powerNode);
				setLastUpdateTime(System.currentTimeMillis());
			}

			// Update Memory
			if (memory.canUpdate()) {
				memory.update(getIloAddress(), iloUser.getAuthData());
			}

			// Update Thermal
			{
				JsonNode thermalNode = getThermalNode(iloUser, iloAddress);
				// Update Fans
				JsonNode fanNode = thermalNode.path("Fans");
				if (fanNode.isArray() && !fans.isEmpty()) {
					for (int i = 0; i < fanNode.size(); i++) {
						JsonNode fanObj = fanNode.get(i);
						IloRestFanObject fan = fans.get(i);
						if (fan.canUpdate()) {
							fan.update(fanObj);
							setLastUpdateTime(System.currentTimeMillis());
						}
					}
				}
				// Update Temperature Sensors
				JsonNode temperaturesNode = thermalNode.path("Temperatures");
				if (temperaturesNode.isArray() && !thermalSensors.isEmpty()) {
					for (int i = 0; i < temperaturesNode.size(); i++) {
						JsonNode thermalSensorNode = temperaturesNode.get(i);
						IloTemperatureSensor thermalSensor = thermalSensors.get(i);
						if (thermalSensor.canUpdate()) {
							thermalSensor.update(thermalSensorNode);
							setLastUpdateTime(System.currentTimeMillis());
						}
					}
				}
			}

			// Update other fields with .update(x, y, z)
			{
				JsonNode systemNode = getSystemNode(iloUser, iloAddress);

				serverHostname = systemNode.path("HostName").asText();
				String indicatorLedString = systemNode.path("IndicatorLED").asText("Off");
				indicatorLed = IloIndicatorLED.get(indicatorLedString);
			}

			iloLicense.update(getLicenseNode(iloUser, iloAddress));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
