package net.laurus.data.dto.ipmi.ilo;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import net.laurus.interfaces.NetworkData;
import net.laurus.interfaces.update.ilo.IloUpdatableFeatureWithoutAuth;

@Data
@AllArgsConstructor
public class IloBiosObject implements IloUpdatableFeatureWithoutAuth {

	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

	String date;
	String version;
	final String family;
	long lastUpdateTime;

	public static IloBiosObject from(@NonNull JsonNode biosNode) {
		String date = biosNode.path("Date").asText("N/A");
		String version = biosNode.path("VersionString").asText("N/A");
		String family = biosNode.path("Family").asText("N/A");
		return new IloBiosObject(date, version, family, System.currentTimeMillis());
	}

	@Override
	public void update(@NonNull JsonNode biosNode) {
		date = biosNode.path("Date").asText("N/A");
		version = biosNode.path("VersionString").asText("N/A");
		lastUpdateTime = System.currentTimeMillis();
	}

	@Override
	public int getTimeBetweenUpdates() {
		return 300;
	}

}
