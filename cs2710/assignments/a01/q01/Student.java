/*
  CS 2710 (Fall 2008), Assignment #1, Question #1
          File Name: Students.java
       Student Name: Tim Oram
              MUN #: #########
*/

// this class describes a student (name, number, credits and gpa)
class Student{
	
	// holds a student name, number credits and gpa
	private String name;
	private String id;
	private int credits;
	private double gpa;
	
	// constructor
	// creates a student setting name, number, credits and gpa
	public Student(String name, String id, int credits, double gpa){
		// validate and set name
		if(name.length() > 0)
			this.name = name;
		else
			throw new IllegalArgumentException("Empty Student Name Supplied");

		// validate and set id			
		if(id.length() == 6)
			this.id = id;
		else
			throw new IllegalArgumentException("Student ID needs to be 6 characters");

		// validate and set credits
		if(credits >= 0)
			this.credits = credits;
		else
			throw new IllegalArgumentException("Number of credits is required to be equal or greater then 0");
			
		// validate and set gpa
		if(gpa >= 0.0)
			this.gpa = gpa;
		else
			throw new IllegalArgumentException("GPA is required to be equal or greater then 0");
	}
	
	// sets the name of a student
	public void setName(String name){
		// validate and set name
		if(name.length() > 0)
			this.name = name;
		else
			throw new IllegalArgumentException("Empty Student Name Supplied");
	}
	
	// sets the student number of a student
	public void setId(String id){
		// validate and set id			
		if(id.length() == 6)
			this.id = id;
		else
			throw new IllegalArgumentException("Student ID needs to be greater then 0");
	}
	
	// sets the credits of a student
	public void setCredits(int credits){
		// validate and set credits
		if(credits >= 0)
			this.credits = credits;
		else
			throw new IllegalArgumentException("Number of credits is required to be equal or greater then 0");
	}
	
	// sets the gpa of a student
	public void setGpa(double gpa){
		// validate and set gpa
		if(gpa >= 0.0)
			this.gpa = gpa;
		else
			throw new IllegalArgumentException("GPA is required to be equal or greater then 0");
	}
	
	// the get methods for this clas
	// self documenting
	public String getName(){
		return this.name;
	}
	
	public String getId(){
		return this.id;
	}
	
	public int getCredits(){
		return this.credits;
	}
	
	public double getGpa(){
		return this.gpa;
	}
	
	// returns a formatted string containing information on this student
	public String toString(){
		return this.name + " (" + this.id + ") Credits: " + this.credits + "  GPA: " + this.gpa;
	}
	
}