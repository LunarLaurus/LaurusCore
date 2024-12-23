package net.laurus.data.dto.ipmi.ilo;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;
import net.laurus.Constant;
import net.laurus.data.enums.ilo.DimmGeneration;
import net.laurus.data.enums.ilo.DimmStatus;
import net.laurus.data.enums.ilo.DimmTechnology;
import net.laurus.data.enums.ilo.ErrorCorrection;
import net.laurus.data.enums.ilo.HpMemoryType;
import net.laurus.interfaces.IloUpdatableFeature;
import net.laurus.interfaces.NetworkData;
import net.laurus.network.IPv4Address;
import net.laurus.util.JsonUtil;
import net.laurus.util.NetworkUtil;

@Data
public class IloMemoryObject implements IloUpdatableFeature, NetworkData {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();
	
    @Value
    @Builder
    public static class IloMemoryDimm implements IloUpdatableFeature, NetworkData {
    	
    	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();
    	
    	@NonFinal
        @Setter
        DimmStatus status;
        DimmLocation location;
        DimmTechnology type;
        DimmGeneration generation;
        ErrorCorrection ecc;
        HpMemoryType hpMemoryType;
        String id;
        String name;
        String manufacturer;
        String partNumber;
        int rank;
        int size;
        int maximumFrequency;
        int minimumVoltageVolts;
        int dataWidth;
        int totalWidth;
        @NonFinal
        long lastUpdateTime;

        @Value
        public static class DimmLocation implements NetworkData {
        	
        	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();
        	
        	int processorId;
            int dimmId;

            private static DimmLocation from(String internalName) {
                String[] socketLocator = internalName.stripTrailing().replace("  ", " ").split(" ");
                return new DimmLocation(Integer.parseInt(socketLocator[1]), Integer.parseInt(socketLocator[3]));
            }
        }

        public static IloMemoryDimm from(IPv4Address ip, String authData, JsonNode node)
                throws JsonMappingException, JsonProcessingException, Exception {

            JsonNode dimmNode = Constant.JSON_MAPPER.readTree(NetworkUtil.fetchDataFromEndpoint(
                    "https://" + ip.toString() + node.path("href").asText(), Optional.of(authData)));

            DimmStatus status = DimmStatus.get(JsonUtil.getSafeTextValueFromNode(dimmNode, "DIMMStatus").toUpperCase());
            DimmLocation loc = DimmLocation.from(JsonUtil.getSafeTextValueFromNode(dimmNode, "SocketLocator"));
            DimmTechnology tech = DimmTechnology.valueOf(JsonUtil.getSafeTextValueFromNode(dimmNode, "DIMMTechnology").toUpperCase());
            DimmGeneration gen = DimmGeneration.valueOf(JsonUtil.getSafeTextValueFromNode(dimmNode, "DIMMType").toUpperCase());
            ErrorCorrection ecc = ErrorCorrection.get(JsonUtil.getSafeTextValueFromNode(dimmNode, "ErrorCorrection").toUpperCase());
            HpMemoryType hpMem = HpMemoryType.valueOf(JsonUtil.getSafeTextValueFromNode(dimmNode, "HPMemoryType").toUpperCase());           

            String id = JsonUtil.getSafeTextValueFromNode(dimmNode, "id");
            String name = JsonUtil.getSafeTextValueFromNode(dimmNode, "Name");
            String manufact = JsonUtil.getSafeTextValueFromNode(dimmNode, "Manufacturer");
            String part = JsonUtil.getSafeTextValueFromNode(dimmNode, "PartNumber");

            int rank = JsonUtil.getSafeIntValueFromNode(dimmNode, "Rank");
            int size = JsonUtil.getSafeIntValueFromNode(dimmNode, "SizeMB");
            int maxFreq = JsonUtil.getSafeIntValueFromNode(dimmNode, "MaximumFrequencyMHz");
            int widthData = JsonUtil.getSafeIntValueFromNode(dimmNode, "DataWidth");
            int widthTotal = JsonUtil.getSafeIntValueFromNode(dimmNode, "TotalWidth");
            int minVoltage = JsonUtil.getSafeIntValueFromNode(dimmNode, "MinimumVoltageVoltsX10");
            
            return IloMemoryDimm.builder().dataWidth(widthData).ecc(ecc).generation(gen).hpMemoryType(hpMem).id(id)
                    .location(loc).manufacturer(manufact).maximumFrequency(maxFreq).minimumVoltageVolts(minVoltage)
                    .name(name).partNumber(part).rank(rank).size(size).status(status).totalWidth(widthTotal).type(tech)
                    .build();
        }

        @Override
        public void update(IPv4Address ip, String authData, JsonNode node) {
            JsonNode dimmNode;
            try {
                dimmNode = Constant.JSON_MAPPER.readTree(NetworkUtil.fetchDataFromEndpoint(
                        "https://" + ip.toString() + node.path("href").asText(), Optional.of(authData)));
                DimmStatus stat = DimmStatus.get(JsonUtil.getSafeTextValueFromNode(dimmNode, "DIMMStatus").toUpperCase());
                lastUpdateTime = System.currentTimeMillis();
                this.setStatus(stat);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getTimeBetweenUpdates() {
            return 300;
        }

    }

    final List<IloMemoryDimm> dimms;
    long lastUpdateTime;

    public static IloMemoryObject from(IPv4Address ip, String authData) throws Exception {

        String memory = NetworkUtil.fetchDataFromEndpoint("https://" + ip.toString() + "/rest/v1/Systems/1/Memory",
                Optional.of(authData));
        JsonNode memoryBlob = Constant.JSON_MAPPER.readTree(memory);
        JsonNode memoryLinks = memoryBlob.path("links").path("Member");

        List<IloMemoryDimm> dimms = new LinkedList<>();
        for (JsonNode node : memoryLinks) {
            try {
                dimms.add(IloMemoryDimm.from(ip, authData, node));
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        return new IloMemoryObject(dimms);
    }

    @Override
    public void update(IPv4Address ip, String authData, JsonNode node) {

        String memory;
        try {
            memory = NetworkUtil.fetchDataFromEndpoint("https://" + ip.toString() + "/rest/v1/Systems/1/Memory",
                    Optional.of(authData));
            JsonNode memoryBlob = Constant.JSON_MAPPER.readTree(memory);
            JsonNode memoryLinks = memoryBlob.path("links").path("Member");
            int dimmIndex = 0;
            for (JsonNode mem : memoryLinks) {
                try {
                    IloMemoryDimm dimm = this.dimms.get(dimmIndex++);
                    if (dimm != null && dimm.canUpdate()) {
                        dimm.update(ip, authData, mem);
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }  
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }

    @Override
    public int getTimeBetweenUpdates() {
        return 0;
    }

}
