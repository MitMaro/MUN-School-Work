/*
  CS 2710 (Fall 2008), Assignment #6, Question #1
          File Name: Node.java
       Student Name: Tim Oram
              MUN #: #########
 */

// a very simplified version of a linked list of nodes that stores integers
// I combined the functionality of the Node class and the LinkedList class into
// one class because I only needed a very simplified version for the assignment.
public class Node{
	
	// the value and a link to the next node in the list
	private int value;
	private Node next;
	
	// constructs a node with just a value
	public Node(int value){
		this.value = value;
		this.next = null;
	}

	// constructs a node that is linked to another node
	// private because this should only be accessed when another node is being
	// added to the node list
	private Node(int value, Node next){
		this.value = value;
		this.next = next;
	}
	
	// add a node to the node list
	public void addNode(int value){
		// set the value next to the node just created and set the next of the
		// node being create to what this node used to be pointing too
		this.next = new Node(value, this.next);
	}
	
	// gets the value of this node
	public int getValue(){
		return this.value;
	}
	
	// gets and returns the next node
	public Node getNext(){
		return this.next;
	}
}