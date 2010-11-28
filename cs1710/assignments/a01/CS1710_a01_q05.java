/*
+-------------------------------------------------+
|Computer Science 1710                            |
|Assignment 01 - Question 05                      |
|Creation Date: 01/26/07                          |
|Last Modified: 01/26/07                          |
|Version: 0.1 Revision: 003                       |
|Programmer: Tim Oram                             |
|Memorial University of Newfoundland              |
+-------------------------------------------------+
|Copyright 2007 Mit Maro Productions              |
|All Rights Reserved                              |
|http://www.mitmaro.ca                            |
+-------------------------------------------------+
*/

// Import Rectanlge Class
import java.awt.Rectangle;

public class CS1710_a01_q05{
	public static void main(String[] args){
		// Create Rectangle
		Rectangle r1 = new Rectangle(0,0,17,10);
		
		// Stores the area 
		double dblArea = 0;
		
		// Calculate Area
		dblArea = r1.getWidth() * r1.getHeight();
		
		// Print out the Area
		System.out.println("The area is: " + dblArea);
	} 
}