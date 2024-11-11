package net.laurus.data.dto.system;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonDeserialize(as = SystemInfoDto.class)
public class SystemInfoDto {

    @JsonAlias("SystemArchitecture")
    private int systemArchitecture;

    @JsonAlias("SystemPlatform")
    private String systemPlatform;

    @JsonAlias("SystemHostName")
    private String systemHostName;

    @JsonAlias("SystemModelName")
    private String systemModelName;

    @JsonAlias("System64Bit")
    private boolean system64Bit;

    @JsonAlias("DotNetProcess64Bit")
    private boolean dotNetProcess64Bit;

    @JsonAlias("SystemUptime")
    private long systemUptime;

    @JsonAlias("SystemStorageInfo")
    private List<StorageDriveInfoDto> systemStorageInfo;

    @JsonAlias("CpuInfo")
    private CpuInfoDto cpuInfoDto;

    @JsonAlias("Gpus")
    private List<GpuInfoDto> gpuDtos;

    @JsonAlias("Memory")
    private MemoryInfoDto memoryDto;

    @JsonAlias("PowerSupplies")
    private List<PsuInfoDto> powerSupplies;

    @JsonAlias("IpmiAddress")
    private String ipmiAddress;

}
