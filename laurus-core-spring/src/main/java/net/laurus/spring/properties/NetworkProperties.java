package net.laurus.spring.properties;

import jakarta.annotation.PostConstruct;
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
    private String baseIp = "0.0.0.0";

    /**
     * Subnet mask of the network.
     */
    private String subnetMask = "0.0.0.0";

    /**
     * Parsed IPv4 address based on the base IP.
     */
    private IPv4Address baseAddress;

    /**
     * Initializes the baseAddress field after object construction.
     */
    @PostConstruct
    public void setupBaseIp() {
        baseAddress = new IPv4Address(getBaseIp());
    }
}
