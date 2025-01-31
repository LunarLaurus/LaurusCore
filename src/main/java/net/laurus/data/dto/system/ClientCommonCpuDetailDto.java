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
public class ClientCommonCpuDetailDto implements NetworkData {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

    // CPU General Info
    private String cpuArch;
    private int sockets;
    private int coresPerSocket;
    private int threadsPerCore;
    private int logicalProcessors;
	private List<ClientCommonCpuPackageDto> packageInfo;
	
}
