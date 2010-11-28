/*
  CS 2710 (Fall 2008), Assignment #6, Question #2
          File Name: Student.java
       Student Name: Tim Oram
              MUN #: #########
 */

import java.util.Scanner;

/**
 * Describes a Student.
 * 
 * @author Tim Oram (#########)
 */
public class Student {

	/**
	 * Allows a user to input the number of students to add to a list of
	 * students. Then asks for the students name, number and mark for each
	 * student and adds it to the list of students.
	 * 
	 * After all students are entered this method calculates and prints the
	 * average mark and highest mark for the list of students and prints the
	 * names of the students with a below average mark and the names of students
	 * with the highest mark. 
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args){
		
		// contains a linked list of students
		LinkedList students = new LinkedList();
		
		// well we need input and the Scanner class is good at that
		Scanner in = new Scanner(System.in);

		// holds the values inputed by the user
		String name;
		int number;
		double mark;
		
		// holds the average class mark
		double avg = 0.0;

		// holds the top mark
		double topmark = 0.0;
		
		// ask for the number of students to enter
		System.out.print("Number of students to enter:");
		int n = in.nextInt();
		
		// stops the next line from being read automatically (wish I knew what
		// causes this, might be a newline issue)
		in.nextLine();
		
		// for each student to add
		for(int i = 0; i < n; ++i){
			// ask for student information
			System.out.print("Student Name:");
			name = in.nextLine().trim();
			System.out.print("Student Number:");
			number = in.nextInt();
			System.out.print("Student Mark:");
			mark = in.nextDouble();
			
			// add the student to the linked list
			students.addFirst(new Student(name, number, mark));
			
			// print a message saying student was added
			System.out.println("Student Added");
			// stops the next line from being read automatically (wish I knew what
			// causes this, might be a newline issue)
			in.nextLine();
		}
		
		// create an iterator to iterate over the list of students
		ListIterator iter = students.listIterator();
		
		
		// figure out the average and highest mark for the students
		while(iter.hasNext()){
			// get the student
			Student s = (Student)iter.next();
			// and the students mark to the average
			avg += s.getMark();
			// get the max between the current top mark and tis students mark
			topmark = Math.max(s.getMark(), topmark);
		}
		avg /= (double)n;

		// print the average mark
		System.out.println(String.format("Student Average: %g", avg));
		
		// recreate the iterator
		iter = students.listIterator();

		// print the students with marks below the average
		System.out.println("Student(s) with below average mark:");
		while(iter.hasNext()){
			// get the student
			Student s = (Student)iter.next();
			// if the students mark is less then average
			if(s.getMark() < avg)
				//print the students name
				System.out.println(s.getName());
		}
		
		// print the highest mark
		System.out.println(String.format("Highest Mark: %g", topmark));
		
		// print the students with the highest mark
		iter = students.listIterator();
		System.out.println("Student(s) with highest mark:");
		while(iter.hasNext()){
			// get the student
			Student s = (Student)iter.next();
			// if the student has the same mark as the highest mark
			if(s.getMark() == topmark)
				// print the students name
				System.out.println(s.getName());
		}
		
	}

	/** contains the students name */
	private String name;
	/** contains the students number */
	private int number;
	/** contains the students mark */
	private double mark;
	
	/**
	 * Constructs a student
	 * 
	 * @param name The students name
	 * @param number The student number
	 * @param mark The students mark
	 */
	public Student(String name, int number, double mark){
		this.name = name;
		this.number = number;
		this.mark = mark;
	}

	/**
	 * Get the students name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the students name
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the students number
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Sets the students number
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Gets the students mark
	 * @return the mark
	 */
	public double getMark() {
		return mark;
	}

	/**
	 * Sets the students mark
	 * @param mark the mark to set
	 */
	public void setMark(double mark) {
		this.mark = mark;
	}

	/**
	 * @return A string representation of a student
	 */
	public String toString() {
		return this.name + "(" +this.number +"): " + this.mark;
	}	
	
}
