package net.laurus.data.dto.system.lmsensors;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemInfoDto {

    @JsonProperty("hostname")
    private String hostname;

    @JsonProperty("uptime")
    private SystemUptimeDto uptime;

    @JsonProperty("management_ip")
    private String managementIp;
    
}