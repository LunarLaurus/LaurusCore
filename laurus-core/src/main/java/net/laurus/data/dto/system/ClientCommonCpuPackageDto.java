package net.laurus.data.dto.system;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.laurus.interfaces.NetworkData;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientCommonCpuPackageDto implements NetworkData {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

    // CPU Package Information    
    private String packageId;
    private float packageTemperature;
    private List<ClientCommonCpuCoreDto> coreData;

}
