package net.laurus.spring.service;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.laurus.ilo.AbstractIloAuthService;
import net.laurus.network.IloUser;
import net.laurus.spring.properties.SystemProperties;

@Getter
@Slf4j
@Service
@RequiredArgsConstructor
public class IloAuthService extends AbstractIloAuthService {

    private final SystemProperties systemProperties;

    private IloUser defaultIloUser;

    @Override
    public boolean isObfuscated() {
        return systemProperties.obfuscateSecrets();
    }

    @Override
    @PostConstruct
    protected void postConstruct() {
        // Initialize default IloUser
        defaultIloUser = new IloUser(getConfigSuppliedIloUsername(), getConfigSuppliedIloPassword(), isObfuscated());
        this.getIloAuthDataMap().put(getConfigSuppliedIloUsername(), defaultIloUser);

        // Log the successful initialization
        log.info("Default IloUser initialized and added to auth data map: {}", getConfigSuppliedIloUsername());
    }

	@Override
	public String getConfigSuppliedIloUsername() {
		return systemProperties.getIlo().getUsername();
	}

	@Override
	public String getConfigSuppliedIloPassword() {
		return systemProperties.getIlo().getPassword();
	}
}
