package net.laurus.data.dto.ipmi.ilo;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.laurus.interfaces.IloUpdatableFeature;
import net.laurus.interfaces.NetworkData;
import net.laurus.network.IPv4Address;

@Data
@AllArgsConstructor
public class IloPowerObject implements IloUpdatableFeature, NetworkData {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();
	
    int capacity;
    int consumption;
    int averageConsumedWatts;
    int intervalInMinutes;
    int maxConsumedWatts;
    int minConsumedWatts;
    List<IloPowerSupplyObject> supplies;
    long lastUpdateTime;

    public static IloPowerObject from(JsonNode node) {
        int powerCapacityWatts = node.path("PowerCapacityWatts").asInt();
        int powerConsumedWatts = node.path("PowerConsumedWatts").asInt();
        JsonNode powerMetrics = node.path("PowerMetrics");
        int avgConsumed = powerMetrics.path("AverageConsumedWatts").asInt();
        int interval = powerMetrics.path("IntervalInMin").asInt();
        int max = powerMetrics.path("MaxConsumedWatts").asInt();
        int min = powerMetrics.path("MinConsumedWatts").asInt();
        List<IloPowerSupplyObject> psus = new LinkedList<>();
        JsonNode supplies = node.path("PowerSupplies");
        if (supplies.isArray()) {
            for (JsonNode psu : supplies) {
                psus.add(IloPowerSupplyObject.from(psu));
            }
        }
        return new IloPowerObject(powerCapacityWatts, powerConsumedWatts, avgConsumed, interval, max, min, psus,
                System.currentTimeMillis());
    }

    @Override
    public void update(IPv4Address ip, String authData, JsonNode node) {
        if (this.canUpdate()) {
            this.setConsumption(node.path("PowerConsumedWatts").asInt());
            JsonNode powerMetrics = node.path("PowerMetrics");
            this.setAverageConsumedWatts(powerMetrics.path("AverageConsumedWatts").asInt());
            this.setIntervalInMinutes(powerMetrics.path("IntervalInMin").asInt());
            this.setMinConsumedWatts(powerMetrics.path("MinConsumedWatts").asInt());
            this.setMaxConsumedWatts(powerMetrics.path("MaxConsumedWatts").asInt());
            JsonNode psuNode = node.path("PowerSupplies");
            for (int i = 0; i < psuNode.size(); i++) {
                JsonNode updateData = psuNode.get(i);
                if (updateData != null && this.getSupplies().size() > 0) {
                    IloPowerSupplyObject sup = this.getSupplies().get(i);
                    if (sup != null && sup.canUpdate()) {
                        sup.update(null, null, updateData);
                    }
                }
            }
            this.setLastUpdateTime(System.currentTimeMillis());
        }
    }

    @Override
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    @Override
    public int getTimeBetweenUpdates() {
        return 5;
    }

}
