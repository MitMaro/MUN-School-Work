/*
  CS 2710 (Fall 2008), Assignment #4, Question #1
          File Name: SmallestValue.java
       Student Name: Tim Oram
              MUN #: #########
 */

/**
 * Class that uses a Divide and Conquer algorithm to find the smallestcan be
 * used to return the smallest 
 * 
 * @author mitmaro
 */
public class SmallestValue {

	/**
	 * Runs the find methods printing the array used, run time and smallest int.
	 * 
	 * @param args Command Line Arguments
	 */
	public static void main(String[] args) {

		// create an array
		int[] a = new int[]{3,7,5,7,9,2,3,6,4,6,8,2,3,5,7};
		
		// get current system time in milliseconds
		long s = System.currentTimeMillis();
		
		// run the find method 100000 times
		for (int i = 0; i < 100000; ++i){
			find(a);
		}
		
		// print the run time
		System.out.println("Run Time (100000 Runs): "
				+ Long.toString(System.currentTimeMillis() - s) + "ms");

		// print the array
		System.out.print("Array:");
		for(int b: a) System.out.print(b + " ");
		System.out.println();
		
		// print the smallest int
		System.out.println("Smallest Integer: " + Integer.toString(find(a)));
	
	}

	/**
	 * Finds the smallest number in a array. Uses 0 and array length minus 1 for
	 * start and end index.
	 * 
	 * @param ary The array to be searched.
	 * @return The value of the smallest integer within range.
	 */
	public static int find(int [] ary){
		return find(ary, 0, ary.length - 1);
	}

	/**
	 * Finds the smallest number in a array between a start and end.
	 * 
	 * @param ary The array to be searched.
	 * @param start The start index for the search.
	 * @param end The end index for the search.
	 * @return The value of the smallest integer within range.
	 */
	public static int find(int[] ary, int start, int end){
		// if the current division contains 2 or less items return the number 
		if((end - start) <= 1) return Math.min(ary[start], ary[end]);
		return Math.min(find(ary, start, (start + end)/2), find(ary, (start + end)/2 + 1, end));
		
	}
	
}