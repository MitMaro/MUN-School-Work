/*
 *+-------------------+
 *|General Information|
 *+-------------------+
 *|
 *|Class Name: BloodDonor
 *|Creation Date: March 8, 2007
 *|Last Modified: March 16, 2007
 *|Version: 0.1 Revision: 007
 *|
 *|Programmer: Tim Oram
 *|Website: http://www.mitmaro.ca
 *|
 *|For: Memorial University of Newfoundland, http://www.mun.ca
 *+-----------------------------------------------------------+
 *
 *+-----------+
 *|Description|
 *+-----------+
 *|
 *|Records, computes and outputs information about the number 
 *|of blood donors of certain types.
 *+-----------------------------------------------------------+
 *
 *+------------+
 *|Legal Jargon|
 *+------------+
 *|
 *|Copyright 2007 Mit Maro Productions, All Rights Reserved
 *|
 *|Disclaimer:
 *|This source code is provided as-is, without any express or
 *|implied warranty. In no event will I the developer be held
 *|liable for any damages arising from the use or misuse of
 *|this or any part of this source code.
 *+-----------------------------------------------------------+
 *
 *+--------------+
 *|Instances List|
 *+--------------+
 *|
 *+-----------------------------------------------------------+
 *
 *+--------------+
 *|Constants List|
 *+--------------+
 *|
 *+-----------------------------------------------------------+
 *
 *+-----------------+
 *|Constructors List|
 *+-----------------+
 *|
 *+-----------------------------------------------------------+
 *
 *+------------+
 *|Methods List|
 *+------------+
 *|
 *|public static void main(String[] args);
 *+-----------------------------------------------------------+
 */

//Remove Before Submitting
//If I forget whoever is compiling this script please
//remove the package line.
//package A05;

/**
 *
 * @author 	Tim Oram
 * @version	0.1.007
 */

//Import scanner library
import java.util.Scanner;

public class BloodDonor {
  
  public static void main(String[] args) {
    
    //Create a instance of scanner
    Scanner s1 = new Scanner(System.in);
    
    //Strings to hold input data
    String donorName;
    String donorType;
    String donorRH;
    
    //Counters
    int aTypeCount = 0;
    int bTypeCount = 0;
    int abTypeCount = 0;
    int oTypeCount = 0;
    int combinCount = 0;
    
    //Print a little message to explain directions
    System.out.println("Blood Donor Program");
    System.out.println("Instructions:");
    System.out.println("Input donor information for each donor. Input 'LASTONE'" +
       " for donor name when all data is inputed.");
    System.out.println();
    
    //Start an indefinite loop
    while(true){
      //Loop exit is located within the loop
      
      //Ask for donor name
      System.out.println("Donor Name:");
      donorName = s1.nextLine();
      //Validate length to make sure input was correct
      if(0 == donorName.length()){
	//If invalid data was supplied, print a message and
	System.out.println("Invalid Input, try again.");
	//restart loop from beginning.
	continue;
      }
      //or if name is 'LASTONE'...
      else if(donorName.equalsIgnoreCase("lastone")){
	//...exit loop.
	break;
      }
      
      //Ask for donor blood group
      System.out.println("Donor Blood Group (Acceptable values: A, B, AB, O):");
      donorType = s1.nextLine();
      //Check for group 'a'...
      if(donorType.equalsIgnoreCase("a")){
	//...if so increase counter.
	aTypeCount += 1;
      }
      //or if type 'b'...
      else if(donorType.equalsIgnoreCase("b")){
	//...increase counter.
	bTypeCount += 1;
      }
      //or if type 'ab'...
      else if(donorType.equalsIgnoreCase("ab")){
	//...increase counter.
	abTypeCount += 1;
      }
      //or if type 'o'...
      else if(donorType.equalsIgnoreCase("o")){
	//...increase counter.
	oTypeCount += 1;
      }
      //If invalid input was supplied...
      else{
	//...print a friendly error message and
	System.out.println("Invalid Input, try again.");
	//restart loop.
	continue;
      }
      
      //Ask for donor RH Factor
      System.out.println("Donor RH Factor (Acceptable values: +, -):");
      donorRH = s1.nextLine();
      //if ivalid input was given...
      if(!(donorRH.equalsIgnoreCase("-") ||
	   donorRH.equalsIgnoreCase("+"))){
	//...print a friendly error message and
	System.out.println("Invalid Input, try again.");
	//restart the loop
	continue;
      }
      
      
      //If donor type is a or ab and RH factor is - then...
      if(
	 (donorType.equalsIgnoreCase("a") ||
	 donorType.equalsIgnoreCase("ab")) &&
	 donorRH.equals("-")){
	//...increase counter.
	combinCount += 1;
      }
      
    }
    
    //Print the data requested
    System.out.println();
    System.out.println("Number of A donors:  " + aTypeCount);
    System.out.println("Number of B donors:  " + bTypeCount);
    System.out.println("Number of AB donors: " + abTypeCount);
    System.out.println("Number of O donors:  " + oTypeCount);
    System.out.println("Number of donors with a blood" +
       " group of A or AB and a negative RH factor:" + combinCount);   
  }
}