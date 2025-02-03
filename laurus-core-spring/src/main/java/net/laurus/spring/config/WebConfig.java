package net.laurus.spring.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Configuration
@Log
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private static final int[] localPorts = { 3000, 32560 };
    private static final String[] protocols = { "http", "https" };
    private static final String[] localHosts = { "localhost", "127.0.0.1" };

    private final ServerProperties serverProperties;
    private final SystemProperties systemConfig;

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(true);
        filter.setAfterMessagePrefix("REQUEST DATA: ");
        return filter;
    }

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        log.info("Using Environment IP for CORS: " + systemConfig.getAllowedIp());
        registry
                .addMapping("/**")
                .allowedOrigins(convertSetToArray(getCorsAllowedOrigins()))
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    private static final String[] convertSetToArray(Set<String> set) {
        return set.toArray(new String[0]);
    }

    private Set<String> getCorsAllowedOrigins() {
        final Set<String> corsOriginCache = new HashSet<>();
        int[] localPortsWithCustom = Arrays.copyOf(localPorts, localPorts.length + 1);
        localPortsWithCustom[localPorts.length] = serverProperties.getPort();
        
        for (String proto : protocols) {
            String localClient = proto + "://" + systemConfig.getAllowedIp();
            if (corsOriginCache.add(localClient)) {
                log.info("Mapping " + localClient + " as allowedOrigins for CORS.");
            }
            for (int port : localPortsWithCustom) {
                localClient = proto + "://" + systemConfig.getAllowedIp() + ":" + port;
                if (corsOriginCache.add(localClient)) {
                    log.info("Mapping " + localClient + " as allowedOrigins for CORS.");
                }
                for (String local : localHosts) {
                    localClient = proto + "://" + local + ":" + port;
                    if (corsOriginCache.add(localClient)) {
                        log.info("Mapping " + localClient + " as allowedOrigins for CORS.");
                    }
                }
            }
        }
        return corsOriginCache;
    }
}
