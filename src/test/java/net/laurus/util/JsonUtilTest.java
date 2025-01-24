package net.laurus.util;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class JsonUtilTest {

    @Test
    void testGetSafeTextValueFromNode() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree("{\"key\": \"value\"}");

        assertEquals("value", JsonUtil.getSafeTextValueFromNode(node, "key"));
        assertEquals("Unknown", JsonUtil.getSafeTextValueFromNode(node, "missingKey"));
    }

    @Test
    void testGetSafeIntValueFromNode() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree("{\"key\": 42}");

        assertEquals(42, JsonUtil.getSafeIntValueFromNode(node, "key"));
        assertEquals(-1, JsonUtil.getSafeIntValueFromNode(node, "missingKey"));
    }
}
