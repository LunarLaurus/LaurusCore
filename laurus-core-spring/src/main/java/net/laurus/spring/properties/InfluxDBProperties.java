package net.laurus.spring.properties;

import lombok.Data;
import lombok.NonNull;

@Data
public class InfluxDBProperties {

    @NonNull
    private String url;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String database;
    
}