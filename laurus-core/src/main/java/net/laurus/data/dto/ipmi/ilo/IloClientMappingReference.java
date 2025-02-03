package net.laurus.data.dto.ipmi.ilo;

import lombok.Value;
import net.laurus.interfaces.NetworkData;
import net.laurus.network.IPv4Address;

@Value
public class IloClientMappingReference implements NetworkData {

    private static final long serialVersionUID = 2L;

    IPv4Address iloAddress;
    String hostClientId;
    String serialNumber;
    String serverModel;
    String serverId;
    String serverUuid;
    String productId;
    String iloVersion;
    String iloText;
    String iloFwBuildDate;
    String iloSerialNumber;
    String iloUuid;
    
}

