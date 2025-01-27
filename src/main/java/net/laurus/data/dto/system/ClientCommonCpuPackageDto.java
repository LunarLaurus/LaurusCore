package net.laurus.data.dto.system;

import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientCommonCpuPackageDto {

    // CPU Package Information    
    private String packageId;
    private float packageTemperature;
    private List<ClientCommonCpuCoreDto> coreData;

}
