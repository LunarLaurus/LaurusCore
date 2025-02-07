package net.laurus.spring.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NonNull;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitProperties {

    @NonNull
    private String host;
    private int port;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private List<String> allowedSerializingClasses;
    
}