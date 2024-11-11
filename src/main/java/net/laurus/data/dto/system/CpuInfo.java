package net.laurus.data.dto.system;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import net.laurus.data.dto.system.SystemInfoDto.CpuInfoDto.CpuDataDto;
import net.laurus.util.CpuTempMath;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level=AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CpuInfo implements UpdateInformation<CpuDataDto> {

    private int cpuId;
    private int coreCount;
    private Map<Integer, Float> coreTemperatures;
    private float cpuPackageTemperature;
    private float coreMaxTemperature;
    private float coreAverageTemperature;

    private CpuInfo(int cpuId, int coreCount, Map<Integer, Float> coreTemperatures) {
        this.cpuId = cpuId;
        this.coreCount = coreCount;
        this.coreTemperatures = coreTemperatures;
    }

    @Override
    public void update(CpuDataDto updateData) {
        this.coreTemperatures.clear();        
        this.coreTemperatures.putAll(CpuTempMath.getCoreTemperatures(cpuId, updateData.getTemperatures()));
        this.setCpuPackageTemperature(CpuTempMath.getCpuPackageTemperature(updateData.getTemperatures()));
        this.setCoreAverageTemperature(CpuTempMath.getCoreAverageTemperature(updateData.getTemperatures()));
        this.setCoreMaxTemperature(CpuTempMath.getCoreMaxTemperature(updateData.getTemperatures()));
    }

    public static CpuInfo from(int cpuId, int coreCount) {        
        return new CpuInfo(cpuId, coreCount, new HashMap<>());
    }

    public static CpuInfo from(int cpuId, CpuDataDto dto) { 
        return new CpuInfo(cpuId, CpuTempMath.countCoresSingleCpu(dto.getTemperatures()), new HashMap<>());
    }

}