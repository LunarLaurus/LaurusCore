package net.laurus.data.serial;

import lombok.NoArgsConstructor;
import net.laurus.data.enums.ilo.Health;
import net.laurus.util.EnumDeserializer;

@NoArgsConstructor
public class HealthDeserializer extends EnumDeserializer<Health> {

    @Override
    protected Class<? extends Enum<?>> getEnumType() {
        return Health.class;
    }

}