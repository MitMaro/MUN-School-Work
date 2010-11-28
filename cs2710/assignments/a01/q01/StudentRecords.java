/*
  CS 2710 (Fall 2008), Assignment #1, Question #1
          File Name: StudentList.java
       Student Name: Tim Oram
              MUN #: #########
*/
import java.util.Scanner;
import java.util.InputMismatchException;


// This class provides a console based interface to the StudentList class
public class StudentRecords{

	// the main method. Prompts user for directions on input and output
	public static void main(String[] args){
		
		// create a new student list
		StudentList students = new StudentList();
		
		// well we need input and the Scanner class is good at that
		Scanner in = new Scanner(System.in);
		
		// contains the command given by the user
		String cmd;
	
		System.out.println("Student Records Prompt");
		
		// enter the program loop
		// this loops until someone puts in the command "quit"
		do{
			// print out the console prompt
			System.out.print(">> ");
		
			// get the command for the user. trim whitespace to make it more user friendly.
			cmd = in.nextLine().trim();
			
			// if cmd is add
			if(cmd.equals("add")){
				try{
					// get student information
					System.out.print("  Student Name: ");
					String name = in.nextLine();
					System.out.print("Student Number: ");
					String number = in.nextLine();
					System.out.print("       Credits: ");
					int credits = in.nextInt();				
					System.out.print("           GPA: ");
					double gpa = in.nextDouble();
					
					// add it the the list
					students.add(name, number, credits, gpa);
					
					// let the user know that the student has been added
					System.out.println("Student Added");
				}
				catch(IllegalArgumentException e){
					// print a message if there was invalid dadt provided to the
					// add method
					System.out.println(e.getMessage());
				}
				catch(InputMismatchException e){
					// catch invalid input and display a message
					System.out.println("Invalid Input");
				}
				
				// this fixes a problem where the nextLine at the top of the loop
				// would capture an empty string on the next loop. I think it has
				// something to do with nextInt and nextDouble but I am unsure.
				in.nextLine();
				
			}
			// the update command
			else if(cmd.equals("update")){
				try{
					// grab the student number
					System.out.print("Student Number: ");
					String number = in.nextLine();
					
					// prompt for what to update
					System.out.print("What to Update (credits or gpa): ");
					String subcmd = in.nextLine();
					
					if(subcmd.equals("credits")){
						// prompt for the number of credits
						System.out.print("Enter the number of credits: ");
						int credits = in.nextInt();
						
						// and update and let the user know it worked 
						students.updateCredits(number, credits);
						System.out.println("Credits have been updated.");
						
						// see comment in the add command for more info
						in.nextLine();
					}
					else if(subcmd.equals("gpa")){
						//prompt fot the students GPA
						System.out.print("Enter the GPA: ");
						double gpa = in.nextDouble();
						// and update and let the user know it worked
						students.updateGpa(number, gpa);
						System.out.println("GPA have been updated.");
						
						// see comment in the add command for more info
						in.nextLine();
					}
					else{
						System.out.println("Invalid update command. Must by credits or gpa.");
					}
				}
				catch(IllegalArgumentException e){
					// catch invalid information being passed to the update functions
					System.out.println(e.getMessage());
				}
				catch(InputMismatchException e){
					// catch invalid input being supplied
					System.out.println("Invalid Input");
				}
				
			}
			// the print command
			else if(cmd.equals("print")){
				try{	
					System.out.print("Student Number: ");
					String number = in.nextLine();
					students.print(number);
				}
				catch(InputMismatchException e){
					System.out.println("Invalid Input");
				}
			}
			// the report command
			else if(cmd.equals("report")){
				students.report();
			}
			// if an invalid command is passed then display a small help message
			else if(!cmd.equals("quit") && !cmd.equals("")){
				System.out.println("Invalid Command Supplied");
				System.out.println("Available commands are: add, update, print, report and quit");
			}
		
		}while (!cmd.equals("quit")); // only exit loop when quit is passed as a command
	}
}