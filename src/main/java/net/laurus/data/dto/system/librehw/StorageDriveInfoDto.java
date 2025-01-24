package net.laurus.data.dto.system.librehw;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;
import net.laurus.data.enums.system.DriveType;

@Data
public class StorageDriveInfoDto {

    @JsonAlias("Name")
    private String name;

    @JsonAlias("DriveType")
    private DriveType driveType;

    @JsonAlias("DriveFormat")
    private String driveFormat;

    @JsonAlias("TotalSize")
    private long totalSize;

    @JsonAlias("AvailableFreeSpace")
    private long availableFreeSpace;

    @JsonAlias("VolumeLabel")
    private String volumeLabel;

    // Utility method to format byte size into a human-readable format
    public static String formatBytes(long bytes) {
        if (bytes >= 1L << 30)
            return String.format("%d GB", bytes / (1L << 30));
        if (bytes >= 1L << 20)
            return String.format("%d MB", bytes / (1L << 20));
        if (bytes >= 1L << 10)
            return String.format("%d KB", bytes / (1L << 10));
        return String.format("%d Bytes", bytes);
    }
}
