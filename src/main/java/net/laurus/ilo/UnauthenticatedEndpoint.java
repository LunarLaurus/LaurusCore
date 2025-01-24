package net.laurus.ilo;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Value;
import lombok.extern.java.Log;
import net.laurus.interfaces.NetworkData;
import net.laurus.network.IPv4Address;
import net.laurus.util.NetworkUtil;
import net.laurus.util.XmlToJsonUtil;

@Log
public class UnauthenticatedEndpoint {

    @Value
    public static class IloNicObject implements NetworkData {
    	
    	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();
        int port;
        String description;
        String location;
        String mac;
        IPv4Address ip;
        String status;

    }

    public static final String suffix = "/xmldata?item=all";

    
    public static UnauthenticatedIloClient getIloClient(IPv4Address ip) throws Exception {

        if (ip == null) {
            return null;
        }
        
        String s = UnauthenticatedEndpoint.getData(ip);
        JsonNode jsonNode = XmlToJsonUtil.convertXmlToJson(s);
        JsonNode rimpNode = jsonNode.path("RIMP");
        JsonNode hsiNode = rimpNode.path("HSI");
        JsonNode mpNode = rimpNode.path("MP");

        String serialNumber = hsiNode.path("SBSN").asText("Invalid").trim();
        String serverModel = hsiNode.path("SPN").asText("Invalid").trim();
        String serverId = hsiNode.path("UUID").asText("Invalid").trim();
        String serverUuid = hsiNode.path("cUUID").asText("Invalid").trim();
        String productId = hsiNode.path("PRODUCTID").asText("Invalid").trim();
        String iloText = mpNode.path("PN").asText("Invalid").trim();
        String iloVersion = mpNode.path("FWRI").asText("Invalid").trim();
        String iloFwBuildDate = mpNode.path("BBLK").asText("Invalid").trim();
        String iloSerialNumber = mpNode.path("SN").asText("Invalid").trim();
        String iloUuid = mpNode.path("UUID").asText("Invalid").trim();
        
        
        int healthStatus = rimpNode.path("HEALTH").path("STATUS").asInt();

        JsonNode nicsNode = hsiNode.path("NICS");
        JsonNode nicNode = nicsNode.path("NIC");
        List<IloNicObject> nics = new LinkedList<>();

        for (JsonNode nic : nicNode) {
            nics.add(new IloNicObject(nic.path("PORT").asInt(), nic.path("DESCRIPTION").asText(),
                    nic.path("LOCATION").asText(), nic.path("MACADDR").asText(),
                    generateFromData(nic.path("IPADDR").asText()), nic.path("STATUS").asText()));
        }
        UnauthenticatedIloClient ilo = new UnauthenticatedIloClient(ip, serialNumber, serverModel, serverId, serverUuid, productId, iloText,
                iloVersion, iloFwBuildDate, iloSerialNumber, iloUuid, healthStatus, nics, System.currentTimeMillis());
        log.info("Created UnauthenticatedIloClient: "+ilo.toString());
        return ilo;

    }

    public static IPv4Address generateFromData(String s) {
        if (IPv4Address.isValidIPv4(s)) {
            return new IPv4Address(s);
        }
        return new IPv4Address("0.0.0.0");
    }

    public static String getData(IPv4Address ip) throws Exception {
        // Fetch XML data from the endpoint
        String xmlData = NetworkUtil.fetchDataFromEndpoint("https://" + ip.toString() + suffix);
        return xmlData;
    }


}
