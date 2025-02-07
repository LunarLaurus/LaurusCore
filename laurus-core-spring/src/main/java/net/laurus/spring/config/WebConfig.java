package net.laurus.spring.config;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.laurus.spring.properties.ServerProperties;
import net.laurus.spring.properties.SystemProperties;

/**
 * Web configuration class handling CORS, logging, and static resource mapping.
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final Set<Integer> LOCAL_PORTS = Set.of(3000, 32560);
    private static final Set<String> PROTOCOLS = Set.of("http", "https");
    private static final Set<String> LOCAL_HOSTS = Set.of("localhost", "127.0.0.1");
    private static final Set<String> ALLOWED_METHODS = Set.of("GET", "POST", "OPTIONS");

    @NonNull
    private final ServerProperties serverProperties;
    @NonNull
    private final SystemProperties systemConfig;

    /**
     * Configures request logging to include headers, payload, and query parameters.
     *
     * @return a {@link CommonsRequestLoggingFilter} instance.
     */
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

    /**
     * Configures CORS settings to allow specified origins and methods.
     *
     * @param registry The CORS registry.
     */
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        log.info("Using Environment IP for CORS: " + systemConfig.getAllowedIp());
        registry.addMapping("/**")
                .allowedOrigins(getCorsAllowedOrigins().toArray(String[]::new))
                .allowedMethods(ALLOWED_METHODS.toArray(String[]::new))
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    /**
     * Configures static resource handling.
     *
     * @param registry The resource handler registry.
     */
    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    /**
     * Determines allowed CORS origins dynamically.
     *
     * @return a set of allowed CORS origin URLs.
     */
    private Set<String> getCorsAllowedOrigins() {
        String allowedIp = systemConfig.getAllowedIp();
        int serverPort = serverProperties.getPort();
        return PROTOCOLS.stream()
                .flatMap(proto -> {
                    Stream<String> baseUrls = Stream.of(
                            proto + "://" + allowedIp,
                            proto + "://" + allowedIp + ":" + serverPort
                    );
                    Stream<String> portUrls = LOCAL_PORTS.stream()
                            .flatMap(port -> Stream.concat(
                                    Stream.of(proto + "://" + allowedIp + ":" + port),
                                    LOCAL_HOSTS.stream().map(host -> proto + "://" + host + ":" + port)
                            ));
                    return Stream.concat(baseUrls, portUrls);
                })
                .peek(url -> log.info("Mapping " + url + " as allowedOrigins for CORS."))
                .collect(Collectors.toUnmodifiableSet());
    }
}
