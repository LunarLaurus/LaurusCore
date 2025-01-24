package net.laurus.data.dto.system.librehw;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import net.laurus.data.enums.system.DriveType;
import net.laurus.interfaces.NetworkData;
import net.laurus.interfaces.UpdateInformation;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StorageDriveInfo implements UpdateInformation<StorageDriveInfoDto> {

	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

	private String name;
	private DriveType driveType;
	private String driveFormat;
	private long totalSize;
	private long availableFreeSpace;
	private String volumeLabel;

	@Override
	public void update(StorageDriveInfoDto updateData) {
		availableFreeSpace = updateData.getAvailableFreeSpace();
		driveFormat = updateData.getDriveFormat();
		volumeLabel = updateData.getVolumeLabel();
	}

	public static StorageDriveInfo from(String name, DriveType driveType, String driveFormat, long totalSize,
			long availableFreeSpace, String volumeLabel) {
		return new StorageDriveInfo(name, driveType, driveFormat, totalSize, availableFreeSpace, volumeLabel);
	}

	public static StorageDriveInfo from(StorageDriveInfoDto updateData) {
		return new StorageDriveInfo(updateData.getName(), updateData.getDriveType(), updateData.getDriveFormat(),
				updateData.getTotalSize(), updateData.getAvailableFreeSpace(), updateData.getVolumeLabel());
	}

}
