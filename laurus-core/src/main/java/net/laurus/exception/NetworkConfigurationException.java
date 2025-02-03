package net.laurus.exception;

public class NetworkConfigurationException extends Exception {

	private static final long serialVersionUID = 7073542656971267461L;

	public NetworkConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public NetworkConfigurationException(String message) {
        super(message);
    }
}
