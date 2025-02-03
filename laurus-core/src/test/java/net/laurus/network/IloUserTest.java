package net.laurus.network;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IloUserTest {

    @Test
    void testIloUserCreation() {
        IloUser iloUser = new IloUser("admin", "password123", true);

        assertEquals("IloUser", iloUser.getSecureUserType());
        assertEquals("admin", iloUser.getUsername());
        assertEquals("password123", iloUser.getPassword());
        assertTrue(iloUser.isObfuscated());
        assertNotNull(iloUser.getWrappedAuthData());
    }

    @Test
    void testWrappedAuthData() {
        IloUser iloUser = new IloUser("admin", "password123", true);

        assertTrue(iloUser.getWrappedAuthData().isPresent());
        assertNotNull(iloUser.getWrappedAuthData().get());
    }
}
