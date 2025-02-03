package net.laurus.spring.config;

import lombok.Data;

@Data
public class IloProperties {

	private String username;
	private String password;
	private int clientTimeoutConnect;
	private int clientTimeoutRead;
	private NetworkProperties network;

}
