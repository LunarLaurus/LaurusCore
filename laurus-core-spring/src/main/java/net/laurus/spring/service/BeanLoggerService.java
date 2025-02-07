package net.laurus.spring.service;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeanLoggerService {

    private final ApplicationContext applicationContext;

    @PostConstruct
    public void logBeans() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
        	log.info("Found bean: {}", beanName);
        }
    }
}
