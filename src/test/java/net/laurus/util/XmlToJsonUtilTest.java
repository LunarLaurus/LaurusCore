package net.laurus.util;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

class XmlToJsonUtilTest {

    @Test
    void testConvertXmlToJson_ValidXml() throws Exception {
        String xml = "<root><child>value</child></root>";
        JsonNode result = XmlToJsonUtil.convertXmlToJson(xml);

        assertNotNull(result);
        assertEquals("value", result.get("root").get("child").asText());
    }

    @Test
    void testConvertXmlToJson_InvalidXml() {
        String invalidXml = "<root><child>value</root>";

        assertThrows(Exception.class, () -> XmlToJsonUtil.convertXmlToJson(invalidXml));
    }
}

