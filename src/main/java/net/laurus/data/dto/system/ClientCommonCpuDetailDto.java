package net.laurus.data.dto.system;

import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientCommonCpuDetailDto {

    // CPU General Info
    private String cpuArch;
    private int sockets;
    private int coresPerSocket;
    private int threadsPerCore;
    private int logicalProcessors;
	private List<ClientCommonCpuPackageDto> packageInfo;
	
}
