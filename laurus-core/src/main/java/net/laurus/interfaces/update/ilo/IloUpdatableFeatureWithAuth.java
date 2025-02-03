package net.laurus.interfaces.update.ilo;

import net.laurus.network.IPv4Address;

public interface IloUpdatableFeatureWithAuth extends IloUpdatableFeature {

    public void update(IPv4Address ip, String authData);
    
}
