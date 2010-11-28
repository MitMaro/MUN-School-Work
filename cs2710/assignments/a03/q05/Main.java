/*
  CS 2710 (Fall 2008), Assignment #3, Question #5
          File Name: Main.java
       Student Name: Tim Oram
              MUN #: #########
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


// tests the Bank class
public class Main {

	public static void main(String[] args) {
		
		// create a new bank and a couple other things to read files
		Bank myBank = new Bank();
		String filename = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		// keep doing this till quit or file given
		do{
			filename = "";
			try{
				// get filename from user
				System.out.print("Input Filename: ");
				filename = in.readLine();
			}
			// catch a unlikely error
			catch(IOException ex){
				System.out.println("Error reading from console");
			}
		// also loops again if readfile failed
		} while(filename.equals("") || (!filename.equals("quit") && !myBank.readFile(filename)));
		
		// print the greatest balance 
		myBank.printGreatestBalance();
		

	}

}
