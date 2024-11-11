package net.laurus.data.dto.system;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import net.laurus.data.dto.system.SystemInfoDto.CpuInfoDto;
import net.laurus.data.dto.system.SystemInfoDto.CpuInfoDto.CpuDataDto;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class SystemInfo implements UpdateInformation<SystemInfoDto> {

    CpuInfo[] cpus;
    int cpuCount;
    int cpuCoreCount;

    private SystemInfo(CpuInfo[] cpus) {
        this.cpus = cpus;
        this.cpuCount = cpus.length;
        this.cpuCoreCount = cpus[0].getCoreCount();
    }
    
    public static SystemInfo from(SystemInfoDto system) {        
       CpuInfoDto cpus = system.getCpuInfoDto();
        
        CpuInfo[] cpuInfo = new CpuInfo[cpus.getCpuCount()];
        int cpuKey = 0;
        for (CpuDataDto dto : cpus.getCpuDataBySocket().values()) {
            cpuInfo[cpuKey] = CpuInfo.from(cpuKey++, dto);
        }        
        return new SystemInfo(cpuInfo);
    }

    @Override
    public void update(SystemInfoDto systemInfoDTO) {
        //Update CPU data        
        for (int cpuId=0; cpuId < systemInfoDTO.getCpuInfoDto().getCpuCount(); cpuId++) {
            cpus[cpuId].update(systemInfoDTO.getCpuInfoDto().getCpuDataBySocket(cpuId));
        }
    }
   
    
}
