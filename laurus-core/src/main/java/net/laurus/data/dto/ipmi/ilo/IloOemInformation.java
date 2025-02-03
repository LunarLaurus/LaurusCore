package net.laurus.data.dto.ipmi.ilo;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import net.laurus.data.enums.ilo.IloPostState;
import net.laurus.data.enums.ilo.IloPowerAutoOn;
import net.laurus.data.enums.ilo.IloPowerOnDelay;
import net.laurus.data.enums.ilo.IloPowerRegulatorMode;
import net.laurus.data.enums.ilo.IloVirtualProfile;
import net.laurus.interfaces.NetworkData;
import net.laurus.interfaces.update.ilo.IloUpdatableFeatureWithoutAuth;

@Data
@AllArgsConstructor
@Builder
public class IloOemInformation implements IloUpdatableFeatureWithoutAuth {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

	@NonNull
    final IloBios bios;
	@NonNull
	final IloHostOSObject hostOS;

	@NonNull
	IloPowerRegulatorMode powerRegulatorMode;
	@NonNull
	final List<IloPowerRegulatorMode> powerRegulatorModesSupported;
	@NonNull
	final IloTpmObject trustedModules;

	int intelligentProvisioningIndex;
	@NonNull
	String intelligentProvisioningLocation;
	@NonNull
	String intelligentProvisioningVersion;
	@NonNull
	IloPostState postState;
	@NonNull
	String powerAllocationLimit;
	@NonNull
	IloPowerAutoOn powerAutoOn;
	@NonNull
	IloPowerOnDelay powerOnDelay;
	@NonNull
	IloVirtualProfile virtualProfile;
	
    long lastUpdateTime;
    

    public static IloOemInformation from(@NonNull JsonNode oemNode) throws Exception {
        JsonNode hostOsNode = oemNode.path("HostOS");
        IloHostOSObject hostOS = IloHostOSObject.from(hostOsNode);
        JsonNode biosNode = oemNode.path("Bios");
        IloBios iloBios = IloBios.from(biosNode);

		int intelligentProvisioningIndex = oemNode.path("IntelligentProvisioningIndex").asInt(-1);
		String intelligentProvisioningLocation = oemNode.path("IntelligentProvisioningLocation").asText("N/A");
		String intelligentProvisioningVersion = oemNode.path("IntelligentProvisioningVersion").asText("N/A");
		IloPostState postState = IloPostState.get(oemNode.path("PostState").asText("N/A"));
		String powerAllocationLimit = oemNode.path("PowerAllocationLimit").asText("N/A");
		IloPowerAutoOn powerAutoOn = IloPowerAutoOn.get(oemNode.path("PowerAutoOn").asText("N/A"));
		IloPowerOnDelay powerOnDelay = IloPowerOnDelay.get(oemNode.path("PowerOnDelay").asText("N/A"));
		IloVirtualProfile virtualProfile = IloVirtualProfile.get(oemNode.path("VirtualProfile").asText("N/A"));
		
		IloPowerRegulatorMode powerMode = IloPowerRegulatorMode.from(oemNode.path("PowerRegulatorMode"));
		List<IloPowerRegulatorMode> powerRegulatorModesSupported = IloPowerRegulatorMode.getSupported(oemNode.path("PowerRegulatorModesSupported"));
		IloTpmObject tpm = IloTpmObject.from(oemNode.path("TrustedModules"));
		
        return new IloOemInformation(iloBios, hostOS, powerMode, 
        		powerRegulatorModesSupported, tpm, intelligentProvisioningIndex, 
        		intelligentProvisioningLocation, intelligentProvisioningVersion, postState, 
        		powerAllocationLimit, powerAutoOn, powerOnDelay,
        		virtualProfile, System.currentTimeMillis());
    }

	@Override
	public void update(@NonNull JsonNode oemNode) {
        JsonNode hostOsNode = oemNode.path("HostOS");
        hostOS.update(hostOsNode);
        JsonNode biosNode = oemNode.path("Bios");
		bios.update(biosNode);
		intelligentProvisioningIndex = oemNode.path("IntelligentProvisioningIndex").asInt(-1);
		intelligentProvisioningLocation = oemNode.path("IntelligentProvisioningLocation").asText("N/A");
		intelligentProvisioningVersion = oemNode.path("IntelligentProvisioningVersion").asText("N/A");
		postState = IloPostState.get(oemNode.path("PostState").asText("N/A"));
		powerAllocationLimit = oemNode.path("PowerAllocationLimit").asText("N/A");
		powerAutoOn = IloPowerAutoOn.get(oemNode.path("PowerAutoOn").asText("N/A"));
		powerOnDelay = IloPowerOnDelay.get(oemNode.path("PowerOnDelay").asText("N/A"));
		virtualProfile = IloVirtualProfile.get(oemNode.path("VirtualProfile").asText("N/A"));		
		powerRegulatorMode = IloPowerRegulatorMode.from(oemNode.path("PowerRegulatorMode"));	
		lastUpdateTime = System.currentTimeMillis();
	}

	@Override
	public int getTimeBetweenUpdates() {
		return 60;
	}

}
