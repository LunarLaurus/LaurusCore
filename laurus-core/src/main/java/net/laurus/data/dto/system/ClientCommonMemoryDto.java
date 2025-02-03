package net.laurus.data.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.laurus.interfaces.NetworkData;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientCommonMemoryDto implements NetworkData {
	
	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

    private long total;
    private long used;
}
