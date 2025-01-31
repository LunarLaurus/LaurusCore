package net.laurus.data.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.laurus.interfaces.NetworkData;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientCommonCpuCoreDto implements NetworkData {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

    // CPU Core Info
    private int coreId;
    private String coreName;
    private int physicalCoreId;
    private int socketId;
    private int temperature;
    private int digitalReadout;
    private boolean virtualThread;
	
}
