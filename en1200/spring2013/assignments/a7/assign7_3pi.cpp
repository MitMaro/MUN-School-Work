/* **********************************************
 * Memorial University of Newfoundland
 * Engineering 1020 Introduction to Programming
 *
 * assign7_3pi.cpp
 * Main driver code for Assignment 7.
 *
 *  Created on: 2013-07-03
 *      Author: dpeters
 */

#include <pololu/orangutan.h>
#include <pololu/3pi.h>
#include "util3pi.h"
#include "assign7.h"

void setup();

int main() {
	setup();
	displayWelcome();
	displayBattery();
	unsigned char b = lineCalibration();

	int numTurns = 0;
	const int MAX_TURNS = 32;
	char turns[MAX_TURNS+1];

	if (b == BUTTON_B) {
		numTurns = solveMaze(turns, MAX_TURNS);

		clear();
		if (numTurns <= MAX_TURNS) {
			play_from_program_space(success);

			turns[numTurns] = '\0';
			print(turns);
		} else {
			print_long(numTurns);
			print(" turns.");
		}
	}
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
