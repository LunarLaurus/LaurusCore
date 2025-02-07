package net.laurus.pojo.config;

import lombok.Builder;
import lombok.Value;

/**
 * Configuration object for RabbitMQ queue properties.
 */
@Value
@Builder
public class RabbitQueueConfig {

	@Builder.Default
	private boolean durable = true; // Persistent queue
	@Builder.Default
	private boolean exclusive = false; // Can be used by multiple consumers
	@Builder.Default
	private boolean autoDelete = false; // Queue remains after consumers disconnect
	@Builder.Default
	private Integer ttl = null; // Time-to-live in milliseconds (null means no expiration)

}
