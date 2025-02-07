package net.laurus.spring.properties;

import lombok.Data;

/**
 * Configuration properties for iLO (Integrated Lights-Out) authentication and network settings.
 */
@Data
public class IloProperties {

    /**
     * Default username for iLO access.
     * Should be changed in production environments.
     */
    private String username = "changeme";

    /**
     * Default password for iLO access.
     * Should be changed in production environments.
     */
    private String password = "changeme";

    /**
     * Timeout (in milliseconds) for client connection attempts.
     */
    private int clientTimeoutConnect = 1000;

    /**
     * Timeout (in milliseconds) for client read operations.
     */
    private int clientTimeoutRead = 1000;

    /**
     * Network configuration properties associated with iLO.
     */
    private NetworkProperties network = new NetworkProperties();
}
