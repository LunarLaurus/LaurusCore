package net.laurus.data.dto.ipmi.ilo;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import net.laurus.data.enums.ilo.IloObjectHealth;
import net.laurus.interfaces.NetworkData;
import net.laurus.util.JsonUtil;

@Value
@AllArgsConstructor
public class IloProcessorSummary implements NetworkData {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();
	
    int count;
    String model;
    IloObjectHealth status;

    public static IloProcessorSummary from(@NonNull JsonNode node) {

        int count = node.path("Count").asInt();
        String model = JsonUtil.getSafeTextValueFromNode(node, "Model");
        IloObjectHealth health = IloObjectHealth.valueOf(node.path("Status").path("HealthRollUp").asText().toUpperCase());

        return new IloProcessorSummary(count, model, health);
    }

}
