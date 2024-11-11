package net.laurus.data.serial;

import lombok.NoArgsConstructor;
import net.laurus.data.enums.ilo.Unit;
import net.laurus.util.EnumDeserializer;

@NoArgsConstructor
public class UnitDeserializer extends EnumDeserializer<Unit> {

    @Override
    protected Class<? extends Enum<?>> getEnumType() {
        return Unit.class;
    }

}