package net.laurus.interfaces;

public interface UpdateInformation<T> extends NetworkData {
	
    public void update(T updateData);
    
}
