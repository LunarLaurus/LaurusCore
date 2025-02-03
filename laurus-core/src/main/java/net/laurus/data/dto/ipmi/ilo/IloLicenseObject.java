package net.laurus.data.dto.ipmi.ilo;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.laurus.interfaces.NetworkData;
import net.laurus.interfaces.update.ilo.IloUpdatableFeatureWithoutAuth;
import net.laurus.util.JsonUtil;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IloLicenseObject implements IloUpdatableFeatureWithoutAuth {

	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

	String license;
	String licenseKey;
	String licenseType;
	long lastUpdateTime;

	public static IloLicenseObject from(@NonNull JsonNode licenseNode) throws Exception {

		String license = JsonUtil.getSafeTextValueFromNode(licenseNode, "License");
		String licenseKey = JsonUtil.getSafeTextValueFromNode(licenseNode, "LicenseKey");
		String licenseType = JsonUtil.getSafeTextValueFromNode(licenseNode, "LicenseType");

		return new IloLicenseObject(license, licenseKey, licenseType, System.currentTimeMillis());
	}

	@Override
	public void update(@NonNull JsonNode licenseNode) {
		license = JsonUtil.getSafeTextValueFromNode(licenseNode, "License");
		licenseKey = JsonUtil.getSafeTextValueFromNode(licenseNode, "LicenseKey");
		licenseType = JsonUtil.getSafeTextValueFromNode(licenseNode, "LicenseType");
		lastUpdateTime = System.currentTimeMillis();
	}

	@Override
	public int getTimeBetweenUpdates() {
		return 300;
	}

}
