/*************************************************************************
 * Memorial University of Newfoundland
 * Engineering 1020 Structured Programming
 * Assignment 1
 * 
 * Author: 
 * Student #: 
 * E-mail (@mun.ca): 
 * Author: Tim Oram
 * Student #: 200529220
 * E-mail (@mun.ca): toram@mun.ca
 * Lab section: 02
 * Due date: 2011.01.27
 * 
 * assign1.cpp -- function to display the battery voltage as a percent
 * ***********************************************************************/
#include <iostream>
using namespace std;

#include "assign1.h"

/** displayBattery *******************************************************
 * @params - none
 * @modifies cout -- The battery voltage percentage is appended
 * ***********************************************************************/

void displayBattery() {
	
	// get the millivolt value from the 3pi
	int bat = read_battery_millivolts();
	
	// calculate the percentage of the input from 3.5 - 5.5
	// convert voltage to floating point number
	double percent = (inp / 1000.0);
	
	// calculate the percent decimal voltage
	percent = (percent - 3.5) / (5.5  - 3.5);
	
	// convert to a percentage
	percent *= 100;
	
	// display the percentage on the screen
	clear();
	print_long(percent);
	print("%");
	lcd_goto_xy(0,1);
	print("Press B");
	
	// shall we wait for B?
	waitForB();
}
