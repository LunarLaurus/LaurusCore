package net.laurus.spring.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import net.laurus.thread.LaurusThreadFactory;

/**
 * Configuration for scheduled tasks using Spring's scheduling mechanism.
 */
@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

    /**
     * Defines a thread pool for scheduled tasks.
     *
     * @return an {@link Executor} for scheduling.
     */
    @Bean
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(10, new LaurusThreadFactory("Yogg-Saron", false));
    }

    /**
     * Configures scheduled tasks to use the defined thread pool.
     *
     * @param taskRegistrar The task registrar.
     */
    @Override
    public void configureTasks(@NonNull ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }
}
