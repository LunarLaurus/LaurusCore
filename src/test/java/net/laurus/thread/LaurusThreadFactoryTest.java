package net.laurus.thread;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LaurusThreadFactoryTest {

    @Test
    void testThreadCreation() {
        LaurusThreadFactory factory = new LaurusThreadFactory("TestThread", true);
        Thread thread = factory.newThread(() -> {});

        assertNotNull(thread);
        assertEquals("TestThread-1", thread.getName());
        assertTrue(thread.isDaemon());
    }
}
