package net.laurus.data.dto.ipmi.ilo;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import net.laurus.interfaces.NetworkData;
import net.laurus.interfaces.update.ilo.IloUpdatableFeatureWithoutAuth;
import net.laurus.util.JsonUtil;

@Data
@AllArgsConstructor
public class IloHostOSObject implements IloUpdatableFeatureWithoutAuth {

	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

	String osName;
	String osSysDescription;
	String osVersion;
	int osType;

	long lastUpdateTime;

	public static IloHostOSObject from(@NonNull JsonNode hostOsNode) throws Exception {

		String osName = JsonUtil.getSafeTextValueFromNode(hostOsNode, "OsName");
		String osSysDescription = JsonUtil.getSafeTextValueFromNode(hostOsNode, "OsSysDescription");
		String osVersion = JsonUtil.getSafeTextValueFromNode(hostOsNode, "OsVersion");
		int osType = JsonUtil.getSafeIntValueFromNode(hostOsNode, "OsType");

		return new IloHostOSObject(osName, osSysDescription, osVersion, osType, System.currentTimeMillis());
	}

	@Override
	public void update(@NonNull JsonNode hostOsNode) {
		osName = JsonUtil.getSafeTextValueFromNode(hostOsNode, "OsName");
		osSysDescription = JsonUtil.getSafeTextValueFromNode(hostOsNode, "OsSysDescription");
		osVersion = JsonUtil.getSafeTextValueFromNode(hostOsNode, "OsVersion");
		osType = JsonUtil.getSafeIntValueFromNode(hostOsNode, "OsType");
		lastUpdateTime = System.currentTimeMillis();
	}

	@Override
	public int getTimeBetweenUpdates() {
		return 60;
	}

}
