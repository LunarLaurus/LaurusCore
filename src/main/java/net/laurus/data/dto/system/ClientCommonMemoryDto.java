package net.laurus.data.dto.system;

import lombok.Value;
import net.laurus.interfaces.NetworkData;

@Value
public class ClientCommonMemoryDto implements NetworkData {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

    private long total;
    private long used;
}
