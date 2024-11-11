package net.laurus.util;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonUtil {

    public static String getSafeTextValueFromNode(JsonNode node, String key){
        JsonNode obj = node.get(key);
        if (obj != null && obj.isTextual()) {
            String text = obj.asText();
            return text.trim();
        }
        return "Unknown";        
    }
    
    public static int getSafeIntValueFromNode(JsonNode node, String key){
        JsonNode obj = node.get(key);
        if (obj != null && obj.isNumber()) {
            return obj.asInt();
        }
        return -1;        
    }
    
}
