package net.laurus.spring.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@ConfigurationPropertiesScan("net.laurus.spring.properties")
@ComponentScan("net.laurus.spring")
public class LaurusCoreAutoConfiguration {
}
