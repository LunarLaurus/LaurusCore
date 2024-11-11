package net.laurus.network;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.laurus.util.SecretsUtils;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class SecureUser {

	final String secureUserType;
	final String username;
	final String password;
	final boolean obfuscated;

	@Override
	public String toString() {
		String displayUsername = obfuscated ? SecretsUtils.obfuscateString(username) : username;
		String displayPassword = obfuscated ? SecretsUtils.obfuscateString(password) : password;
		return secureUserType+" [username=" + displayUsername + ", password=" + displayPassword + "]";
	}

	public static SecureUser from(String workerUsername, String workerPassword, boolean obfuscated) {
		return SecureUser.builder().username(workerUsername).password(workerPassword).obfuscated(obfuscated).build();
	}

}