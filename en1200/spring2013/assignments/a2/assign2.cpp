/*************************************************************************
 * Memorial University of Newfoundland
 * Engineering 1020 Structured Programming
 * Assignment 2
 * 
 * Author: Tim Oram
 * Student #: #########
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

// the speed of the robot
#define SPEED 50

// how long to pause the program for the motors to move the robot one inch
#define WAIT_PER_INCH 124

// the size of the box in inches
#define BOX_WIDTH 24

// the distance to turn the robot to do a 90 degree angle
#define TURN_DISTANCE 2.70

/** makeBox **************************************************************
 * Makes the robot move in a 2'x2' square
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
	// one motor forward, one motor in reverse
	set_motors(SPEED, -SPEED);
	delay_ms(WAIT_PER_INCH * TURN_DISTANCE);
	set_motors(0,0);
}

/** driveAhead ************************************************************
 * Drive Ahead to the specified distance given in inches
 * @params distance -- the distance in inches
 * ***********************************************************************/
void driveAhead(double distance) {
	// motors forward for some time
	set_motors(SPEED, SPEED);
	delay_ms(WAIT_PER_INCH * distance);
	set_motors(0,0);
	// pause for a fraction of a second before continuing to smooth out the run
	delay_ms(250);
}
