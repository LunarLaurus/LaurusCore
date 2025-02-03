package net.laurus.data.dto.system.esxi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsxiCpuCoreDto {
    private int coreId;
    private int physicalCoreId;
    private int socketId;
    private int temperature;
    private int digitalReadout;
    private boolean virtualThread;
}