package net.laurus.data.dto.ipmi.ilo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.laurus.interfaces.NetworkData;

@Data
@NoArgsConstructor
public class IloBios implements NetworkData {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();
	
    @JsonProperty(value = "Current")
    IloBiosObject current;
    @JsonProperty(value = "Backup")
    IloBiosObject backup;
    @JsonProperty(value = "Bootblock")
    IloBiosObject bootBlock;
    @JsonProperty(value = "UefiClass")
    int uefiClass;

}
