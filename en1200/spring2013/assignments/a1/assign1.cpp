/*************************************************************************
 * Memorial University of Newfoundland
 * Engineering 1020 Structured Programming
 * Assignment #1
 * 
 * Author: Obinna Nelson Okonkwo
 * Student #: 201010808
 * E-mail (@mun.ca): ono754@mun.ca
 * Author: Tim Oram
 * Student #: 200529220
 * E-mail (@mun.ca): toram@mun.ca
 * Lab section: 02
 * Due date: 2011.01.27
 * 
 * assign1.cpp -- function to display the battery voltage as a percent
 * ***********************************************************************/

#include <pololu/orangutan.h>
#include <pololu/3pi.h>

#include "assign1.h"

/** displayBattery *******************************************************
 * @params - none
 * @modifies cout -- The battery voltage percentage is appended
 * ***********************************************************************/

#define MIN_VOLTS 3.5
#define MAX_VOLTS 5.5

void displayBattery() {
	
	// get the millivolt value from the 3pi, convert to floating volts value
	double volts = read_battery_millivolts() / 1000.0;
	
	// calculate the percent decimal voltage
	double percent = (volts - MIN_VOLTS) / (MAX_VOLTS  - MIN_VOLTS);
	
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
