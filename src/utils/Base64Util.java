package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;

public class Base64Util {
	
	private static final String BASE_64_PREFIX = "data:image/png;base64,";
	
	public static byte[] decode(String decodedText) throws UnsupportedEncodingException {
        if (decodedText.startsWith(BASE_64_PREFIX))
            return Base64.getDecoder().decode(decodedText.substring(BASE_64_PREFIX.length()));
        else
            throw new IllegalStateException("it is not base 64 string");
	}
	
	public static String escape(String escapedText) {
		return URLDecoder.decode(escapedText);
	}
	
}
