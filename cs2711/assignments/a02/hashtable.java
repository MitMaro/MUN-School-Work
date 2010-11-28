// COMP 2711 assignment 2, Fall 2009
// author: Tim Oram, oram, #########
/*
  COMP 2711 (Fall 2008), Assignment #2
          File Name: hashtable.java
       Student Name: Tim Oram
              MUN #: #########
*/

/*
1. cod: 3 * 1 + 15 * 2 + 4 * 2 ^ 2 = 49
     it: 9 * 1 + 20 * 2 = 49
    end: 5 * 1 + 14 * 2 + 4 * 2 ^ 2 = 49
    see: 19 * 1 + 5 * 2 + 5 * 2 ^ 2 = 49
     so: 19 * 1 + 15 * 2 = 49

 3. No not always, depending on the size and words in the list different
    parameters are better. n has more of an effect on the efficiency of the
    hash. Prime numbers for n of course often work best.
*/

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Vector;

class hashtable {

	// out values from the command line
	public static int hash_a;
	public static int hash_n;

	// a very much stripped down linked list, if Java supported generic typed
	// arrays I could use the Java linked list. But that would be difficult.
	private static class WordBucket {

		// the node of the linked list
		private class Node {
			public String word; // The word data
			public Node next; // A reference to the next word

			public Node(String value){
				this.word = value;
				this.next = null;
			}

			public Node(Node n, String value){
				this.next = n;
				this.word = value;
			}
		}

		private Node first_node;
		// I could count count the size of the linked list when I need the size
		// but this should be faster
		private int size;

		public WordBucket(){
			this.first_node = null;
			this.size = 0;
		}

		// add a value to the linked list
		public void add(String value){
			this.first_node = new Node(this.first_node, value);
			this.size++;
		}

		// check if the linked list contains the given string
		public boolean contains(String s){
			Node n = this.first_node;
			// linear search... :(
			while(n != null){
				if(n.word.equals(s)){
					return true;
				}
				n = n.next;
			}
			return false;
		}

		public int size(){
			return this.size;
		}

		// return the value at index i
		public String get(int i){
			Node n = this.first_node;
			int j = 0;
			// linear seek...
			while(j < i){
				n = n.next;
				j++;
			}
			return n.word;
		}
	}

	public static void main(String[] args){

		// a usage message before anything else
		if(args.length != 4){
			System.out.println("Usage: java hashtable command a n wordfile");
			System.out.println("  command is either search for interactive " +
			                   "search or test for non-interactive test.");
			System.out.println("  a and n are the parameters for the hash " +
			                   "function: (x_0  * a^(k - 1) + ... + x_(k-1)) " +
			                   "mod n.");
			System.out.println("  wordfile is a dictionary file contain a " +
			                   "list of words to hash.\n");
			return;
		}

		String line; // the line being read
		String[] tmp; // used when a line is split
		BufferedReader in; // the file reader
		WordBucket[] hash_table; // the hash_table/array
		int hash; // stores the calculated hash


		// check parameter a
		try{
			hash_a = Integer.parseInt(args[1]);
		}
		catch(NumberFormatException e){
			System.out.println("Invalid value given for a");
			return;
		}

		// check parameter n
		try{
			hash_n = Integer.parseInt(args[2]);
		}
		catch(NumberFormatException e){
			System.out.println("Invalid value given for n");
			return;
		}

		// open the wordfile
		try{
			in = new BufferedReader(new FileReader(args[3]));
		}
		catch(FileNotFoundException e){
			System.out.println("The word file provided does not exist.");
			return;
		}

		// initialize the hash table
		hash_table = new WordBucket[hash_n];
		for(int i = 0; i < hash_n; i++){
			hash_table[i] = new WordBucket();
		}

		// read the file and hash each word
		try{
			// read each line in the file
			while( (line = in.readLine()) != null){
				// split up words in the line
				tmp = line.split("\\s+");
				// for each word in line in file
				for(String s: tmp){
					// calculate our hash
					hash = hash(s);
					// we do not want to add a word twice
					if(!hash_table[hash].contains(s)){
						hash_table[hash].add(s);
					}
				}
			}
		}
		catch(IOException e){
			System.out.println("An unexpected IO error occurred.");
			return;
		}

		if(args[0].equals("test")){

			// find the hash_table entry with the longest chain
			int max_chain_index = 0;
			int words = 0;

			// find the entry in the hash table that contains the most words
			// (ie. the most collisions) and keep count of the non-empty buckets
			// in the hash array.
			for(int i = 0; i < hash_table.length; i++){
				if(hash_table[i].size() > hash_table[max_chain_index].size()){
					max_chain_index = i;
				}
				words += hash_table[i].size();
			}

			// print out some stats on the test
			System.out.printf("With a=%d, n=%d on input file wordfile\n",
			                  hash_a, hash_n);
			// I think this is the correct definition of load factor, there
			// seems to be several but this is the one explained on wikipedia
			// and I trust wikipedia
			System.out.printf("the load factor is %.1f\n",
			                  (float)(hash_n / words));
			System.out.printf("the longest chain is of length %d on words\n",
			                  hash_table[max_chain_index].size());
			System.out.print(hash_table[max_chain_index].get(0));
			for(int i = 1; i < hash_table[max_chain_index].size(); i++){
				System.out.print(", " + hash_table[max_chain_index].get(i));
			}
			System.out.println(".");

		}
		else if(args[0].equals("search")){
			// get the search words from the user
			BufferedReader user_in = new BufferedReader(
			                                  new InputStreamReader(System.in));
			System.out.print("Enter search pattern: ");
			try{
				// for each word given
				String[] terms = user_in.readLine().split("\\s+");
				for(String s: terms){
					// check its hash against the hash_table and see
					// if the word was found
					hash = hash(s);
					if(hash_table[hash].contains(s)){
						System.out.print(s + " ");
					}
				}
				System.out.println();
			}
			catch(IOException e){
				System.out.println("An unexpected IO error occurred.");
				return;
			}
		}
		else{
			System.out.println("Invalid command given: " + args[1]);
		}

	}

	// the function for doing the hashing
	public static int hash(String word){
		long value = 0;
		int k = word.length();
		// the sum
		for(char c: word.toCharArray()){
			value += (int)((c * Math.pow(hash_a, k-1)) % hash_n);
		}
		// the mod
		return (int)value % hash_n;
	}
}
