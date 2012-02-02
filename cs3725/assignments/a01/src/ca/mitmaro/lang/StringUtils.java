/**
 * Tim Oram's Commons Libray - Lang Library
 * By: Tim Oram
 */
package ca.mitmaro.lang;

public class StringUtils {
	
	public static String join(String[] parts) {
		return StringUtils.join(parts, " ");
	}
	
	public static String join(String[] parts, String glue) {
		
		int k = parts.length;
		
		if (k == 0) {
			return "";
		}
		
		StringBuilder rtn = new StringBuilder();
		
		rtn.append(parts[0]);
		
		for (int i = 1; i < parts.length; i++) {
		  rtn.append(glue).append(parts[i]);
		}
		
		return rtn.toString();
	}
	
	public static String truncate(String str, int length) {
		return StringUtils.truncate(str, length, " ...");
	}
	
	public static String repeat(String str, int repeat) {
		
		if (str == null) {
			return null;
		}
		
		if (repeat <= 0) {
			return "";
		}
		
		int input_length = str.length();
		if (repeat == 1 || input_length == 0) {
			return str;
		}
		
		StringBuilder sb = new StringBuilder(input_length * repeat);
		for (int i = 0; i < repeat; i++) {
			sb.append(str);
		}
		return sb.toString();
	}
	
	public static String truncate(String str, int length, String prepend) {
		if (str.length() <= length) {
			return str;
		}
		
		return str.substring(0, length) + prepend;
		
	}
	
}
