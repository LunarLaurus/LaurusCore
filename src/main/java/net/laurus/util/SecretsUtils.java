package net.laurus.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecretsUtils {

	/**
	 * TODO - This method exists solely to hide data in production, it's a temporary workaround.
	 * @param input
	 * @return
	 */
    public static String obfuscateString(String input) {
        try {
            // Create a SHA-256 digest
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // Hash the input string and return the byte array
            byte[] hashBytes = digest.digest(input.getBytes());
            
            // Convert the byte array into a hex string for readability
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                // Convert each byte to a 2-digit hexadecimal number
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');  // Padding for single digit hex values
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: SHA-256 algorithm not found.", e);
        }
    }
	
}
