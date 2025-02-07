package net.laurus.pojo.config;

import lombok.Builder;
import lombok.Value;

/**
 * Configuration object for RabbitMQ queue properties.
 */
@Value
@Builder
public class RabbitQueueConfig {

    /**
     * Determines if the queue is durable (persists across restarts).
     * Defaults to {@code true}.
     */
    @Builder.Default
    private boolean durable = true;

    /**
     * Determines if the queue is exclusive (only accessible by one connection).
     * Defaults to {@code false}.
     */
    @Builder.Default
    private boolean exclusive = false;

    /**
     * Determines if the queue auto-deletes when no consumers are connected.
     * Defaults to {@code false}.
     */
    @Builder.Default
    private boolean autoDelete = false;

    /**
     * Time-to-live (TTL) for messages in milliseconds.
     * If {@code null}, messages do not expire.
     */
    @Builder.Default
    private Integer ttl = null;
}
