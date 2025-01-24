package net.laurus.util;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class JsonUtilTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testGetSafeTextValueFromNode() throws Exception {
        JsonNode node = mapper.readTree("{\"key\": \"value\", \"invalid\": \"value�\"}");

        // Valid text value
        assertEquals("value", JsonUtil.getSafeTextValueFromNode(node, "key"));

        // Missing key
        assertEquals("Unknown", JsonUtil.getSafeTextValueFromNode(node, "missingKey"));

        // Invalid character in value
        assertEquals("Unknown", JsonUtil.getSafeTextValueFromNode(node, "invalid"));
    }

    @Test
    void testGetSafeIntValueFromNode() throws Exception {
        JsonNode node = mapper.readTree("{\"key\": 42, \"notAnInt\": \"value\"}");

        // Valid integer value
        assertEquals(42, JsonUtil.getSafeIntValueFromNode(node, "key"));

        // Missing key
        assertEquals(-1, JsonUtil.getSafeIntValueFromNode(node, "missingKey"));

        // Non-integer value
        assertEquals(-1, JsonUtil.getSafeIntValueFromNode(node, "notAnInt"));
    }

    @Test
    void testGetSafeIntValueFromNodeWithDefaultValue() throws Exception {
        JsonNode node = mapper.readTree("{\"key\": 42, \"notAnInt\": \"value\"}");

        // Valid integer value
        assertEquals(42, JsonUtil.getSafeIntValueFromNode(node, "key", 99));

        // Missing key, return default value
        assertEquals(99, JsonUtil.getSafeIntValueFromNode(node, "missingKey", 99));

        // Non-integer value, return default value
        assertEquals(99, JsonUtil.getSafeIntValueFromNode(node, "notAnInt", 99));
    }

    @Test
    void testContainsInvalidCharacter() {
        // Valid string
        assertFalse(JsonUtil.containsInvalidCharacter("valid string"));

        // String with invalid character
        assertTrue(JsonUtil.containsInvalidCharacter("invalid�string"));
    }

    @Test
    void testToPrettyJson_ValidObject() throws Exception {
        Object data = new Object() {
            public String name = "Test";
            public int value = 42;
        };

        String result = JsonUtil.toPrettyJson(data);

        assertTrue(result.contains("\"name\" : \"Test\""));
        assertTrue(result.contains("\"value\" : 42"));
    }

    @Test
    void testToPrettyJson_InvalidObject() {
        Object invalidObject = new Object() {
            // Objects that fail Jackson serialization
            private final Object self = this;
        };

        assertThrows(IOException.class, () -> JsonUtil.toPrettyJson(invalidObject));
    }
}
