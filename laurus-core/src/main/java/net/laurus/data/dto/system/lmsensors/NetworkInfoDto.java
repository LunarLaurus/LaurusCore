package net.laurus.data.dto.system.lmsensors;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NetworkInfoDto {

    @JsonProperty("interface_name")
    private String interfaceName;

    @JsonProperty("received")
    private long received;

    @JsonProperty("transmitted")
    private long transmitted;

    @JsonProperty("mtu")
    private long mtu;
}