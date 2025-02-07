package net.laurus.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.laurus.interfaces.NetworkData;

/**
 * Enum representing different IPMI implementations.
 */
@AllArgsConstructor
@Getter
public enum IpmiImplementation implements NetworkData {

    /** HP iLO (Intelligent Provisioning Management Interface) */
    ILO(true),

    /** Dell DRAC (Dell Remote Access Controller) */
    DRAC(false),

    /** Unknown IPMI implementation */
    UNKNOWN(false);

    private static final long serialVersionUID = 0;

    private final boolean currentlySupported;
}
