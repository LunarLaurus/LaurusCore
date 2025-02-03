package net.laurus.spring.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import net.laurus.network.IPv4Address;

@Data
public class NetworkProperties {
	private String baseIp;
	private String subnetMask;

	private IPv4Address baseAddress;

	@PostConstruct
	public void setupBaseIp() {
		baseAddress = new IPv4Address(getBaseIp());
	}
}