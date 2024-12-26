package net.laurus.data.dto.ipmi.ilo;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.laurus.Constant;
import net.laurus.interfaces.IloUpdatableFeature;
import net.laurus.interfaces.NetworkData;
import net.laurus.network.IPv4Address;
import net.laurus.util.JsonUtil;
import net.laurus.util.NetworkUtil;

@Data
@AllArgsConstructor
public class IloLicenseObject implements IloUpdatableFeature, NetworkData {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();
	
	String license;
    String licenseKey;
    String licenseType;
    long lastUpdateTime;

    public static IloLicenseObject from(IPv4Address ip, String authData) throws Exception {

        String licenseJson = NetworkUtil.fetchDataFromEndpoint("https://"+ip.toString()+"/rest/v1/Managers/1/LicenseService/1",
                Optional.of(authData));
        JsonNode node = Constant.JSON_MAPPER.readTree(licenseJson);
        String license = JsonUtil.getSafeTextValueFromNode(node, "License");
        String licenseKey = JsonUtil.getSafeTextValueFromNode(node, "LicenseKey");
        String licenseType = JsonUtil.getSafeTextValueFromNode(node, "LicenseType");
        
        return new IloLicenseObject(license, licenseKey, licenseType, System.currentTimeMillis());
    }

	@Override
	public void update(IPv4Address ip, String authData, JsonNode node) {
		String licenseJson;
		try {
			licenseJson = NetworkUtil.fetchDataFromEndpoint("https://"+ip.toString()+"/rest/v1/Managers/1/LicenseService/1",
			        Optional.of(authData));
	        JsonNode licenseNode = Constant.JSON_MAPPER.readTree(licenseJson);
	        license = JsonUtil.getSafeTextValueFromNode(licenseNode, "License");
	        licenseKey = JsonUtil.getSafeTextValueFromNode(licenseNode, "LicenseKey");
	        licenseType = JsonUtil.getSafeTextValueFromNode(licenseNode, "LicenseType");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getTimeBetweenUpdates() {
		return 300;
	}

}
