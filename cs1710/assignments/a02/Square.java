/*
+-------------------+
|General Information|
+-------------------+
|
|Computer Science 1710
|Class Name: Square
|Creation Date: 01/29/07
|Last Modified: 02/07/07
|Version: 0.1 Revision: 007
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
|A class that describes a square when given a width of the
|sides. Returns the area and perimeter of the square.
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
|private int intSideWidth
+-----------------------------------------------------------+

+--------------+
|Constants List|
+--------------+
|
|public static final int SQUARE_NUMBER_OF_SIDES = 4;
+-----------------------------------------------------------+

+-----------------+
|Constructors List|
+-----------------+
|
|public Square(int intWidth)
+-----------------------------------------------------------+

+------------+
|Methods List|
+------------+
|
|public int getArea()
|public int getPerimeter()
|public int getWidth()
+-----------------------------------------------------------+
*/
/**
Describes a square when given a width of the sides. Returns the area and perimeter of the square.
@author 	Tim Oram
@version	0.1.007
*/

public class Square{
	
	
	/* Instance Fields */
	
	/** Holds the width of the sides of the square. */
    private int intSideWidth;	
	
	/*...*/
	
	
	/* Constants */
	
	/** Number of sides a square has */
	public static final int SQUARE_NUMBER_OF_SIDES = 4;
	
	/*...*/
	
	
	/**
	Constructor for Square Class.
    @param intWidth The width of the square's sides
    */
	public Square(int intWidth){
            intSideWidth = intWidth;     
	}
	
    /**
    Gets the area of the square.
    @return The area of the square
    */
	public int getArea(){
            return intSideWidth * intSideWidth;
	}
	
	/**
	Gets the perimeter of the square
	@return The perimeter of the square
	*/
	public int getPerimeter(){
		return intSideWidth * SQUARE_NUMBER_OF_SIDES;
	}
	
	/**
	Gets the width of the square.
	@return the width of the square
	*/
	public int getWidth(){
		return intSideWidth;
	}
}

// TEMPLATE VERSION 0.3 (02-07-07)