package net.laurus.data.dto.system.lmsensors;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemUptimeDto {

    @JsonProperty("days")
    private long days;

    @JsonProperty("hours")
    private long hours;

    @JsonProperty("minutes")
    private long minutes;

    @JsonProperty("seconds")
    private long seconds;

    @JsonProperty("total_seconds")
    private long totalSeconds;
}