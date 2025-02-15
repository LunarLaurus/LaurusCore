package net.laurus.util;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;
import net.laurus.data.Bitmap;
import net.laurus.network.IPv4Address;
import net.laurus.network.Subnet;
import net.laurus.network.SubnetMask;

@Log
@UtilityClass
public class NetworkUtil {

	private static boolean disabledSslVerification = false;

	public static IPv4Address getLocalIpAddress() {
		IPv4Address address;
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			address = new IPv4Address(localhost.getHostAddress());
			log.fine("Local IP Address: " + address);
		} catch (UnknownHostException e) {
			log.warning("Error getting local IP address: " + e.getMessage());
			address = new IPv4Address(127, 0, 0, 1);
		}
		return address;
	}

	public static String getHostname() {
		InetAddress localHost;
		try {
			localHost = InetAddress.getLocalHost();
			String name = localHost.getHostName();
			log.fine("Local Host Name: " + name);
			return name;
		} catch (UnknownHostException e) {
			log.warning("Failed to get hostname.");
		}
		return "bad-host-name";
	}

	public static boolean isZeroIPAddress(String ipAddress) {
		if (ipAddress == null) {
			return true;
		}
		return ipAddress.trim().equals("0.0.0.0");
	}

	public static void disableSslVerification() throws Exception {
		if (disabledSslVerification) {
			return;
		}
		// Create a trust manager that accepts all certificates
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		// Install the custom trust manager
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Disable hostname verification
		HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
		disabledSslVerification = true;
	}

	public static String fetchDataFromEndpoint(String endpointUrl) throws Exception {
		return fetchDataFromEndpoint(endpointUrl, new HashMap<>());
	}

	public static String fetchDataFromEndpoint(String endpointUrl, HashMap<String, String> headerData)
			throws Exception {
		return fetchDataFromEndpoint(endpointUrl, headerData, Optional.empty());
	}

	public static String fetchDataFromEndpoint(String endpointUrl, Optional<String> authData) throws Exception {
		return fetchDataFromEndpoint(endpointUrl, new HashMap<>(), authData);
	}

	public static String fetchDataFromEndpoint(String endpointUrl, HashMap<String, String> headerData,
			Optional<String> authData) throws Exception {
		NetworkUtil.disableSslVerification();
		URL url = new URL(endpointUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		for (String key : headerData.keySet()) {
			addRequestHeader(connection, key, headerData.get(key));
		}
		if (authData.isPresent()) {
			addBasicAuthenticationHeader(connection, authData.get());
		}
		connection.setRequestMethod("GET");
		int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			StringBuilder response = new StringBuilder();
			try (Scanner scanner = new Scanner(connection.getInputStream())) {
				while (scanner.hasNext()) {
					response.append(scanner.nextLine());
				}
			}
			return response.toString();
		} else {
			log.info("Failed to fetch data from the endpoint. Response code: " + responseCode);
			return "Bad";
		}
	}

	private static void addRequestHeader(HttpURLConnection connection, String headerName, String headerValue) {
		connection.setRequestProperty(headerName, headerValue);
	}

	private static void addBasicAuthenticationHeader(HttpURLConnection connection, String authData) {
		connection.setRequestProperty("Authorization", authData);
	}

	public static String generateBase64AuthString(String username, String password) {
		String credentials = username + ":" + password;
		return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
	}

	// Calculate the last IP address in the subnet from the base IP and mask
	public static IPv4Address calculateNetworkEnd(IPv4Address baseIp, SubnetMask subnetMask) {
		long base = baseIp.toUnsignedInteger();
		long mask = ~subnetMask.toUnsignedInteger();
		return IPv4Address.fromUnsignedInteger(base | mask);
	}

	public List<IPv4Address> generateAddressesForSubnet(IPv4Address baseAddress, SubnetMask mask) {
		List<IPv4Address> addresses = new ArrayList<>();

		// Create Subnet object
		Subnet subnet = new Subnet(baseAddress, mask);

		// Calculate the network range
		long networkStart = subnet.calculateNetworkStart();
		long networkEnd = subnet.calculateNetworkEnd();

		// Generate all IP addresses within the subnet
		for (long i = networkStart; i <= networkEnd; i++) {
			addresses.add(IPv4Address.fromUnsignedInteger(i));
		}

		return addresses;
	}

	/**
	 * Default method to map clients to a Bitmap assuming default values for thread
	 * pool size and subnet mask.
	 *
	 * @param ipv4Addresses                      A list of IPv4 addresses to
	 *                                           validate.
	 * @param functionToValidateAddressIsToBeSet A function that takes an IPv4
	 *                                           address and returns a boolean
	 *                                           indicating whether the address
	 *                                           should be marked.
	 * @return A Bitmap with the addresses mapped according to the provided
	 *         function.
	 * @param blacklist A list passed in, where clients that respond but get mapped
	 *                  to, then later ignored.
	 */
	public static Bitmap mapClientsToBitmap(List<IPv4Address> ipv4Addresses,
			Function<IPv4Address, Boolean> functionToValidateAddressIsToBeSet, @NonNull List<Integer> blacklist) {
		return mapClientsToBitmap(ipv4Addresses, functionToValidateAddressIsToBeSet, new SubnetMask("255.255.255.0"),
				Executors.newFixedThreadPool(10), blacklist);
	}

	/**
	 * Maps a list of IPv4 addresses to a Bitmap using a validation function that
	 * determines if an address should be marked.
	 *
	 * @param ipv4Addresses      A list of IPv4 addresses to validate.
	 * @param validationFunction A function that takes an IPv4 address and returns a
	 *                           boolean indicating whether the address should be
	 *                           marked.
	 * @param subnetMask         The subnet mask to use for determining the valid
	 *                           range of addresses (defaults to 255.255.255.0 if
	 *                           not provided).
	 * @param executorService    Thread pool for concurrent execution.
	 * @param blacklist          A list passed in, where clients that respond but
	 *                           get mapped to, then later ignored.
	 * @return A Bitmap where each bit represents whether the corresponding address
	 *         is valid based on the provided function.
	 */
	public static Bitmap mapClientsToBitmap(@NonNull List<IPv4Address> ipv4Addresses,
			@NonNull Function<IPv4Address, Boolean> validationFunction, @NonNull SubnetMask subnetMask,
			@NonNull ExecutorService executorService, @NonNull List<Integer> blacklist) {

		// Initialize the bitmap assuming a /24 subnet for simplicity (256 addresses)
		Bitmap bitmap = new Bitmap(256);
		List<Future<Boolean>> futures = new ArrayList<>();

		for (IPv4Address address : ipv4Addresses) {
			if (blacklist.contains(address.hashCode())) {
				continue;
			}
			Callable<Boolean> task = () -> validationFunction.apply(address);
			futures.add(executorService.submit(task));
		}

		for (int i = 0; i < futures.size(); i++) {
			Future<Boolean> future = futures.get(i);

			try {
				boolean result = future.get(); // Get the result of the task
				if (result) {
					log.info("Found Good Client: " + ipv4Addresses.get(i).toString());
					// Convert the IPv4 address to a bitmap index and set the corresponding bit
					int index = ipv4ToIndex(ipv4Addresses.get(i), subnetMask);
					blacklist.add(ipv4Addresses.get(i).hashCode());
					bitmap.setBit(index); // Mark the corresponding bit in the bitmap
				} else {
				}
			} catch (Exception e) {
				// Handle exceptions for each address validation
				System.err.println("Error processing address " + ipv4Addresses.get(i) + ": " + e.getMessage());
			}
		}

		return bitmap;
	}
	
	public static CompletableFuture<Bitmap> mapClientsToBitmapAsync(
	        @NonNull List<IPv4Address> ipv4Addresses,
	        @NonNull Function<IPv4Address, CompletableFuture<Boolean>> validationFunction,
	        @NonNull SubnetMask subnetMask,
	        @NonNull List<IPv4Address> blacklist
	) {
	    Bitmap bitmap = new Bitmap(256);

	    // Create a list of async validation tasks
	    List<CompletableFuture<Void>> tasks = ipv4Addresses.stream()
	            .filter(ip -> !blacklist.contains(ip)) // Skip blacklisted addresses
	            .map(ip -> validationFunction.apply(ip)
	                    .thenAccept(isValid -> {
	                        if (isValid) {
	                            log.info("Found Good Client: " + ip.toString());
	                            int index = ipv4ToIndex(ip, subnetMask);
	                            synchronized (bitmap) {
	                                bitmap.setBit(index);
	                            }
	                        }
	                    })
	                    .exceptionally(e -> {
	                        log.severe("Error processing address " + ip + ": " + e.getMessage());
                            blacklist.add(ip);
	                        return null;
	                    }))
	            .toList();

	    // Return a CompletableFuture that completes when all tasks finish
	    return CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]))
	            .thenApply(ignored -> bitmap);
	}


	/**
	 * Converts an IPv4 address to an index in the Bitmap assuming the provided
	 * subnet mask.
	 * 
	 * @param address    The IPv4 address to convert.
	 * @param subnetMask The subnet mask (e.g., 255.255.255.0) used to calculate the
	 *                   index.
	 * @return The index corresponding to the IPv4 address within the specified
	 *         subnet range.
	 */
	private static int ipv4ToIndex(IPv4Address address, SubnetMask subnetMask) {
	    // Convert the IPv4 address and subnet mask to unsigned integers
	    long addressInt = address.toUnsignedInteger();
	    long subnetMaskInt = subnetMask.toUnsignedInteger();

	    // Calculate the host portion of the IP address
	    long index = addressInt & (~subnetMaskInt & 0xFFFFFFFFL);

	    // Cast the index back to an int, as it's guaranteed to fit within 32 bits
	    return (int) index;
	}

	/**
	 * Pings the specified IP address.
	 *
	 * @param ipAddress The IP address to ping.
	 * @param timeout   The timeout in milliseconds before the ping attempt is
	 *                  considered failed.
	 * @return True if the IP address is reachable, false otherwise.
	 */
	@SneakyThrows
	public static boolean ping(IPv4Address ipAddress, int timeout) {
		InetAddress inetAddress = InetAddress.getByName(ipAddress.toString());
		return inetAddress.isReachable(timeout);
	}

}
