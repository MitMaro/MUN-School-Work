/*
+-------------------------------------------------+
|Computer Science 1710                            |
|Assignment 01 - Question 06                      |
|Creation Date: 01/26/07                          |
|Last Modified: 01/26/07                          |
|Version: 0.1 Revision: 001                       |
|Programmer: Tim Oram                             |
|Memorial University of Newfoundland              |
+-------------------------------------------------+
|Copyright 2007 Mit Maro Productions              |
|All Rights Reserved                              |
|http://www.mitmaro.ca                            |
+-------------------------------------------------+
*/

import java.util.GregorianCalendar;
import java.util.Calendar;

public class CS1710_a01_q06{
	public static void main(String[] args){
		// Days to add
		final int DAYS_TO_ADD = 100;
		
		//Stores Calander Info
		int intDay;
		int intMonth;
		int intYear;
		int intWeekday;
		
		// Create calendar with todays date
		GregorianCalendar gcCal = new GregorianCalendar();
		
		// Add days
		gcCal.add(Calendar.DAY_OF_MONTH, DAYS_TO_ADD);
		
		intDay =  gcCal.get(Calendar.DAY_OF_MONTH);
		intYear =  gcCal.get(Calendar.YEAR);
		intMonth =  gcCal.get(Calendar.MONTH);
		intWeekday =  gcCal.get(Calendar.DAY_OF_WEEK);
		
		// Print out result of adding days
		System.out.println(DAYS_TO_ADD + " days from today it will be: " + intMonth + "/" + intDay + "/"
		+ intYear);
		System.out.println("The day of week will be: " + intWeekday);
	}
}