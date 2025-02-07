package net.laurus.spring.properties;

import lombok.Data;
import net.laurus.network.IPv4Address;

/**
 * Configuration properties for network settings.
 */
@Data
public class NetworkProperties {

    /**
     * Base IP address of the network.
     */
    private String baseIp;

    /**
     * Subnet mask of the network.
     */
    private String subnetMask;

    /**
     * Cached parsed IPv4 address based on the base IP.
     */
    private IPv4Address baseAddress;

    /**
     * Retrieves the parsed IPv4 address.
     * If the baseAddress is not initialized, it is created using baseIp.
     *
     * @return The parsed {@link IPv4Address} object.
     */
    public IPv4Address getBaseAddress() {
        if (baseAddress == null && baseIp != null) {
            baseAddress = new IPv4Address(baseIp);
        }
        return baseAddress;
    }
}
