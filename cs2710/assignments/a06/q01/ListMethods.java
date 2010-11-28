/*
  CS 2710 (Fall 2008), Assignment #6, Question #1
          File Name: ListMethods.java
       Student Name: Tim Oram
              MUN #: #########
 */

import java.util.Random;

public class ListMethods {

	// test code
	public static void main(String[] args) {
		// there wasn't anything in the question saying that this had to take
		// any input from the user so I made it all random
		
		
		// because random values are cool
		Random gen = new Random();
		
		// the maximum value of the random number (ranges from 1-10)
		int n = gen.nextInt(10) + 1;
		
		// the size of the linked list generated (ranges from 10-30)
		int s = gen.nextInt(21) + 10;
		
		// set the first node
		Node node = new Node(gen.nextInt(n + 1));
		
		// add some more nodes
		for(int i = 0; i < s - 1; ++i)
			// add a node with a random value
			node.addNode(gen.nextInt(n + 1));
		
		// print the list of nodes
		System.out.println("List Values:");
		Node i = node;
		do{
			System.out.print(i.getValue() + " ");
		}while((i = i.getNext()) != null);
		System.out.println();

		// generate two random numbers for the valuesBetween method
		int a = gen.nextInt(n + 1);
		int b = gen.nextInt(n + 1);
		// print the number of values between a and b
		System.out.println(String.format("Count of nodes between %d and %d is %d", a, b, valuesBetween(node, a, b)));
		
		
		// get a list of unique values
		Node unique = uniqueList(node);
		// print the list of nodes
		System.out.println("Unique Values:");
		i = unique;
		do{
			System.out.print(i.getValue() + " ");
		}while((i = i.getNext()) != null);
		System.out.println();
		
	}
	
	// Gets the count of the values between a and b in the list node
	public static int valuesBetween(Node node, int a, int b){
		
		// switch a and b if a is greater then b
		if(a > b){
			// swap
			int tmp = a;
			a = b;
			b = tmp;
		}
		
		// holds the count
		int count = 0;
		
		do{
			// if node value is between a and b inclusive increase count
			if(node.getValue() >= a && node.getValue() <= b)
				count++;
		// loop while the next node in the list is not null
		}while((node = node.getNext()) != null);
		
		// and return the count
		return count;
	}
	
	// Returns a Node list of unique values in the provided Node list
	public static Node uniqueList(Node node){

		// flag that is set to true when a value is found to be duplicate
		boolean found = false;
		
		// the new list of nodes that is returned
		Node unique = new Node(node.getValue());
		
		// used to iterate over the nodes in the unique list
		Node i = unique;
		
		// loop over the supplied list of nodes
		do{
			// loop over the unique values list of nodes
			do{
				// if the value was already added to the unique list
				if(node.getValue() == i.getValue())
					// set a flag saying so
					found = true;
			// loop while there are nodes to loop over and not found
			}while((i = i.getNext()) != null && !found);
			
			// if the number was not found in the unique list
			if(!found){
				// add the value to the unique list
				unique.addNode(node.getValue());
			}
			// reset the Node i to point to the first node in the unique list
			i = unique;
			// and reset the found flag
			found = false;
		// while there are nodes left to loop over
		}while((node = node.getNext()) != null);
		
		// return the unique list
		return unique;
	}
}
