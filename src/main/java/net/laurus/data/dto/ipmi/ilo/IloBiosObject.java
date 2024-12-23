package net.laurus.data.dto.ipmi.ilo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.laurus.interfaces.NetworkData;

@Data
@NoArgsConstructor
public class IloBiosObject implements NetworkData {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

    @JsonProperty(value = "Date")
    String date;
    @JsonProperty(value = "Family")
    String family;
    @JsonProperty(value = "VersionString")
    String version;

}
