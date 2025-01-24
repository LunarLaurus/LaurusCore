package net.laurus.data.dto.system.esxi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsxiSystemDto {
    private String hostname;
    private String vmwareVersion;
    private String kernelVersion;
    private String biosVersion;
    private String hostModel;
    private String managementIp;
    private long uptimeSeconds;
    private long totalMemoryBytes;
}