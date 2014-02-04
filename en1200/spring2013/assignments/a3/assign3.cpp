/*************************************************************************
 * Memorial University of Newfoundland
 * Engineering 1020 Structured Programming
 * Assignment 3
 * 
 * Author: Tim Oram
 * Student #: #########
 * E-mail (@mun.ca): toram@mun.ca
 * Lab section: 02
 * Due date: 2013.07.10
 * 
 * assign3.cpp -- A function that returns the digit value corresponding to
 * the letter passed based on the encoding on a telephone handset and a
 * function that returns the day number (1 to 366) in a year for a date
 * that is provided as input data. 
 * ***********************************************************************/


/** leapYear *************************************************************
 * Determines if a year is a leap year or not
 * 
 * @params year -- the year to check for leap year
 * @returns -- true if the year is a leap year, else false
 * ***********************************************************************/
bool leapYear(int year) {
	
	// this is terribly inefficient and can be done better
	// with some bitwise operators and no if statements
	// but at the cost of readability
	
	// 400 is also divisible by 4
	if (year % 400 == 0) {
		return true;
	}
	
	// any year not caught about that is divisible by 100
	if (year % 100 == 0) {
		return false;
	}
	
	// any remaining years divisible by 4 are leap, else not
	return ((year % 4) == 0);
	
}


/** phoneKey *************************************************************
 * Returns the digit value corresponding to the letter passed based on the
 * encoding on a telephone handset
 * 
 * @params l -- the letter to check
 * @returns -- A integer from 2 to 9 for a valid letter, or -1 for invalid input
 * ***********************************************************************/
int phoneKey(char l) {
	
	// convert upper case letters to lower case
	if (l >= 'A' && l <= 'Z') {
		l += ('a' - 'A');
	}
	
	// return -1 for values that are not letters
	if (l < 'a' || l > 'z') {
		return -1;
	}
	
	// wxyz match
	if (l >= 'w') {
		return 9;
	}
	
	// tuv match
	if (l >= 't') {
		return 8;
	}
	
	// pqrs match
	if (l >= 'p') {
		return 7;
	}
	
	// everything else we do some math
	return ((l - 'a') / 3 + 2);
}

/** dayNumber ************************************************************
 * Returns the day number (1 to 366) in a year for a date
 * 
 * @params day -- The day of the month
 *         month -- The month number
 *         year -- The year as a integer
 * @returns -- The day of the year for the given date
 * ***********************************************************************/
int dayNumber(int day, int month, int year) {
	
	// number of days in each month indexed by month number
	static int monthDays[] = {
		0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
	};
	
	int i;
	
	// add the number of days into the month
	int totalDays = day;
	
	// add days of month up to but not including the current month
	for (i = 1; i < month; ++i) {
		totalDays += monthDays[i];
	}
	
	// add an extra day for a leap year assuming the month is after February
	if (month > 2 && leapYear(year)) {
		totalDays++;
	}
	
	return totalDays;
}
