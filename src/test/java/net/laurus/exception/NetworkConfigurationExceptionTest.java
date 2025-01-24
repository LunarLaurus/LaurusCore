package net.laurus.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NetworkConfigurationExceptionTest {

    @Test
    void testExceptionWithMessage() {
        NetworkConfigurationException exception = new NetworkConfigurationException("Test message");

        assertEquals("Test message", exception.getMessage());
    }

    @Test
    void testExceptionWithMessageAndCause() {
        Throwable cause = new RuntimeException("Cause");
        NetworkConfigurationException exception = new NetworkConfigurationException("Test message", cause);

        assertEquals("Test message", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
