package net.laurus.data.dto.system;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.List;

@Data
@NoArgsConstructor
@JsonDeserialize(as = SystemInfoDto.class)
public class SystemInfoDto {

    @JsonAlias("ClientName")
    private String clientName;

    @JsonAlias("ModelName")
    private String modelName;

    @JsonAlias("CpuInfo")
    private CpuInfoDto cpuInfoDto;

    @JsonAlias("Gpus")
    private List<GpuDto> gpuDtos;

    @JsonAlias("Memory")
    private MemoryDto memoryDto;

    @JsonAlias("IloAddress")
    private String iloAddress;

    @Data
    @NoArgsConstructor
    @JsonDeserialize(as = CpuInfoDto.class)
    public static class CpuInfoDto {

        @JsonAlias("CpuDataBySocket")
        private Map<Integer, CpuDataDto> cpuDataBySocket;

        @JsonAlias("CpuCount")
        private int cpuCount;

        @JsonAlias("CpuCoreCount")
        private int cpuCoreCount;
        
        public CpuDataDto getCpuDataBySocket(int socket) {
            return cpuDataBySocket.get(socket);
        }

        @Data
        @NoArgsConstructor
        @JsonDeserialize(as = CpuDataDto.class)
        public static class CpuDataDto {

            @JsonAlias("Temperatures")
            private Map<String, Float> temperatures;

            @JsonAlias("Load")
            private Map<String, Float> load;

            @JsonAlias("ClockSpeeds")
            private Map<String, Float> clockSpeeds;
        }
    }

    @Data
    @NoArgsConstructor
    @JsonDeserialize(as = GpuDto.class)
    public static class GpuDto {

        @JsonAlias("Name")
        private String name;

        @JsonAlias("Temperatures")
        private Map<String, Float> temperatures;

        @JsonAlias("Load")
        private Map<String, Float> load;

        @JsonAlias("ClockSpeeds")
        private Map<String, Float> clockSpeeds;

        @JsonAlias("MemoryUsage")
        private Map<String, Float> memoryUsage;
    }

    @Data
    @NoArgsConstructor
    @JsonDeserialize(as = MemoryDto.class)
    public static class MemoryDto {

        @JsonAlias("Used")
        private double used;

        @JsonAlias("Available")
        private double available;

        @JsonAlias("Load")
        private double load;
    }
}
