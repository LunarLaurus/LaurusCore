package net.laurus.data.serial;

import lombok.NoArgsConstructor;
import net.laurus.data.enums.ilo.HpSensorLocation;
import net.laurus.util.EnumDeserializer;

@NoArgsConstructor
public class HpFanContextDeserializer extends EnumDeserializer<HpSensorLocation> {

    @Override
    protected Class<? extends Enum<?>> getEnumType() {
        return HpSensorLocation.class;
    }

}