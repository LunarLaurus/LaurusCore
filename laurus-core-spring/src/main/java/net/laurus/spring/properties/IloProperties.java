package net.laurus.spring.properties;

import lombok.Data;

@Data
public class IloProperties {

	private String username = "changeme";
	private String password = "changeme";
	private int clientTimeoutConnect = 1000;
	private int clientTimeoutRead = 1000;
	private NetworkProperties network = new NetworkProperties();

}
