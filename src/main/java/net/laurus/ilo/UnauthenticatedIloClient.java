package net.laurus.ilo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.laurus.ilo.UnauthenticatedEndpoint.IloNicObject;
import net.laurus.interfaces.IloUpdatableFeature;
import net.laurus.network.IPv4Address;
import net.laurus.util.XmlToJsonUtil;

@Data
@AllArgsConstructor
public class UnauthenticatedIloClient implements Serializable, IloUpdatableFeature {

    private static final long serialVersionUID = 5093912163023751757L;
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
    	update(null, null, null);
    }
    
    @Override
    public void update(IPv4Address ip, String auth, JsonNode node) {
    	node = getUnauthenticatedDataForClient();
        if (node == null) {
            return;
        }
        int health = node.path("HEALTH").path("STATUS").asInt();
        List<IloNicObject> adapters = new LinkedList<>();
        JsonNode rimpNode = node.path("RIMP");
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

    @Override
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    @Override
    public int getTimeBetweenUpdates() {
        return 60;
    }
}