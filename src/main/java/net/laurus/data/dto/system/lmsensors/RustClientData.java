package net.laurus.data.dto.system.lmsensors;

import static net.laurus.Constant.JSON_MAPPER;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RustClientData {

	public static RustClientData from(String jsonData) throws IOException {
		return JSON_MAPPER.readValue(jsonData, RustClientData.class);
	}

    @JsonProperty("uptime")
    private SystemUptimeDto uptime;

    @JsonProperty("cpu_info")
    private CpuInfoDto cpuInfo;

    @JsonProperty("cpu_packages")
    private List<CpuPackageDataDto> cpuData;

    @JsonProperty("memory_info")
    private MemoryInfoDto memoryInfo;

    @JsonProperty("disks")
    private List<DiskInfoDto> disks;

    @JsonProperty("network_interfaces")
    private List<NetworkInfoDto> networkInterfaces;

    @JsonProperty("components")
    private List<ComponentInfoDto> components;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CpuInfoDto {

        @JsonProperty("usage_per_core")
        private List<Float> usagePerCore;

        @JsonProperty("core_count")
        private int coreCount;

        @JsonProperty("cpu_arch")
        private String cpuArch;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CpuPackageDataDto {

        @JsonProperty("package_id")
        private String packageId;

        @JsonProperty("adapter_name")
        private String adapterName;

        @JsonProperty("package_temperature")
        private float packageTemperature;

        @JsonProperty("high_threshold")
        private float highTemperatureThreshold;

        @JsonProperty("critical_threshold")
        private float criticalTemperatureThreshold;

        @JsonProperty("cores")
        private List<CpuCoreDataDto> coreData;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class CpuCoreDataDto {

            @JsonProperty("core_name")
            private String coreName;

            @JsonProperty("temperature")
            private float temperature;

            @JsonProperty("high_threshold")
            private float highTemperatureThreshold;

            @JsonProperty("critical_threshold")
            private float criticalTemperatureThreshold;
        }
    }
}

