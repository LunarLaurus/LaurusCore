package net.laurus.spring.properties;

import lombok.Data;
import lombok.NonNull;

@Data
public class IloProperties {

    @NonNull
	private String username;
    @NonNull
	private String password;
	private int clientTimeoutConnect;
	private int clientTimeoutRead;
    @NonNull
	private NetworkProperties network;

}
