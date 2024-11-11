package net.laurus.data.dto.system;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import net.laurus.data.dto.system.SystemInfoDto.MemoryDto;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level=AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemoryInfo implements UpdateInformation<MemoryDto> {

    private double used;

    private double available;

    private double load;


    @Override
    public void update(MemoryDto updateData) {
    	
    }

    public static MemoryInfo from(double used, double available, double load) {        
        return new MemoryInfo(used, available, load);
    }

    public static MemoryInfo from(MemoryDto updateData) { 
        return new MemoryInfo(updateData.getUsed(), updateData.getAvailable(), updateData.getLoad());
    }

}
