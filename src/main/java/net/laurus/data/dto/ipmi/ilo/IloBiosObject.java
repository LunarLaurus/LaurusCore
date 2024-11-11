package net.laurus.data.dto.ipmi.ilo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@SuppressWarnings("serial")
public class IloBiosObject implements Serializable {

    @JsonProperty(value = "Date")
    String date;
    @JsonProperty(value = "Family")
    String family;
    @JsonProperty(value = "VersionString")
    String version;

}
