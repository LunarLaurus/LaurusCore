package net.laurus.data.dto.system;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientCommonCpuCoreDto {

    // CPU Core Info
    private int coreId;
    private String coreName;
    private int physicalCoreId;
    private int socketId;
    private int temperature;
    private int digitalReadout;
    private boolean virtualThread;
	
}
