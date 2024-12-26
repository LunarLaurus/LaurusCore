package net.laurus.data.dto.ipmi.ilo;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.laurus.interfaces.IloUpdatableFeature;
import net.laurus.interfaces.NetworkData;
import net.laurus.network.IPv4Address;

@Data
@AllArgsConstructor
public class IloBiosObject implements IloUpdatableFeature, NetworkData {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

    String date;
    String version;
    final String family;
    long lastUpdateTime;

	public static IloBiosObject from(JsonNode biosNode) {
		String date = biosNode.path("Date").asText();
		String version = biosNode.path("VersionString").asText();
		String family = biosNode.path("Family").asText();	
		return new IloBiosObject(date, version, family, System.currentTimeMillis());
	}
    
	@Override
	public void update(IPv4Address ip, String authData, JsonNode biosNode) {
		date = biosNode.path("Date").asText();
		version = biosNode.path("VersionString").asText();		
	}
	
	@Override
	public int getTimeBetweenUpdates() {
		return 300;
	}

}
