package net.laurus.util;

import org.json.JSONObject;
import org.json.XML;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.experimental.UtilityClass;
import net.laurus.Constant;

@UtilityClass
public class XmlToJsonUtil {

    public static JsonNode convertXmlToJson(String xmlString) throws JsonMappingException, JsonProcessingException {
        JSONObject json = XML.toJSONObject(xmlString);
        String jsonString = json.toString(4);
        return Constant.JSON_MAPPER.readTree(jsonString);
    }

}
