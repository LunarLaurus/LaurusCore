package net.laurus.data.dto.ipmi.ilo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@SuppressWarnings("serial")
public class IloBios implements Serializable {

    @JsonProperty(value = "Current")
    IloBiosObject current;
    @JsonProperty(value = "Backup")
    IloBiosObject backup;
    @JsonProperty(value = "Bootblock")
    IloBiosObject bootBlock;
    @JsonProperty(value = "UefiClass")
    int uefiClass;

}
