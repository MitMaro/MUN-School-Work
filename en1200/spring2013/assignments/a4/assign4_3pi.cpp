/* **********************************************
 * Memorial University of Newfoundland
 * Engineering 1020 Introduction to Programming
 *
 * assign4_3pi.cpp
 * Main driver code for Assignment 4.
 *
 *  Created on: 2013-06-11
 *      Author: dpeters
 */

#include <pololu/orangutan.h>
#include <pololu/3pi.h>
#include "util3pi.h"
#include "assign4.h"

void setup();

int main() {
	setup();
	displayWelcome();
	displayBattery();
	lineCalibration();

	followLineLaps(4);

	play_from_program_space(success);
	clear();
	print("Done.");
	while (true); // prevent exit from main
	return 0; // dead code
}


/** setup **********************************************************
 * All the initialization goes here. It should be called once when
 * the program starts up.
 *
 */
void setup() {
	pololu_3pi_init(2000); // library function that initializes sensors
	load_custom_characters();
}
