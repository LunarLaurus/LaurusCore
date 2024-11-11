package net.laurus.util;

import static net.laurus.Constant.JSON_MAPPER;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;
import net.laurus.network.IPv4Address;
import net.laurus.network.IloUser;

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

    public static boolean isValidIPAddress(String ipAddress) {
        String[] octets = ipAddress.split("\\.");
        if (octets.length != 4) {
            return false;
        }
        for (String octet : octets) {
            if (!isValidOctetValue(octet)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidOctetValue(int value) {
        return value >= 0 && value <= 255;
    }

    private static boolean isValidOctetValue(String octet) {
        try {
            int value = Integer.parseInt(octet);
            return isValidOctetValue(value);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int ipAddressToInt(String ipAddress) {
        String[] octets = ipAddress.split("\\.");
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result |= (Integer.parseInt(octets[i]) << ((3 - i) * 8));
        }
        return result;
    }

    public static String intToIPAddress(int ipAddress) {
        StringBuilder result = new StringBuilder();
        for (int i = 3; i >= 0; i--) {
            result.append((ipAddress >> (i * 8)) & 0xFF);
            if (i > 0) {
                result.append(".");
            }
        }
        return result.toString();
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

    public static String fetchDataFromEndpoint(String endpointUrl, HashMap<String, String> headerData) throws Exception {
        return fetchDataFromEndpoint(endpointUrl, headerData, Optional.empty());
    }

    public static String fetchDataFromEndpoint(String endpointUrl, Optional<String> authData) throws Exception {
        return fetchDataFromEndpoint(endpointUrl, new HashMap<>(), authData);
    }
    
    public static String fetchDataFromEndpoint(String endpointUrl, HashMap<String, String> headerData, Optional<String> authData) throws Exception {
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
            try (java.util.Scanner scanner = new java.util.Scanner(connection.getInputStream())) {
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

    private static final Map<IPv4Address, String> iloUuidCache = new ConcurrentHashMap<>();
    private static final Map<IPv4Address, Integer> iloSystemFanCountCache = new ConcurrentHashMap<>();
    
    @SneakyThrows
	public static String getIloUuidForClient(IPv4Address ip) {
		if (ip == null) {
			return null;
		}
		if (iloUuidCache.containsKey(ip)) {
			return iloUuidCache.get(ip);
		}
		String uuid = XmlToJsonUtil
				.convertXmlToJson(
						NetworkUtil.fetchDataFromEndpoint("https://" + ip.toString() + "/xmldata?item=all"))
				.path("RIMP").path("MP").path("UUID").asText("Invalid").trim();
		iloUuidCache.put(ip, uuid);
		return uuid;
	}

	public static int getFanCountFromIlo(IPv4Address ip, IloUser iloUser) throws Exception {
		Integer fanCount = iloSystemFanCountCache.get(ip);
		if (fanCount == null) {
			HashMap<String, String> headers = new HashMap<>();
			String thermal = NetworkUtil.fetchDataFromEndpoint(
					"https://" + ip.getAddress() + "/rest/v1/Chassis/1/Thermal", headers, iloUser.getWrappedAuthData());
			JsonNode fansArrayNode = JSON_MAPPER.readTree(thermal).path("Fans");
			fanCount = fansArrayNode.isArray() ? fansArrayNode.size() : -1;
			iloSystemFanCountCache.put(ip, fanCount);
		}
		return fanCount;

	}
    
}
