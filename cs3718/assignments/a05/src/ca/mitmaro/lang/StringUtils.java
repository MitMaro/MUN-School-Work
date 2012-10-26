/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.lang;

/**
 * Various useful string methods
 * 
 * @author Tim Oram (MitMaro)
 *
 */
public class StringUtils {
	
	/**
	 * Joins an array of strings int a single string using a space as a separator.
	 * 
	 * @param parts The parts of the string to join.
	 * @return The joined string
	 */
	public static String join(String[] parts) {
		return StringUtils.join(parts, " ");
	}
	
	/**
	 * Joins an array of strings int a single string using the provided glue as a separator.
	 * @param parts The parts of the string to join
	 * @param glue The separator
	 * @return The joined string
	 */
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
	
	/**
	 * Repeats a string by concatenating together the provided string the number or repeats provided.
	 * 
	 * @param str The string to repeat
	 * @param repeat The number of times to repeat the string
	 * @return The string after being repeated
	 */
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
	
	/**
	 * Truncates the string to the length provided. A string that is longer than length
	 * will have "..." prepended to the end.
	 * 
	 * @param str The string to truncate
	 * @param length The length of the truncated string
	 * @return The truncated string
	 */
	public static String truncate(String str, int length) {
		return StringUtils.truncate(str, length, "...");
	}

	/**
	 * Truncates the string to the length provided. A  string that is longer than length
	 * will have prepend prepended to the end.
	 * 
	 * @param str The string to truncate
	 * @param length The length of the truncated string
	 * @param prepend The prepend
	 * @return The truncated string
	 */
	public static String truncate(String str, int length, String prepend) {
		if (str.length() <= length) {
			return str;
		}
		
		return str.substring(0, length - prepend.length()) + prepend;
	}
	
}
