package net.laurus.spring.util;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.laurus.pojo.config.RabbitQueueConfig;

/**
 * Utility class for managing RabbitMQ queues.
 */
@Slf4j
@UtilityClass
public class RabbitQueueUtils {

    /**
     * Creates a queue with the specified configuration.
     *
     * @param rabbitAdmin The RabbitAdmin instance.
     * @param queueName   The name of the queue.
     * @param config      The RabbitQueueConfig object defining queue properties.
     * @return true if the queue was created successfully, false otherwise.
     */
    public boolean createQueue(AmqpAdmin rabbitAdmin, String queueName, RabbitQueueConfig config) {
        if (rabbitAdmin == null || queueName == null || queueName.isBlank() || config == null) {
            log.error("Invalid arguments: RabbitAdmin, queueName, and config must be provided.");
            return false;
        }
        try {
            log.debug("Attempting to declare queue: {} with config {}", queueName, config);
            
            QueueBuilder queueBuilder = config.isDurable() 
                    ? QueueBuilder.durable(queueName) 
                    : QueueBuilder.nonDurable(queueName);
            
            if (config.isAutoDelete()) {
            	queueBuilder.autoDelete();
            }
            if (config.isExclusive()) {
            	queueBuilder.exclusive();
            }
            if (config.getTtl() != null) {
                queueBuilder.ttl(config.getTtl());
            }

            Queue queue = queueBuilder.build();
            rabbitAdmin.declareQueue(queue);
            
            log.info("Queue '{}' created successfully with config: {}", queueName, config);
            return true;
        } catch (Exception e) {
            log.error("Failed to create queue '{}': {}", queueName, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Deletes a queue.
     *
     * @param rabbitAdmin The RabbitAdmin instance.
     * @param queueName   The name of the queue.
     * @return true if the queue was deleted successfully, false otherwise.
     */
    public boolean deleteQueue(AmqpAdmin rabbitAdmin, String queueName) {
        if (rabbitAdmin == null || queueName == null || queueName.isBlank()) {
            log.error("Invalid arguments: RabbitAdmin and queueName must be provided.");
            return false;
        }
        try {
            log.debug("Attempting to delete queue: {}", queueName);
            boolean deleted = rabbitAdmin.deleteQueue(queueName);
            if (deleted) {
                log.info("Queue '{}' deleted successfully.", queueName);
            } else {
                log.warn("Queue '{}' does not exist or could not be deleted.", queueName);
            }
            return deleted;
        } catch (Exception e) {
            log.error("Failed to delete queue '{}': {}", queueName, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Checks if a queue exists.
     *
     * @param rabbitAdmin The RabbitAdmin instance.
     * @param queueName   The name of the queue.
     * @return true if the queue exists, false otherwise.
     */
    public boolean doesQueueExist(AmqpAdmin rabbitAdmin, String queueName) {
        if (rabbitAdmin == null || queueName == null || queueName.isBlank()) {
            log.error("Invalid arguments: RabbitAdmin and queueName must be provided.");
            return false;
        }
        try {
            log.debug("Checking existence of queue: {}", queueName);
            Object queueInfo = rabbitAdmin.getQueueProperties(queueName);
            boolean exists = queueInfo != null;
            log.info("Queue '{}' existence check: {}", queueName, exists);
            return exists;
        } catch (Exception e) {
            log.error("Failed to check existence of queue '{}': {}", queueName, e.getMessage(), e);
            return false;
        }
    }
}
