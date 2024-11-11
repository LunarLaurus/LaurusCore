package net.laurus.data.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum IpmiImplementation {

    ILO(true),
    DRAC(false),
    UNKNOWN(false);
    
    boolean currentlySupported;
    
}
