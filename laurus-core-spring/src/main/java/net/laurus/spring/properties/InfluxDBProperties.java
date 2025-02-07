package net.laurus.spring.properties;

import lombok.Data;

/**
 * Configuration properties for InfluxDB connection.
 */
@Data
public class InfluxDBProperties {

    /**
     * URL of the InfluxDB instance.
     */
    private String url = "http://localhost";

    /**
     * Username for InfluxDB authentication.
     */
    private String username = "changeme";

    /**
     * Password for InfluxDB authentication.
     */
    private String password = "changeme";

    /**
     * Name of the InfluxDB database.
     */
    private String database = "changeme";
}
