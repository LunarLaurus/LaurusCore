package net.laurus.spring.config;

import lombok.Data;

@Data
public class InfluxDBProperties {
	
    private String url;
    private String username;
    private String password;
    private String database;
    
}