package net.laurus.interfaces;

import java.io.Serializable;

import lombok.SneakyThrows;
import net.laurus.util.VersionReader;

public interface NetworkData extends Serializable {

	@SneakyThrows
	public static long getCurrentVersionHash() {
		return VersionReader.getVersion() != null
				? VersionReader.getVersion().hashCode() * Byte.MAX_VALUE
						: Integer.MAX_VALUE;
	}
	
}
