package net.laurus.data.dto.system.lmsensors;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiskInfoDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("total_space")
    private long totalSpace;

    @JsonProperty("available_space")
    private long availableSpace;

    @JsonProperty("read_bytes")
    private long readBytes;

    @JsonProperty("written_bytes")
    private long writtenBytes;
}