package net.laurus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.laurus.network.IloUser;

@Getter
@AllArgsConstructor
public abstract class AbstractIloAuthService {

	private final Map<String, IloUser> iloAuthDataMap = new ConcurrentHashMap<>();
	
	protected abstract boolean isObfuscated();

	protected abstract String getConfigSuppliedIloUsername();
	
	protected abstract String getConfigSuppliedIloPassword();
	
	protected abstract IloUser getDefaultIloUser();
	
	protected abstract void postConstruct();
	
	public final void addIloUser(IloUser user) {
		iloAuthDataMap.put(user.getUsername(), user);
	}
	
}
