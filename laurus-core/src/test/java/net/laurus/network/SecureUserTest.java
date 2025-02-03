package net.laurus.network;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.laurus.util.SecretsUtils;

public class SecureUserTest {

	@Test
	void testSecureUserCreation() {
		SecureUser user = new SecureUser("UserType", "admin", "password123", false);

		assertEquals("UserType", user.getSecureUserType());
		assertEquals("admin", user.getUsername());
		assertEquals("password123", user.getPassword());
		assertFalse(user.isObfuscated());
	}

	@Test
	void testSecureUserToStringWithoutObfuscation() {
		SecureUser user = new SecureUser("UserType", "admin", "password123", false);

		String expected = "UserType [username=admin, password=password123]";
		assertEquals(expected, user.toString());
	}

	@Test
	void testSecureUserToStringWithObfuscation() {
		SecureUser user = new SecureUser("UserType", "admin", "password123", true);

		// Use SecretsUtils to calculate expected obfuscation
		String expectedUsername = SecretsUtils.obfuscateString("admin");
		String expectedPassword = SecretsUtils.obfuscateString("password123");

		String toStringResult = user.toString();

		// Validate the obfuscated username and password in the output
		assertTrue(toStringResult.contains("username=" + expectedUsername), "Obfuscated username mismatch");
		assertTrue(toStringResult.contains("password=" + expectedPassword), "Obfuscated password mismatch");
	}

	@Test
	void testStaticFromMethod() {
		SecureUser user = SecureUser.from("admin", "password123", true);

		assertEquals("admin", user.getUsername());
		assertEquals("password123", user.getPassword());
		assertTrue(user.isObfuscated());
	}
}
