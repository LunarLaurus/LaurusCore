package net.laurus.data.dto.system.lmsensors;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemoryInfoDto {

    @JsonProperty("total")
    private long total;

    @JsonProperty("used")
    private long used;

    @JsonProperty("total_swap")
    private long totalSwap;

    @JsonProperty("used_swap")
    private long usedSwap;
}