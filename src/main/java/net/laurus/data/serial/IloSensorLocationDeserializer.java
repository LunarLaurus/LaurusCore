package net.laurus.data.serial;

import lombok.NoArgsConstructor;
import net.laurus.data.enums.ilo.IloSensorLocation;
import net.laurus.util.EnumDeserializer;

@NoArgsConstructor
public class IloSensorLocationDeserializer extends EnumDeserializer<IloSensorLocation> {

    @Override
    protected Class<? extends Enum<?>> getEnumType() {
        return IloSensorLocation.class;
    }

}