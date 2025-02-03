package net.laurus.data.dto.system.lmsensors;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentInfoDto {

    @JsonProperty("label")
    private String label;

    @JsonProperty("temperature")
    private Float temperature;

    @JsonProperty("max_temperature")
    private Float maxTemperature;

    @JsonProperty("critical_temperature")
    private Float criticalTemperature;
}