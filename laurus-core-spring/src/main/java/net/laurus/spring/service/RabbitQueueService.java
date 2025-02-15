package net.laurus.spring.service;

import java.io.IOException;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.laurus.pojo.config.RabbitQueueConfig;
import net.laurus.spring.util.RabbitQueueUtils;
import net.laurus.util.RabbitMqUtils;

/**
 * Service for managing RabbitMQ queues.
 * <p>
 * This class provides utilities to create, delete, check, and interact with
 * RabbitMQ queues. It wraps {@link RabbitQueueUtils} and simplifies queue
 * operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitQueueService {

	/**
	 * The RabbitMQ admin bean used for queue management.
	 */
	@Getter
	private final AmqpAdmin rabbitAdminBean;

	/**
	 * The RabbitMQ template bean used for sending and receiving messages.
	 */
	@Getter
	private final RabbitTemplate rabbitQueueBean;

	/**
	 * Default queue configuration with a TTL of 3 hours (10,800,000 milliseconds).
	 */
	public static final RabbitQueueConfig DEFAULT_QUEUE_CONFIG = RabbitQueueConfig.builder().ttl(3600000 * 3).build();

	/**
	 * Creates a queue with the specified configuration.
	 *
	 * @param queueName The name of the queue.
	 * @param config    The {@link RabbitQueueConfig} object defining queue
	 *                  properties.
	 * @return {@code true} if the queue was created successfully, otherwise
	 *         {@code false}.
	 */
	public boolean createQueue(String queueName, RabbitQueueConfig config) {
		log.debug("Request to create queue: {} with config {}", queueName, config);
		boolean result = RabbitQueueUtils.createQueue(rabbitAdminBean, queueName, config);

		if (result) {
			log.info("Queue '{}' created successfully.", queueName);
		}
		return result;
	}

	/**
	 * Deletes a queue.
	 *
	 * @param queueName The name of the queue.
	 * @return {@code true} if the queue was deleted successfully, otherwise
	 *         {@code false}.
	 */
	public boolean deleteQueue(String queueName) {
		log.debug("Request to delete queue: {}", queueName);
		boolean result = RabbitQueueUtils.deleteQueue(rabbitAdminBean, queueName);

		if (result) {
			log.info("Queue '{}' deleted successfully.", queueName);
		}
		return result;
	}

	/**
	 * Checks if a queue exists.
	 *
	 * @param queueName The name of the queue.
	 * @return {@code true} if the queue exists, otherwise {@code false}.
	 */
	public boolean doesQueueExist(String queueName) {
		log.debug("Checking if queue '{}' exists.", queueName);
		boolean exists = RabbitQueueUtils.doesQueueExist(rabbitAdminBean, queueName);
		log.info("Queue '{}' existence status: {}", queueName, exists);
		return exists;
	}

	/**
	 * Sends a message to a queue.
	 *
	 * @param <T>            The type of the message object.
	 * @param queueName      The name of the queue.
	 * @param objectForQueue The object to send as a message.
	 * @param compressed     Whether the message should be compressed.
	 */
	public <T> void sendMessage(String queueName, T objectForQueue, boolean compressed) {
		log.debug("Sending message to queue '{}': {}", queueName, objectForQueue);
		try {
			byte[] payload = RabbitMqUtils.preparePayload(objectForQueue, compressed);
			rabbitQueueBean.convertAndSend(queueName, payload);
			log.info("Message sent to queue '{}'", queueName);
		} catch (IOException e) {
			log.error("Failed to send message to queue '{}'", queueName, e);
		}
	}

	/**
	 * Peeks at a message in the queue without removing it.
	 *
	 * @param queueName The name of the queue.
	 * @return The message content as a string, or {@code null} if the queue is
	 *         empty.
	 */
	public String peekMessage(String queueName) {
		log.debug("Peeking message from queue '{}'", queueName);
		Message message = rabbitQueueBean.receive(queueName, 1); // Short timeout to peek

		if (message != null) {
			String body = new String(message.getBody());
			log.info("Peeked message from queue '{}': {}", queueName, body);
			return body;
		}

		log.warn("No messages available to peek in queue '{}'", queueName);
		return null;
	}

	/**
	 * Retrieves the number of messages in a queue.
	 *
	 * @param queueName The name of the queue.
	 * @return The number of messages in the queue, or -1 if the queue does not
	 *         exist.
	 */
	public int getQueueMessageCount(String queueName) {
		log.debug("Checking message count for queue '{}'", queueName);

		if (!doesQueueExist(queueName)) {
			log.warn("Queue '{}' does not exist. Returning -1.", queueName);
			return -1;
		}

		var queueProperties = rabbitAdminBean.getQueueProperties(queueName);
		if (queueProperties == null || !queueProperties.containsKey("QUEUE_MESSAGE_COUNT")) {
			log.warn("Could not retrieve message count for queue '{}'. Returning -1.", queueName);
			return -1;
		}

		Integer messageCount = (Integer) queueProperties.get("QUEUE_MESSAGE_COUNT");
		int count = messageCount != null ? messageCount : 0;
		log.info("Queue '{}' has {} messages.", queueName, count);
		return count;
	}
}
