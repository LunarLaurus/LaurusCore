package net.laurus.spring.service;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.laurus.ilo.AbstractIloAuthService;
import net.laurus.network.IloUser;
import net.laurus.spring.properties.SystemProperties;

/**
 * Service handling iLO authentication.
 */
@Getter
@Slf4j
@Service
@RequiredArgsConstructor
public class IloAuthService extends AbstractIloAuthService {

    /**
     * System-wide configuration properties.
     */
    private final SystemProperties systemProperties;

    /**
     * Default iLO user for authentication.
     */
    private IloUser defaultIloUser;

    /**
     * Determines if secrets should be obfuscated in logs.
     *
     * @return {@code true} if secrets should be obfuscated, otherwise {@code false}.
     */
    @Override
    public boolean isObfuscated() {
        return systemProperties.obfuscateSecrets();
    }

    /**
     * Initializes the default iLO user and registers it in the authentication data map.
     */
    @Override
    @PostConstruct
    protected void postConstruct() {
        // Initialize default IloUser
        defaultIloUser = new IloUser(getConfigSuppliedIloUsername(), getConfigSuppliedIloPassword(), isObfuscated());
        this.getIloAuthDataMap().put(getConfigSuppliedIloUsername(), defaultIloUser);

        // Log the successful initialization
        log.info("Default IloUser initialized and added to auth data map: {}", getConfigSuppliedIloUsername());
    }

    /**
     * Retrieves the configured iLO username.
     *
     * @return the iLO username from configuration.
     */
    @Override
    public String getConfigSuppliedIloUsername() {
        return systemProperties.getIlo().getUsername();
    }

    /**
     * Retrieves the configured iLO password.
     *
     * @return the iLO password from configuration.
     */
    @Override
    public String getConfigSuppliedIloPassword() {
        return systemProperties.getIlo().getPassword();
    }
}
