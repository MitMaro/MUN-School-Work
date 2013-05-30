/*************************************************************************
 * Memorial University of Newfoundland
 * Engineering 1020 Structured Programming
 * Assignment 2
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
 * assign2.cpp -- moves the robot in a square of a set distance
 * ***********************************************************************/

#include <pololu/orangutan.h>
#include <pololu/3pi.h>

#include "util3pi.h"
#include "assign2.h"

#define SPEED 255

#define WAIT_PER_INCH 254


#define BOX_WIDTH 24
#define TURN_DISTANCE 5.8

/** makeBox **************************************************************
 * Makes the robot move in a 2x2 square
 * ***********************************************************************/
void makeBox() {
	driveAhead(BOX_WIDTH);
	turnRight();
	driveAhead(BOX_WIDTH);
	turnRight();
	driveAhead(BOX_WIDTH);
	turnRight();
	driveAhead(BOX_WIDTH);
	turnRight();
}

/** turnRight ************************************************************
 * Makes the robot do a 90 degree turn
 * ***********************************************************************/
void turnRight() {
	set_motor(SPEED, -SPEED);
	delay_ms(WAIT_PER_INCH * TURN_DISTANCE)
}

/** driveAhead ************************************************************
 * Drive Ahead
 * ***********************************************************************/
void driveAhead(double distance) {
	set_motor(SPEED, SPEED);
	delay_ms(WAIT_PER_INCH * distance);
}
