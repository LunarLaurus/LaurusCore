package net.laurus.data.enums;

import java.io.Serializable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum IpmiImplementation implements Serializable {

    ILO(true),
    DRAC(false),
    UNKNOWN(false);
    
    boolean currentlySupported;
    
}
