package net.laurus.interfaces.update.ilo;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.NonNull;

public interface IloUpdatableFeatureWithoutAuth extends IloUpdatableFeature {

    public void update(@NonNull JsonNode updateDataNode);
    
}
