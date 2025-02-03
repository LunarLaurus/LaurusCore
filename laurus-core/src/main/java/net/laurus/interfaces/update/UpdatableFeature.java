package net.laurus.interfaces.update;

import net.laurus.interfaces.NetworkData;
import net.laurus.util.GeneralUtil;

public interface UpdatableFeature extends NetworkData {

    public default boolean canUpdate() {
        return GeneralUtil.timeDifference(System.currentTimeMillis(), getLastUpdateTime(), getTimeBetweenUpdates());
    }
    
    public long getLastUpdateTime();
    
    public int getTimeBetweenUpdates();
    
}
