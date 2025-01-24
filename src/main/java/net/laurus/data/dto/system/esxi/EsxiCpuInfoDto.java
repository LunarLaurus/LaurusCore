package net.laurus.data.dto.system.esxi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsxiCpuInfoDto {
    private int sockets;
    private int coresPerSocket;
    private int threadsPerCore;
    private int logicalProcessors;
    private int cpuUsagePercentage;
    private int tjmax;
}