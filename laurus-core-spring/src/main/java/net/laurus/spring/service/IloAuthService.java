package net.laurus.spring.service;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.laurus.ilo.AbstractIloAuthService;
import net.laurus.network.IloUser;
import net.laurus.spring.properties.IloProperties;
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
     * iLO configuration properties.
     */
    private final IloProperties iloProperties;

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
     * If the username is null or empty, returns a default value of "changeme".
     *
     * @return the iLO username from configuration, or "changeme" if not set.
     */
    @Override
    public String getConfigSuppliedIloUsername() {
        String username = iloProperties.getUsername();
        return (username == null || username.isBlank()) ? "changeme" : username;
    }

    /**
     * Retrieves the configured iLO password.
     * If the password is null or empty, returns a default value of "changeme".
     *
     * @return the iLO password from configuration, or "changeme" if not set.
     */
    @Override
    public String getConfigSuppliedIloPassword() {
        String password = iloProperties.getPassword();
        return (password == null || password.isBlank()) ? "changeme" : password;
    }

}
