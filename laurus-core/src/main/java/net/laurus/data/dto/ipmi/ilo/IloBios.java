package net.laurus.data.dto.ipmi.ilo;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import net.laurus.interfaces.NetworkData;
import net.laurus.interfaces.update.ilo.IloUpdatableFeatureWithoutAuth;

@Data
@AllArgsConstructor
public class IloBios implements IloUpdatableFeatureWithoutAuth {

	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

	final IloBiosObject current;
	final IloBiosObject backup;
	final IloBiosObject bootBlock;
	int uefiClass;
	long lastUpdateTime;

	public static IloBios from(@NonNull JsonNode biosNode) {
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
	public void update(@NonNull JsonNode biosNode) {
		JsonNode nodeBackup = biosNode.path("Backup");
		JsonNode nodeBootblock = biosNode.path("Bootblock");
		JsonNode nodeCurrent = biosNode.path("Current");
		backup.update(nodeBackup);
		bootBlock.update(nodeBootblock);
		current.update(nodeCurrent);
		uefiClass = biosNode.path("UefiClass").asInt();
		lastUpdateTime = System.currentTimeMillis();
	}

	@Override
	public int getTimeBetweenUpdates() {
		return 300;
	}

}
