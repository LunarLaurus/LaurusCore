package net.laurus.data.dto.ipmi.ilo;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.laurus.interfaces.IloUpdatableFeature;
import net.laurus.interfaces.NetworkData;
import net.laurus.network.IPv4Address;

@Data
@AllArgsConstructor
public class IloBios implements IloUpdatableFeature, NetworkData {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();
	
    final IloBiosObject current;
    final IloBiosObject backup;
    final IloBiosObject bootBlock;
    int uefiClass;
    long lastUpdateTime;

	public static IloBios from(JsonNode biosNode) {
		JsonNode nodeBackup = biosNode.path("Backup");
		JsonNode nodeBootblock = biosNode.path("Bootblock");
		JsonNode nodeCurrent = biosNode.path("Current");
		IloBiosObject biosBackup = IloBiosObject.from(nodeBackup);
		IloBiosObject biosBootblock = IloBiosObject.from(nodeBootblock);
		IloBiosObject biosCurrent = IloBiosObject.from(nodeCurrent);
		int uefi = biosNode.path("UefiClass").asInt();		
		return new IloBios(biosCurrent, biosBackup, biosBootblock, uefi, System.currentTimeMillis());
	}
    
	@Override
	public void update(IPv4Address ip, String authData, JsonNode biosNode) {
		JsonNode nodeBackup = biosNode.path("Backup");
		JsonNode nodeBootblock = biosNode.path("Bootblock");
		JsonNode nodeCurrent = biosNode.path("Current");
		backup.update(ip, authData, nodeBackup);
		bootBlock.update(ip, authData, nodeBootblock);
		current.update(ip, authData, nodeCurrent);
		uefiClass = biosNode.path("UefiClass").asInt();		
	}
	
	@Override
	public int getTimeBetweenUpdates() {
		return 300;
	}

}
