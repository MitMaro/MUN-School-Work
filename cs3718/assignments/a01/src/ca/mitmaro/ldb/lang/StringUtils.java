/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.lang;

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
	
	public static String truncate(String str, int length, String prepend) {
		if (str.length() <= length) {
			return str;
		}
		
		return str.substring(0, length) + prepend;
		
	}
	
}
