package net.laurus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Getter
@Slf4j
public class SecretsProperties {

    @Value("${system.obfuscate-secrets:true}")
    @Accessors(fluent = true)
    private boolean obfuscate;

    @PostConstruct
    public void init() {
        log.info("SecretsConfig initialized with obfuscate: {}", obfuscate);
    }
	
}
