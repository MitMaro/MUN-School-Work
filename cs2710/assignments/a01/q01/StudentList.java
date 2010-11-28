/*
  CS 2710 (Fall 2008), Assignment #1, Question #1
          File Name: StudentList.java
       Student Name: Tim Oram
              MUN #: #########
*/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

// this class describes a list of students
class StudentList{
	
	// the array list of sudents
	private ArrayList<Student> students;

	// Constructor
	// just creates an empty ArrayList
	public StudentList(){
		this.students = new ArrayList<Student>();
	}

	// adds a student to the list of students
	public void add(String name, String id, int credits, double gpa){
		this.students.add(new Student(name, id, credits, gpa));
	}
	
	// finds the index of a student by their student number
	private int findIndexById(String id){
		// loop through the students till the student number if found
		for (int i = 0; i < this.students.size(); i++) {
 			if(this.students.get(i).getId().compareTo(id) == 0){
				// return the index
 				return i;
 			}
 		}
 		// if the number is not found return a -1
 		return -1;
	}
	
	// prints out a formatted string of a students information
	public void print(String id){
		// find the index of the student
		int index = this.findIndexById(id);
		// if student number was found
		if(index > -1){
			System.out.println(students.get(index).toString());
		}
		// if the sutdent was not found print a message saying so
		else{
			System.out.println("Record not found for Student Number: " + id);
		}
	}
	
	// updates the GPA of a student
	public void updateGpa(String id, double value){
		// find the index of the student
		int index = this.findIndexById(id);
		// if student number was found
		if(index > -1){
			this.students.get(index).setGpa(value);
		}
		// if the student was not found thrown an exception
		else{
			throw new IllegalArgumentException("Invalid student number provided");
		}
	}
	
	// update the credits of a student
	public void updateCredits(String id, int value){
		// find the index of the student
		int index = this.findIndexById(id);
		// if the student number was found
		if(index > -1){
			this.students.get(index).setCredits((int)value);
		}
		// if not then thrown an exception
		else{
			throw new IllegalArgumentException("Invalid student number provided");
		}
	}
	
	// prints out a report of all students in the list
	public void report(){
		// sort using the Collections sort method
		Collections.sort(this.students, new StudentListCompare());
		
		// loop through all students and print out their information
		for(Student stu: this.students){
			System.out.println(stu.toString());
		}
	}


	// sub class used in the sort method
	// contains one function thats used to compare two students
	class StudentListCompare implements Comparator<Student>{
		public int compare(Student stu1, Student stu2){
			
			if(stu1.getGpa() > stu2.getGpa()){
				return -1;
			}
			else if(stu1.getGpa() < stu2.getGpa()){
				return 1;
			}
			
			return 0;
		}
	}
	
}

