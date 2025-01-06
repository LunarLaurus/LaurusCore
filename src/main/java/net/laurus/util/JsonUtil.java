package net.laurus.util;

import static net.laurus.Constant.JSON_MAPPER;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    public static String getSafeTextValueFromNode(JsonNode node, String key){
        JsonNode obj = node.get(key);
        if (obj != null && obj.isTextual() && containsInvalidCharacter(obj.asText())) {
            String text = obj.asText();
            return text.trim();
        }
        return "Unknown";        
    }
    
    public static int getSafeIntValueFromNode(JsonNode node, String key){
        return getSafeIntValueFromNode(node, key, -1);       
    }
    
    public static int getSafeIntValueFromNode(JsonNode node, String key, int defaultValue){
        JsonNode obj = node.get(key);
        if (obj != null && obj.isNumber()) {
            return obj.asInt();
        }
        return defaultValue;        
    }
    
    private static boolean containsInvalidCharacter(@NonNull String text) {
    	if (text.contains("�")) {
    		log.error("ERROR: Found invalid character in "+text);
    		return true;
    	}
    	return false;
    }

    /**
     * Converts an object to a prettified JSON string.
     *
     * @param data The object to convert.
     * @return A prettified JSON string representation of the object.
     * @throws IOException If conversion fails.
     */
    public static String toPrettyJson(Object data) throws IOException {
        return JSON_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }
    
}
