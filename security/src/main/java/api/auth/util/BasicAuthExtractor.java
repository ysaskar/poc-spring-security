package api.auth.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuthExtractor {

	public static String[] extract(String basicAuth) {
		String base64Credentials = basicAuth.substring("Basic".length()).trim();
		byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
		String credentials = new String(credDecoded, StandardCharsets.UTF_8);
		final String[] values = credentials.split(":", 2);
		return values;
	}
}
