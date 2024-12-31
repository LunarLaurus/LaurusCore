package net.laurus.data.serial;

import lombok.NoArgsConstructor;
import net.laurus.data.enums.ilo.IloFanUnit;
import net.laurus.util.EnumDeserializer;

@NoArgsConstructor
public class IloFanUnitDeserializer extends EnumDeserializer<IloFanUnit> {

    @Override
    protected Class<? extends Enum<?>> getEnumType() {
        return IloFanUnit.class;
    }

}