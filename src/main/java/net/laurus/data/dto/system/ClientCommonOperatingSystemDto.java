package net.laurus.data.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.laurus.data.enums.OperatingSystem;
import net.laurus.interfaces.NetworkData;
import net.laurus.network.IPv4Address;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientCommonOperatingSystemDto implements NetworkData {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

    private String hostname;
    private IPv4Address systemIp;
    private OperatingSystem operatingSystem;
    private String osVersion;
    private long uptime;
	
}
