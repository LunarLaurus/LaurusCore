package net.laurus.data.enums;

import lombok.AllArgsConstructor;
import net.laurus.interfaces.NetworkData;

@AllArgsConstructor
public enum IpmiImplementation implements NetworkData {
	
    ILO(true),
    DRAC(false),
    UNKNOWN(false);
	
	private static final long serialVersionUID = 0;
    
    boolean currentlySupported;
    
}
