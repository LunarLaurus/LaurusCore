package net.laurus.data.dto.system.lmsensors;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessInfo {

    @JsonProperty("name")
    private String name;

    @JsonProperty("pid")
    private int pid;

    @JsonProperty("memory")
    private long memory;
}