package net.laurus.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class VersionReaderTest {

    @Test
    void testGetVersion_NonJarPath() throws IOException {
        String result = VersionReader.getVersion();
        assertNull(result, "Should return null if not running in a JAR environment.");
    }
}

