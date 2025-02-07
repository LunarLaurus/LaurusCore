package net.laurus.spring.service;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service that logs all registered Spring beans at startup.
 * <p>
 * This is useful for debugging and verifying that all required beans are properly initialized.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BeanLoggerService {

    /**
     * Spring Application Context, which provides access to all registered beans.
     */
    private final ApplicationContext applicationContext;

    /**
     * Logs all registered bean names in alphabetical order after the application starts.
     */
    @PostConstruct
    public void logBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beanNames); // Sorting for easier readability

        for (String beanName : beanNames) {
            log.info("Found bean: {}", beanName);
        }
    }
}
