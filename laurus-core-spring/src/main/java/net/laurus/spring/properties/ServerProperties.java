package net.laurus.spring.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "server")
public class ServerProperties {

    private int port;

}
