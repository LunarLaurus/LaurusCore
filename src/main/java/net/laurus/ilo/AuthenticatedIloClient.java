package net.laurus.ilo;

import static net.laurus.Constant.JSON_MAPPER;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.java.Log;
import net.laurus.data.dto.IloBios;
import net.laurus.data.dto.IloLicenseObject;
import net.laurus.data.dto.IloMemoryObject;
import net.laurus.data.dto.IloPowerObject;
import net.laurus.data.dto.IloProcessorSummary;
import net.laurus.data.dto.IloRestFanObject;
import net.laurus.ilo.UnauthenticatedEndpoint.IloNicObject;
import net.laurus.network.IPv4Address;
import net.laurus.network.IloUser;
import net.laurus.util.GeneralUtil;
import net.laurus.util.NetworkUtil;

@Data
@Builder
@Log
public class AuthenticatedIloClient implements Serializable {

	private static final long serialVersionUID = 4396305666752192679L;

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
	final String productId;
	@NonNull
	final String iloText;
	@NonNull
	final String iloVersion;
	@NonNull
	final String iloFwBuildDate;
	@NonNull
	final String iloSerialNumber;
	@NonNull
    List<IloNicObject> nics;
    int healthStatus;	
	
	@NonNull
    final IloBios bios;
	@NonNull
    final IloProcessorSummary cpuData;
	@NonNull
    final IloMemoryObject memory;
	@NonNull
    List<IloRestFanObject> fans;
	@NonNull
    IloPowerObject powerData;
	@NonNull
    final IloLicenseObject license;
    long lastUpdate;

    public void update() {

        if (!GeneralUtil.timeDifference(System.currentTimeMillis(), lastUpdate, 2)) {
            return;
        }
        try {
            // Update Power
            if (powerData.canUpdate()) {
                String power = NetworkUtil.fetchDataFromEndpoint("https://"+iloAddress.toString()+"/rest/v1/Chassis/1/Power",
                        iloUser.getWrappedAuthData());
                JsonNode powerNode = JSON_MAPPER.readTree(power);
                powerData.update(null, null, powerNode);
                setLastUpdate(System.currentTimeMillis());
            }            
            
            // Update Memory
            if (memory.canUpdate()) {
                memory.update(getIloAddress(), iloUser.getAuthData(), null);
            }
            
            // Update Fans
            String thermal = NetworkUtil.fetchDataFromEndpoint("https://"+iloAddress.toString()+"/rest/v1/Chassis/1/Thermal",
                    iloUser.getWrappedAuthData());
            JsonNode fanNode = JSON_MAPPER.readTree(thermal).path("Fans");
            if (fanNode.isArray() && !fans.isEmpty()) {
                for (int i = 0; i < fanNode.size(); i++) {
                    JsonNode fanObj = fanNode.get(i);
                    IloRestFanObject fan = fans.get(i);
                    if (fan.canUpdate()) {
                        fan.update(null, null, fanObj);
                        setLastUpdate(System.currentTimeMillis());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static AuthenticatedIloClient from(IloUser iloUser, IPv4Address iloAddress, UnauthenticatedIloClient client) {

        if (iloUser == null || iloAddress == null) {
            return null;
        }

        try {
        	String authData = iloUser.getAuthData();
        	Optional<String> maybeAuthData = iloUser.getWrappedAuthData();
        	
            String system = NetworkUtil.fetchDataFromEndpoint(
                    "https://" + iloAddress.toString() + "/rest/v1/Systems/1", maybeAuthData);

            JsonNode systemNode = JSON_MAPPER.readTree(system);
            JsonNode biosNode = systemNode.path("Oem").path("Hp").path("Bios");
            IloBios iloBios = JSON_MAPPER.readValue(biosNode.toPrettyString(), IloBios.class);
            JsonNode processorSummaryNode = systemNode.path("ProcessorSummary");
            IloProcessorSummary cpuData = IloProcessorSummary.from(processorSummaryNode);            

            String power = NetworkUtil.fetchDataFromEndpoint(
                    "https://" + iloAddress.toString() + "/rest/v1/Chassis/1/Power", maybeAuthData);
            String thermal = NetworkUtil.fetchDataFromEndpoint(
                    "https://" + iloAddress.toString() + "/rest/v1/Chassis/1/Thermal", maybeAuthData);
            
            JsonNode powerNode = JSON_MAPPER.readTree(power);
            JsonNode thermalNode = JSON_MAPPER.readTree(thermal);            
            IloPowerObject iloPower = IloPowerObject.from(powerNode);
            JsonNode fanNode = thermalNode.path("Fans");
            List<IloRestFanObject> fans = new LinkedList<>();
            if (fanNode.isArray()) {
                for (JsonNode fanObjectNode : fanNode) {
                    IloRestFanObject fan = IloRestFanObject.from(fanObjectNode);
                    fans.add(fan);
                }
            }
            IloLicenseObject licenseObj = IloLicenseObject.from(iloAddress, authData);
            IloMemoryObject memory = IloMemoryObject.from(iloAddress, authData);
            
            AuthenticatedIloClient obj = AuthenticatedIloClient.builder()
                    .bios(iloBios)
                    .cpuData(cpuData)
                    .fans(fans)
                    .healthStatus(client.getHealthStatus())
                    .iloAddress(iloAddress)
                    .iloFwBuildDate(client.getIloFwBuildDate())
                    .iloSerialNumber(client.getIloSerialNumber())
                    .iloText(client.getIloText())
                    .iloUser(iloUser)
                    .iloUuid(client.iloUuid)
                    .iloVersion(client.getIloVersion())
                    .lastUpdate(System.currentTimeMillis())
                    .license(licenseObj)
                    .memory(memory)
                    .nics(client.getNics())
                    .powerData(iloPower)
                    .productId(client.getProductId())
                    .serialNumber(client.getSerialNumber())
                    .serverId(client.getServerId())
                    .serverModel(client.getServerModel())
                    .serverUuid(client.getServerUuid())
                    .build();
            
            log.info("Created AuthenticatedIloClient: "+obj.toString());
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    
    public void updateFieldsFromUnauthenticatedIloClient(UnauthenticatedIloClient client) {
    	healthStatus = client.getHealthStatus();
    	nics = client.getNics();
    }

}
