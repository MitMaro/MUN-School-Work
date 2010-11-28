/*
  CS 2710 (Fall 2008), Assignment #4, Question #2
          File Name: Sentence.java
       Student Name: Tim Oram
              MUN #: #########
 */

/**
 * Contains methods for finding a matching String in a sentence.
 * 
 * @author mitmaro
 *
 */
public class Sentence {

	/** A string contianing a sentence. */
	private String sentence;
	
	/**
	 * Run this to test the class.
	 * 
	 * @param args Array of command line arguments.
	 */
	public static void main(String[] args) {

		// create a new Sentence
		Sentence s1 = new Sentence("abcdefghijk");
		// search for the start of the Sentence
		System.out.println(" Should be true: " + Boolean.toString(s1.find("abc")));
		// search for a string right at the end of the Sentence
		System.out.println(" Should be true: " + Boolean.toString(s1.find("ijk"))); 
		// search for a string not in the Sentence
		System.out.println("Should be false: " + Boolean.toString(s1.find("jkl")));
	}

	/**
	 * Constructs a new Sentence.
	 * 
	 * @param str The string to search for.
	 */
	public Sentence(String str){
		this.sentence = str;
	}
	
	/**
	 * Finds if there is an occurance of a string in a sentence.
	 * 
	 * @param sub The String to find.
	 * @return True on found, False if not
	 */
	public boolean find(String sub){
		// call the other sentence method with start being 0
		return find(sub, 0);
	}
	
	
	/**
	 * Finds if there is an occurance of a string in a setence.
	 * 
	 * @param str String to search for
	 * @param start Index of where to start search
	 * @return True on found, False if not
	 */
	public boolean find(String str, int start){
		
		// if there is not enough string left for a match return false
		if(start + str.length() - 1 >= this.sentence.length()) return false;
		
		// if the substring is a match then return true
		if(this.sentence.substring(start, start + str.length()).equals(str)){
			return true;
		}
		
		// increase start and search again
		return find(str, ++start);
		
	}
		
}
