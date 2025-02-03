package net.laurus.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ReflectionUtilTest {

    @Test
    void testFindAllClassesInPackage() {
        String[] result = ReflectionUtil.findAllClassesInPackage("net.laurus.data");
        assertTrue(result.length > 0, "Should return classes in the package.");
    }

    @Test
    void testFindAllClassesInPackage_InvalidPackage() {
        String[] result = ReflectionUtil.findAllClassesInPackage("non.existent.package");
        assertEquals(0, result.length, "Should return an empty array for a non-existent package.");
    }
}
