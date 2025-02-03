package net.laurus.util;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import lombok.experimental.UtilityClass;

@UtilityClass
public class VersionReader {
	
	private static String VERSION;

    public static String getVersion() throws IOException {
    	if (VERSION != null) {
    		return VERSION;
    	}
        try {
            // Path to the jar file, assuming you are working with the current classpath
            String jarPath = VersionReader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            File jarFilePath = new File(jarPath);
            
            // If it's not a JAR, return null
            if (!jarFilePath.exists() || !jarFilePath.getName().endsWith(".jar")) {
                //System.err.println("Unable to find jar");
            	VERSION = null;
            	return VERSION;
            }
            
            // Open the JAR file and try to read the version from the manifest
            try (JarFile jarFile = new JarFile(jarFilePath)) {
                Manifest manifest = jarFile.getManifest();
                if (manifest == null) {
                    System.err.println("Manifest missing");
                	VERSION = null;
                	return VERSION;
                }
                Attributes manifestAttributes = manifest.getMainAttributes();
                return manifestAttributes.getValue("Version");
            }
        } catch (IOException e) {
            // Log the error and fall back to another method
            System.err.println("Failed to read version from JAR: " + e.getMessage());
        	VERSION = null;
        	return VERSION;
        }
    }
}
