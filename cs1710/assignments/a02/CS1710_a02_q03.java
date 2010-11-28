/*
+-------------------+
|General Information|
+-------------------+
|
|Computer Science 1710
|Assignment 02 - Question 03-1
|Creation Date: 01/29/07
|Last Modified: 02/02/07
|Version: 0.1 Revision: 012
|
|Programmer: Tim Oram
|Website: http://www.mitmaro.ca
|
|For: Memorial University of Newfoundland, http://www.mun.ca
+-----------------------------------------------------------+

+-----------+
|Description|
+-----------+
|
|Test Application for car class.
+-----------------------------------------------------------+

+------------+
|Legal Jargon|
+------------+
|
|Copyright 2007 Mit Maro Productions, All Rights Reserved
|
|Disclaimer:
|This source code is provided as-is, without any express or
|implied warranty. In no event will I the developer be held
|liable for any damages arising from the use or misuse of
|this or any part of this source code.
+-----------------------------------------------------------+

+--------------+
|Instances List|
+--------------+
|
|
+-----------------------------------------------------------+

+--------------+
|Constants List|
+--------------+
|
|
+-----------------------------------------------------------+

+------------+
|Methods List|
+------------+
|
|public static void main(String[] args)
+-----------------------------------------------------------+
*/

/**
Test class for the Car Class
@author 	Tim Oram
@version	0.1.012
*/

public class CS1710_a02_q03{
	
	/* Instance Fields */
	
	/*...*/
	
	
	/* Constants */
	
	/*...*/
	
    /**
    Program Entry Point
    */
	public static void main(String[] args){
		Car c1 = new Car(56.0); // Create a car
		
		c1.addGas(29); //Fuel up
		
		// Print a blank line to separate the output paragraph from the other console test
		System.out.println();
		
		// Print out some information
		System.out.println("The car starts with " + c1.getGas() + " liters of gas.");
		
		c1.drive(100); //Drive a bit
		
		// Print out information.
		// I converted some values to integers so they are easier to read in the output
		// if I knew more about Java string formatting I would have used that.
		System.out.println("The car has driven a distance of " + c1.getDistTraveled() + " km ");
		System.out.println("and has burnt approximately " + (int)c1.getGasBurnt() + " liters of gas.");
		System.out.println("You have about " + (int)c1.getGas() + " liters of gas remaining and you ");
		System.out.println("can travel " + (int)c1.getDistPossible() + " km more.");
		System.out.println();
		
	}
}

// TEMPLATE VERSION 0.3 (02-07-07)