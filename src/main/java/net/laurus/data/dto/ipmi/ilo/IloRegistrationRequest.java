package net.laurus.data.dto.ipmi.ilo;

import lombok.Value;
import net.laurus.interfaces.NetworkData;
import net.laurus.network.IPv4Address;

@Value
public class IloRegistrationRequest implements NetworkData {

    private static final long serialVersionUID = 2L;

    IPv4Address iloAddress;
    
}

