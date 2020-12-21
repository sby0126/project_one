package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
	
	public static byte[] encode(String text) {
		return Base64.encodeBase64(text.getBytes());
	}
	
	public static String decode(byte[] decodedText) throws UnsupportedEncodingException {
		return new String(Base64.decodeBase64(decodedText), "UTF-8");
	}
	
	public static String escape(String escapedText) {
		return URLDecoder.decode(escapedText);
	}
}
