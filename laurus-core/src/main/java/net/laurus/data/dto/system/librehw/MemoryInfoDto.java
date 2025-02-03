package net.laurus.data.dto.system.librehw;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonDeserialize(as = MemoryInfoDto.class)
public class MemoryInfoDto {

    @JsonAlias("Used")
    private double used;

    @JsonAlias("Available")
    private double available;

    @JsonAlias("Load")
    private double load;
}