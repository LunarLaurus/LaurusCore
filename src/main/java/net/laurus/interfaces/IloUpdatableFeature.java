package net.laurus.interfaces;

import com.fasterxml.jackson.databind.JsonNode;

import net.laurus.network.IPv4Address;
import net.laurus.util.GeneralUtil;

public interface IloUpdatableFeature {

    public void update(IPv4Address ip, String authData, JsonNode node);

    public default boolean canUpdate() {
        return GeneralUtil.timeDifference(System.currentTimeMillis(), getLastUpdateTime(), getTimeBetweenUpdates());
    }
    
    public long getLastUpdateTime();
    
    public int getTimeBetweenUpdates();
    
}
