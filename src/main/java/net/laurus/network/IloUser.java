package net.laurus.network;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.laurus.util.NetworkUtil;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class IloUser extends SecureUser {

    String authData;
	
	public IloUser(String username, String password, boolean obfuscate) {
		super("IloUser", username, password, obfuscate);
		authData = NetworkUtil.generateBase64AuthString(username, password);
	}
	
	public Optional<String> getWrappedAuthData(){
		return Optional.of(this.authData);
	}

}
