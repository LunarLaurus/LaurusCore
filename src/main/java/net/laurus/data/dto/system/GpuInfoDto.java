package net.laurus.data.dto.system;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonDeserialize(as = GpuInfoDto.class)
public class GpuInfoDto {

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
