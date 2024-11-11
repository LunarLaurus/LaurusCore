package net.laurus.interfaces;

import java.io.Serializable;

public interface UpdateInformation<T> extends Serializable {

    public void update(T updateData);
    
}
