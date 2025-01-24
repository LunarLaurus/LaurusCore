package net.laurus.data.dto.system.librehw;

import lombok.Value;
import net.laurus.data.impi.IpmiInfo;
import net.laurus.interfaces.NetworkData;

@Value
public class SystemIpmiInfo implements NetworkData {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();
	
    private boolean hasIpmi;

    private IpmiInfo ipmiSystem;
}
