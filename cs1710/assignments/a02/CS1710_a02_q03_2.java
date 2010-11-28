/*
+-------------------+
|General Information|
+-------------------+
|
|Computer Science 1710
|Assignment 02 - Question 03-2
|Creation Date: 01/29/07
|Last Modified: 02/02/07
|Version: 0.1 Revision: 005
|
|Programmer: Tim Oram
|Website: http://www.mitmaro.ca
|
|For: Memorial University of Newfoundland, http://www.mun.ca
+-----------------------------------------------------------+

+-----------+
|Discription|
+-----------+
|
|Test program for the square class.
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
Test class for the Square Class
@author 	Tim Oram
@version	0.1.005
*/
public class CS1710_a02_q03_2{
		
	/* Instance Fields */
	
	/*...*/
	
	
	/* Constants */
	
	/*...*/
	
    /**
    Program Entry Point
    */
	public static void main(String[] args){
	    
	    // Create a new square
	    Square sq1 = new Square(8);
	    
	    //Print out the information on the square
	    System.out.println("The Square has a width of: " + sq1.getWidth());
	    System.out.println("The Square has an area of: " + sq1.getArea());
	    System.out.println("The Square has a perimeter of: " + sq1.getPerimeter());
	    
	}
}

// TEMPLATE VERSION 0.3 (02-07-07)