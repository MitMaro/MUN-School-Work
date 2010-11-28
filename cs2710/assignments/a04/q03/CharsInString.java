/*
  CS 2710 (Fall 2008), Assignment #4, Question #3
          File Name: CharsInString.java
       Student Name: Tim Oram
              MUN #: #########
 */

/**
 * Counts the number of characters in a string.
 * 
 * @author mitmaro
 */
public class CharsInString {

	/**
	 * Testing method for class
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		
		// Lorem Ipsum anyone?
		String str = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit."
		+ "Vivamus nec lacus. Fusce vulputate venenatis lacus. Maecenas semper magna"
		+ "feugiat ipsum.";
		
		// some test code
		System.out.println("Searching String: ");
		System.out.println(str);
		System.out.println();
		System.out.println("Number of 'a': " + Integer.toString(find(str, 'a')));
		System.out.println("Number of 'f': " + Integer.toString(find(str, 'f')));
		System.out.println("Number of 'h': " + Integer.toString(find(str, 'h')));
		System.out.println("Number of ' ': " + Integer.toString(find(str, ' ')));
		System.out.println("Number of 'L': " + Integer.toString(find(str, 'L')));
		
	}

	/**
	 * Counts the number of characters in a string.
	 * 
	 * @param str The string to be searched.
	 * @param chr The character serach for.
	 * @return Count of character in string.
	 */
	public static int find(String str, char chr){
		return find(str, chr, 0);
	}
	
	/**
	 * Counts the number of characters in a string starting at depth.
	 * 
	 * @param str The string to be searched.
	 * @param chr The character serach for.
	 * @param depth The depth into the string to search.
	 * @return Count of character in string.
	 */
	public static int find(String str, char chr, int depth){
		if(depth == str.length()) return 0;
		
		if(str.charAt(depth) == chr)
			return 1 + find(str, chr, ++depth);
		else
			return find(str, chr, ++depth);
	}
	
}
