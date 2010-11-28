/*
  CS 2710 (Fall 2008), Assignment #2, Question #2
          File Name: Person.java
       Student Name: Tim Oram
              MUN #: #########
 */

// this class describes a person
public class Person {

	// stores a persons name and height
	private String name;
	private double height;

	// constructor to create a person with a name and height
	public Person(String name, double height) {
		this.name = name;
		this.height = height;
	}

	// set persons height
	public void setHeight(double height) {
		this.height = height;
	}

	// set persons name
	public void setName(String name) {
		this.name = name;
	}

	// gets the persons height
	public double getHeight() {
		return this.height;
	}

	// get the persons name
	public String getName() {
		return this.name;
	}

	public String toString() {
		return this.name + " is " + this.height + " cm tall.";
	}
}