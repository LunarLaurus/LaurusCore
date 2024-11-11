package net.laurus.data.dto.system;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PsuInfoDto {

    @JsonAlias("Name")
    private String name;

    @JsonAlias("Power")
    private Map<String, Float> power = new HashMap<>();

    @JsonAlias("Voltage")
    private Map<String, Float> voltage = new HashMap<>();
}

