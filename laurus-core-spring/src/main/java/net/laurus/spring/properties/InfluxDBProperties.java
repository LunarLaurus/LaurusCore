package net.laurus.spring.properties;

import lombok.Data;

@Data
public class InfluxDBProperties {

    private String url = "http://localhost";
    private String username = "changeme";
    private String password = "changeme";
    private String database = "changeme";
    
}