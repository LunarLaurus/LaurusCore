package net.laurus.spring.properties;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import net.laurus.network.IPv4Address;

@Data
public class NetworkProperties {
	
	private String baseIp = "0.0.0.0";
	private String subnetMask = "0.0.0.0";    
    private IPv4Address baseAddress;

	@PostConstruct
	public void setupBaseIp() {
		baseAddress = new IPv4Address(getBaseIp());
	}
}