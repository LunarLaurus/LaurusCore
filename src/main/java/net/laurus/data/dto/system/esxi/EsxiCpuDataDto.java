package net.laurus.data.dto.system.esxi;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsxiCpuDataDto {
    private EsxiCpuInfoDto info;
    private List<EsxiCpuCoreDto> data;
}