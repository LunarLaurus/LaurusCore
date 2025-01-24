package net.laurus.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SecretsUtilsTest {

    // Tests for obfuscateString
    @Test
    void testObfuscateString_NullInput() {
        assertThrows(NullPointerException.class, () -> SecretsUtils.obfuscateString(null));
    }

    @Test
    void testObfuscateString_ShortInput() {
        assertEquals("*", SecretsUtils.obfuscateString("a"), "Single-character input should be fully masked");
        assertEquals("**", SecretsUtils.obfuscateString("ab"), "Two-character input should be fully masked");
    }

    @Test
    void testObfuscateString_NormalInput() {
        assertEquals("a***n", SecretsUtils.obfuscateString("admin"), "String should be partially obfuscated");
        assertEquals("1*******9", SecretsUtils.obfuscateString("123456789"), "String should be partially obfuscated");
    }

    // Tests for fullyObfuscateString
    @Test
    void testFullyObfuscateString_NormalInput() {
        assertEquals("*****", SecretsUtils.fullyObfuscateString("admin"), "String should be fully obfuscated");
        assertEquals("*********", SecretsUtils.fullyObfuscateString("123456789"), "String should be fully obfuscated");
    }

    @Test
    void testFullyObfuscateString_ShortInput() {
        assertEquals("*", SecretsUtils.fullyObfuscateString("a"), "Single-character input should be fully masked");
        assertEquals("**", SecretsUtils.fullyObfuscateString("ab"), "Two-character input should be fully masked");
    }

    @Test
    void testFullyObfuscateString_NullInput() {
        assertThrows(NullPointerException.class, () -> SecretsUtils.fullyObfuscateString(null));
    }

    // Tests for obfuscateEmail
    @Test
    void testObfuscateEmail_InvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> SecretsUtils.obfuscateEmail("invalidEmail"));
    }

    @Test
    void testObfuscateEmail_NullInput() {
        assertThrows(NullPointerException.class, () -> SecretsUtils.obfuscateEmail(null));
    }

    @Test
    void testObfuscateEmail_ValidInput() {
        assertEquals("u***@example.com", SecretsUtils.obfuscateEmail("user@example.com"), "Email should be partially obfuscated");
        assertEquals("*@example.com", SecretsUtils.obfuscateEmail("u@example.com"), "Short email local part should be fully obfuscated");
    }

    // Tests for customObfuscate
    @Test
    void testCustomObfuscate_NormalInput() {
        assertEquals("12##56", SecretsUtils.customObfuscate("123456", 2, 2, '#'), "Custom obfuscation should apply correctly");
    }

    @Test
    void testCustomObfuscate_NoMaskingNeeded() {
        assertEquals("hello", SecretsUtils.customObfuscate("hello", 5, 0, '*'), "No masking needed if visible prefix covers the entire input");
    }

    @Test
    void testCustomObfuscate_InvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> SecretsUtils.customObfuscate("admin", -1, 1, '*'));
        assertThrows(IllegalArgumentException.class, () -> SecretsUtils.customObfuscate("admin", 5, -5, '*'));
    }
}
