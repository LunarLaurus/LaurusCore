package net.laurus.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SecretsUtils {

    /**
     * Partially obfuscates the input string by masking the middle characters with asterisks.
     * - Example: "admin" -> "a***n", "123456789" -> "1*******9"
     * - Very short inputs (≤2 chars) will be fully masked.
     *
     * @param input The string to obfuscate.
     * @return The partially obfuscated string.
     */
    public static String obfuscateString(@NonNull String input) {
        if (input.length() <= 2) {
            return fullyObfuscateString(input); // Fully obfuscate short strings
        }
        return customObfuscate(input, 1, 1, '*');
    }

    /**
     * Fully masks the input string with asterisks.
     * - Example: "admin" -> "*****", "123456" -> "******"
     *
     * @param input The string to fully obfuscate.
     * @return The fully masked string.
     */
    public static String fullyObfuscateString(@NonNull String input) {
        return "*".repeat(input.length());
    }

    /**
     * Obfuscates an email address by partially masking the local part.
     * - Example: "user@example.com" -> "u***@example.com"
     *
     * @param email The email address to obfuscate.
     * @return The obfuscated email address.
     */
    public static String obfuscateEmail(@NonNull String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address format.");
        }

        String[] parts = email.split("@", 2);
        String localPart = parts[0];
        String domain = parts[1];

        if (localPart.length() <= 2) {
            localPart = fullyObfuscateString(localPart); // Fully obfuscate very short local parts
        } else {
            localPart = customObfuscate(localPart, 1, 0, '*');
        }

        return localPart + "@" + domain;
    }


    /**
     * Utility method to obfuscate any string with a custom masking character and pattern.
     * - Example: ("hello", 2, 2, '#') -> "he###lo"
     *
     * @param input         The string to obfuscate.
     * @param visiblePrefix The number of characters to keep visible at the start.
     * @param visibleSuffix The number of characters to keep visible at the end.
     * @param maskChar      The character used for masking.
     * @return The custom-obfuscated string.
     */
    public static String customObfuscate(@NonNull String input, int visiblePrefix, int visibleSuffix, char maskChar) {
        validateObfuscationParameters(input, visiblePrefix, visibleSuffix);

        int length = input.length();
        int maskLength = Math.max(0, length - (visiblePrefix + visibleSuffix));

        if (maskLength <= 0) {
            return input;
        }

        return input.substring(0, visiblePrefix) +
               String.valueOf(maskChar).repeat(maskLength) +
               input.substring(length - visibleSuffix);
    }

    /**
     * Validates the parameters for obfuscation to ensure correctness.
     * Throws an exception if the parameters are invalid.
     *
     * @param input         The input string.
     * @param visiblePrefix The number of characters to keep visible at the start.
     * @param visibleSuffix The number of characters to keep visible at the end.
     */
    private static void validateObfuscationParameters(String input, int visiblePrefix, int visibleSuffix) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty.");
        }

        if (visiblePrefix < 0 || visibleSuffix < 0) {
            throw new IllegalArgumentException("Visible prefix and suffix cannot be negative.");
        }
    }
}
