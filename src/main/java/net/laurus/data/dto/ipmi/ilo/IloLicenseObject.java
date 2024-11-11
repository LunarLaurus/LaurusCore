package net.laurus.data.dto.ipmi.ilo;

import java.io.Serializable;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Value;
import net.laurus.Constant;
import net.laurus.network.IPv4Address;
import net.laurus.util.JsonUtil;
import net.laurus.util.NetworkUtil;

@Value
@SuppressWarnings("serial")
public class IloLicenseObject implements Serializable {

    String license;
    String licenseKey;
    String licenseType;

    public static IloLicenseObject from(IPv4Address ip, String authData) throws Exception {

        String licenseJson = NetworkUtil.fetchDataFromEndpoint("https://"+ip.toString()+"/rest/v1/Managers/1/LicenseService/1",
                Optional.of(authData));
        JsonNode node = Constant.JSON_MAPPER.readTree(licenseJson);
        String license = JsonUtil.getSafeTextValueFromNode(node, "License");
        String licenseKey = JsonUtil.getSafeTextValueFromNode(node, "LicenseKey");
        String licenseType = JsonUtil.getSafeTextValueFromNode(node, "LicenseType");
        
        return new IloLicenseObject(license, licenseKey, licenseType);
    }

}
