package net.laurus.data.dto.system;

import java.io.Serializable;

import lombok.Value;
import net.laurus.data.impi.IpmiInfo;

@Value
public class SystemIpmiInfo implements Serializable {

    private boolean hasIpmi;

    private IpmiInfo ipmiSystem;
}
