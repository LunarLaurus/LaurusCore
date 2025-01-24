package net.laurus.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.laurus.interfaces.NetworkData;

@AllArgsConstructor
@Getter
public enum IpmiImplementation implements NetworkData {
	
    ILO(true),
    DRAC(false),
    UNKNOWN(false);
	
	private static final long serialVersionUID = 0;
    
    boolean currentlySupported;
    
}
