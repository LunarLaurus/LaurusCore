package net.laurus.data.dto.ipmi.ilo;

import java.io.Serializable;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Value;
import net.laurus.data.enums.ilo.Health;
import net.laurus.util.JsonUtil;

@Value
@AllArgsConstructor
@SuppressWarnings("serial")
public class IloProcessorSummary implements Serializable {

    int count;
    String model;
    Health status;

    public static IloProcessorSummary from(JsonNode node) {

        int count = node.path("Count").asInt();
        String model = JsonUtil.getSafeTextValueFromNode(node, "Model");
        Health health = Health.valueOf(node.path("Status").path("HealthRollUp").asText().toUpperCase());

        return new IloProcessorSummary(count, model, health);
    }

}
