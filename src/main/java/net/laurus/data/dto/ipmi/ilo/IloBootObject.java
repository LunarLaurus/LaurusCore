package net.laurus.data.dto.ipmi.ilo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import net.laurus.data.enums.ilo.IloBootSourceOverrideTarget;
import net.laurus.interfaces.NetworkData;
import net.laurus.interfaces.update.ilo.IloUpdatableFeatureWithoutAuth;

@Data
@AllArgsConstructor
public class IloBootObject implements IloUpdatableFeatureWithoutAuth {

	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

	boolean bootSourceOverrideEnabled;
	List<IloBootSourceOverrideTarget> bootSourceOverrideSupported;
	IloBootSourceOverrideTarget bootSourceOverrideTarget;

	String uefiTargetBootSourceOverride;
	List<String> uefiTargetBootSourceOverrideSupported;

	long lastUpdateTime;

	public static IloBootObject from(@NonNull JsonNode biosNode) {

		var bootSourceOverrideEnabled = biosNode.path("BootSourceOverrideEnabled").asBoolean(false);
		var bootSourceOverrideSupported = IloBootSourceOverrideTarget
				.getSupported(biosNode.path("BootSourceOverrideSupported"));
		var bootSourceOverrideTarget = IloBootSourceOverrideTarget
				.get(biosNode.path("IloBootSourceOverrideTarget").asText("N/A"));
		var uefiTargetBootSourceOverride = biosNode.path("UefiTargetBootSourceOverride").asText("N/A");

		List<String> uefiTargetBootSourceOverrideSupported = new ArrayList<>();
		JsonNode supported = biosNode.path("UefiTargetBootSourceOverrideSupported");
		if (supported.isArray()) {
			for (JsonNode target : supported) {
				uefiTargetBootSourceOverrideSupported.add(target.asText("N/A"));
			}
		}
		return new IloBootObject(bootSourceOverrideEnabled, bootSourceOverrideSupported, bootSourceOverrideTarget,
				uefiTargetBootSourceOverrideSupported.isEmpty() ? "UEFI not supported" : uefiTargetBootSourceOverride, uefiTargetBootSourceOverrideSupported, System.currentTimeMillis());
	}

	@Override
	public void update(@NonNull JsonNode biosNode) {
		bootSourceOverrideEnabled = biosNode.path("BootSourceOverrideEnabled").asBoolean(false);
		bootSourceOverrideSupported = IloBootSourceOverrideTarget
				.getSupported(biosNode.path("BootSourceOverrideSupported"));
		bootSourceOverrideTarget = IloBootSourceOverrideTarget
				.get(biosNode.path("IloBootSourceOverrideTarget").asText("N/A"));
		uefiTargetBootSourceOverride = biosNode.path("UefiTargetBootSourceOverride").asText("N/A");
		lastUpdateTime = System.currentTimeMillis();
	}

	@Override
	public int getTimeBetweenUpdates() {
		return 300;
	}

}
