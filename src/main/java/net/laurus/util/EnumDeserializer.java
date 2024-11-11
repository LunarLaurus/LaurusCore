package net.laurus.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public abstract class EnumDeserializer<T extends Enum<?>> extends JsonDeserializer<Enum<?>> {

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return (T) Enum.valueOf(getEnumType(), p.getText().toUpperCase());
    }
    
    @SuppressWarnings("rawtypes")
    protected abstract Class<? extends Enum> getEnumType();
}
