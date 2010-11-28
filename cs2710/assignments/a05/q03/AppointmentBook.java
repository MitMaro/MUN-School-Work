import java.lang.IllegalArgumentException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Problem Discrption:<br>
 * Write a program that keeps an appointment book. Users should be able to print
 * out the appointments for a given day as well as add new appointments.
 * <p>
 * An appointment consists of a start date/time, a end date/time and a
 * description.
 * <p>
 * <p>
 * This class describes an AppointmentBook. It allows you to add Appointments
 * assuring that no two appointments will overlap each other. Also the ability
 * to print the appointments for a given day is provided.
 * 
 * @author Tim Oram (#########)
 */
public class AppointmentBook {

	/**
	 * A book of Appointments
	 */
	private ArrayList<Appointment> book;

	/**
	 * The main method, used to interact with the Appointment Book class.
	 * <p>
	 * When this program is run it will display a prompt. This prompt takes one
	 * of <code>add</code>, <code>print</code> or <code>quit</code> commands.
	 * <p>
	 * The <code>add</code> command allows you to add an appointment to the 
	 * appointment book. It will again prompt you for additional information on
	 * the appointment. It will display a error if you try to add an appointment
	 * that will conflict with an appointment already in the book.
	 * <p>
	 * The <code>print</code> command will print all the appointments for a
	 * date. It will prompt you for a date.
	 * <p>
	 * The <code>quit</code> command is used when you with to quit the program.   
	 * <p>
	 * Pseudo Code:
	 * <code><pre>
	 * main(args)
	 *   create a new appointment book
	 *   start loop
	 *     prompt user for command
	 *     if command is add
	 *       prompt user for star date/time, end date/time and description
	 *       Add a new appointment to the appointment book
	 *       if the appointment was overlapping another
	 *         print error message
	 *     else if comment is print
	 *       prompt user for the date
	 *       call method to print appointments for that date
	 *     else if command is not quit and not empty
	 *   	 print a small help message
	 *   end loop if command was quit
	 * </pre></code>
	 * 
	 * @param args Command line arguments. This program does not use command
	 * line arguments.
	 */
	public static void main(String[] args){
		
		// create a new Appointment Book
		AppointmentBook book = new AppointmentBook();
		
		// well we need input and the Scanner class is good at that
		Scanner in = new Scanner(System.in);
		
		// contains the command given by the user
		String cmd;
	
		System.out.println("Appointment Book");

		// enter the program loop
		// this loops until someone puts in the command "quit"
		do{
			// print out the console prompt
			System.out.print(">> ");
		
			// get the command for the user. trim whitespace to make it more
			// user friendly.
			cmd = in.nextLine().trim();
			
			// if cmd is add
			if(cmd.equals("add")){
				try{

					// create a SimpleDateFormat for parsing the dates from the
					// prompt
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
					
					// get appointment information
					System.out.print("Start Date & Time (dd/mm/yyyy hh:mm:ss am/pm): ");
					Date start = sdf.parse(in.nextLine()); //parse date
					System.out.print("  End Date & Time (dd/mm/yyyy hh:mm:ss am/pm): ");
					Date end = sdf.parse(in.nextLine()); // parse date
					System.out.println("Description: ");
					String desc = in.nextLine();				
					
					// convert the 'Date's into 'Calendar's
					Calendar s = Calendar.getInstance();
					s.setTime(start);
					Calendar e = Calendar.getInstance();
					e.setTime(end);
					
					// add the appointment
					book.addAppointment(new Appointment(s, e, desc));

					// let the user know that the appointment has been added
					System.out.println("Appointment Added");						
				}
				catch(ParseException e){
					// prints a message when a Date given is invalid
					System.out.println(e.getMessage());
				}
				catch(IllegalArgumentException e){
					// print a message if there was invalid data provided to the
					// addAppointment method
					System.out.println(e.getMessage());
				}
				catch(InputMismatchException e){
					// catch invalid input and display a message
					System.out.println("Invalid Input");
				}
				
			}
			// the print command
			else if(cmd.equals("print")){
				try{
					// create a SimpleDateFormat for parsing the dates from the
					// prompt
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
					// get the date to print data for
					System.out.print("Please Give Date to Print (dd/mm/yyyy):");
					Date d = sdf.parse(in.nextLine()); //parse date

					// convert date to a calendar
					Calendar s = Calendar.getInstance();
					s.setTime(d);
					
					// print the appointments for the given date
					book.printAppointmentsForDay(s);
				}
				catch(ParseException e){
					// prints a message when the date given is invalid
					System.out.println(e.getMessage());
				}
				catch(InputMismatchException e){
					// catch invalid input and display a message
					System.out.println("Invalid Input");
				}
			}
			// if an invalid command is passed then display a small help message
			else if(!cmd.equals("quit") && !cmd.equals("")){
				System.out.println("Invalid Command Supplied");
				System.out.println("Available commands are: add, print and quit");
			}
		
		}while (!cmd.equals("quit")); // only exit loop when quit is passed as a command

	}
	
	/**
	 * Constructs an empty AppointmentBook
	 * <p>
	 * Pseudo Code:
	 * <code><pre>
	 * create new Array
	 * </pre></code>
	 */
	public AppointmentBook(){
		// initiate the array list 
		this.book = new ArrayList<Appointment>();
	}

	/**
	 * Adds an appointment to the appointment book
	 * <p>
	 * Pseudo Code:
	 * <code><pre>
	 * addApointment(a)
	 *   get index to insert at
	 *   if inserting at end of the book
	 *     add appointment to appointment book
	 *   else if there was no overlap
	 *     insert appointment to the appointment book at index
	 *   else
	 *     throw an exception for appointment overlap
	 * </code></pre>
	 * @param a The appointment to add
	 * @throws IllegalArgumentException When the supplied appointment overlaps
	 * an appointment already in the book.
	 */
	public void addAppointment(Appointment a){

		// find out the index to add the new appointment
		int index = getInsertIndex(a);

		// if the index is greater then or equal to the size of the book just 
		// add the appointment. no insert required for appointments at the end
		// of the appointment book.
		if(index >= this.book.size()){
			this.book.add(a);
		}
		// if the index was not -1 (-1 means the appointment overlaps) and the
		// index does not overlap the appointment it is being inserted before
		// add the appointment to the book.
		else if(index != -1 && !this.book.get(index).isOverlap(a)){
			this.book.add(index, a);
		}
		// if we get here that means that there was an overlap in the
		// appointments so throw an exception.
		else{
			throw new IllegalArgumentException("Error: Given Appointment Overlaps Another");
		}
	}

	/**
	 * Finds the index of where the appointment should be inserted into the
	 * array.
	 * <p>
	 * Pseudo Code:
	 * <code><pre>
	 * getInsertIndex(needle)
	 *   mid = 0
	 *   low = 0
	 *   high = book.length - 1
	 *   index = 0
	 *   while low less then or equal to high
	 *     mid = (low + high)/2
	 *     if book[mid] overlaps needle
	 *       return -1
	 *     if book[mid] after needle
	 *       index = mid
	 *       high = mid - 1
	 *     else if book[mid] before needle
	 *       low = mid + 1
	 *       index = low
	 *     else
	 *       return -1
	 *   end while
	 *   return index
	 * </pre></code>
	 * @param needle The appointment to search for
	 * @return The index in the array to insert the appointment or a -1 if there
	 * is an overlap.
	 */
	private int getInsertIndex(Appointment needle){
		
		int mid = 0;
		int low = 0;
		int high = this.book.size() - 1;
		int index = 0; // holds the index to be inserted at
		
		// I could have done recursion instead of a loop but tail recursion is
		// ugly. This is a basic Binary Search the only difference is that it
		// returns the index of the item ahead of the needle and returns -1 on
		// a match.
		while (low <= high) {
			mid = (low + high) / 2;
			
			// if the appointment overlaps with the current appointment return
			// -1 since the appointment is only overlapping with another
			// appointment and continuing the search is pointless.
			if(this.book.get(mid).isOverlap(needle)){
				return -1;
			}
			// if the appointment is after the current appointment
			if(this.book.get(mid).after(needle)){
				index = mid; // the index now becomes the mid point.
				high = mid - 1;
			}
			else if (this.book.get(mid).before(needle)){
				low = mid + 1;
				index = low; // the index now becomes the low value
			}
			else{
				// When the overlap between two Appointments is when the start
				// or end of one Appointment is equal to the start or end of the
				// other Appointment return -1.
				return -1;
			}
		}

		// return the index
		return index;
	}
	
	/**
	 * Prints all the appointments for the given date.
	 * <p>
	 * Pseudo Code:
	 * <code><pre>
	 * printAppointmentsForDay(c)
	 *   found = false
	 *   for appointments in appointment book
	 *     if appointment date equals given date
	 *       print appointment data
	 *       found = true
	 *   if no appointments were found
	 *     print no appointment message
	 * @param c A calendar of the date to print appointments for.
	 */
	public void printAppointmentsForDay(Calendar c){
		// appointment found flag
		boolean found = false;
		// for all appointments in the appointment book
		for(Appointment a: this.book){
			// if the date of the appointment is equal too the given date
			if(a.isDate(c)){
				// print the appointment
				System.out.println(a.toString());
				found = true; // set found to true
			}
		}
		// if no appointments were found
		if(!found){
			// print a message saying so
			System.out.println("No Appointments for that Day");
		}
	}
}
