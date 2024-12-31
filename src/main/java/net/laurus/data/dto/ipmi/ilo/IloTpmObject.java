package net.laurus.data.dto.ipmi.ilo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;
import net.laurus.data.enums.ilo.IloTpmModuleType;
import net.laurus.interfaces.NetworkData;
import net.laurus.interfaces.update.ilo.IloUpdatableFeatureWithoutAuth;
import net.laurus.util.JsonUtil;

@Data
@AllArgsConstructor
public class IloTpmObject implements IloUpdatableFeatureWithoutAuth {

	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

	String status;
	List<IloTpm> trustedModules;
	long lastUpdateTime;

	public static IloTpmObject from(@NonNull JsonNode tpmNode) {		
		List<IloTpm> tpm;
		if (tpmNode.isArray()) {
			tpm = new ArrayList<>();
			for (JsonNode tpmDevice : tpmNode) {
				tpm.add(IloTpm.from(tpmDevice));
			}
		}
		else {
			tpm = List.of();
		}
		String license = tpm.isEmpty() ? "NotPresent" : tpm.get(0).getStatus();
		return new IloTpmObject(license, List.of(), System.currentTimeMillis());
	}

	@Override
	public void update(@NonNull JsonNode licenseNode) {
		status = JsonUtil.getSafeTextValueFromNode(licenseNode, "Status");
		lastUpdateTime = System.currentTimeMillis();
	}

	@Override
	public int getTimeBetweenUpdates() {
		return 300;
	}
	
	@Value
	public static class IloTpm implements IloUpdatableFeatureWithoutAuth {
		
		private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

		String status;
		IloTpmModuleType type;
		long lastUpdateTime;

		@Override
		public int getTimeBetweenUpdates() {
			return 300;
		}

		public static IloTpm from(@NonNull JsonNode tpmDevice) {
			String status = tpmDevice.get("Status").asText("N/A");
			return new IloTpm(status, IloTpmModuleType.UNKNOWN, System.currentTimeMillis());
		}

		@Override
		public void update(@NonNull JsonNode updateDataNode) {
			// TODO - I don't have any TPM to test with			
		}
		
	}

}
