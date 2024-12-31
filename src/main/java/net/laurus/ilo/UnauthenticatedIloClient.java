package net.laurus.ilo;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import net.laurus.ilo.UnauthenticatedEndpoint.IloNicObject;
import net.laurus.interfaces.NetworkData;
import net.laurus.interfaces.update.ilo.IloUpdatableFeatureWithoutAuth;
import net.laurus.network.IPv4Address;
import net.laurus.util.XmlToJsonUtil;

@Data
@AllArgsConstructor
public class UnauthenticatedIloClient implements IloUpdatableFeatureWithoutAuth {

    private static final long serialVersionUID = NetworkData.getCurrentVersionHash();
    IPv4Address iloAddress;
    String serialNumber;
    String serverModel;
    String serverId;
    String serverUuid;
    String productId;
    String iloText;
    String iloVersion;
    String iloFwBuildDate;
    String iloSerialNumber;
    String iloUuid;
    int healthStatus;
    List<IloNicObject> nics;
    long lastUpdateTime;
    
    private JsonNode getUnauthenticatedDataForClient() {
        String s;
        try {
            s = UnauthenticatedEndpoint.getData(iloAddress);
            JsonNode jsonNode = XmlToJsonUtil.convertXmlToJson(s);
            return jsonNode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void updateUnauthenticatedClient() {
    	update(getUnauthenticatedDataForClient());
    }

    @Override
    public int getTimeBetweenUpdates() {
        return 60;
    }
    
	@Override
	public void update(@NonNull JsonNode updateDataNode) {
        int health = updateDataNode.path("HEALTH").path("STATUS").asInt();
        List<IloNicObject> adapters = new LinkedList<>();
        JsonNode rimpNode = updateDataNode.path("RIMP");
        JsonNode hsiNode = rimpNode.path("HSI");
        JsonNode nicsNode = hsiNode.path("NICS");
        JsonNode nicNode = nicsNode.path("NIC");
        for (JsonNode nic : nicNode) {
            adapters.add(new IloNicObject(nic.path("PORT").asInt(), nic.path("DESCRIPTION").asText(),
                    nic.path("LOCATION").asText(), nic.path("MACADDR").asText(),
                    UnauthenticatedEndpoint.generateFromData(nic.path("IPADDR").asText()), nic.path("STATUS").asText()));
        }
        if (!adapters.isEmpty()) {
            this.setNics(adapters);
        }
        this.setHealthStatus(health);
        this.setLastUpdateTime(System.currentTimeMillis());
    }
}