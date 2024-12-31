package net.laurus.data.dto.ipmi.ilo;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import net.laurus.interfaces.NetworkData;
import net.laurus.interfaces.update.ilo.IloUpdatableFeatureWithoutAuth;
import net.laurus.network.IPv4Address;

@Data
@AllArgsConstructor
public class IloOemInformation implements IloUpdatableFeatureWithoutAuth {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

	@NonNull
    final IloBios bios;
	@NonNull
	final IloHostOSObject hostOS;
	
    long lastUpdateTime;

    public static IloOemInformation from(@NonNull JsonNode oemNode) throws Exception {
        JsonNode hostOsNode = oemNode.path("HostOS");
        IloHostOSObject hostOS = IloHostOSObject.from(hostOsNode);
        JsonNode biosNode = oemNode.path("Bios");
        IloBios iloBios = IloBios.from(biosNode);
        return new IloOemInformation(iloBios, hostOS, System.currentTimeMillis());
    }

	@Override
	public void update(@NonNull JsonNode oemNode) {
        JsonNode hostOsNode = oemNode.path("HostOS");
        hostOS.update(hostOsNode);
        JsonNode biosNode = oemNode.path("Bios");
		bios.update(biosNode);
		lastUpdateTime = System.currentTimeMillis();
	}

	@Override
	public int getTimeBetweenUpdates() {
		return 60;
	}

}
