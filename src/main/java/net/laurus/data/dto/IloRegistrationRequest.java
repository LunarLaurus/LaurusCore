package net.laurus.data.dto;

import java.io.Serializable;

import lombok.Value;
import net.laurus.network.IPv4Address;

@Value
@SuppressWarnings("serial")
public class IloRegistrationRequest implements Serializable {

	IPv4Address iloAddress;
	String hostClientId;
	
}
