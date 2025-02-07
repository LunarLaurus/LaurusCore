package net.laurus.spring.properties;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NonNull;
import net.laurus.network.IPv4Address;

@Data
public class NetworkProperties {
	
    @NonNull
	private String baseIp;
    @NonNull
	private String subnetMask;
    
    private IPv4Address baseAddress;

	@PostConstruct
	public void setupBaseIp() {
		baseAddress = new IPv4Address(getBaseIp());
	}
}