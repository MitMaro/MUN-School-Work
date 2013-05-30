/*************************************************************************
 * Memorial University of Newfoundland
 * Engineering 1020 Introduction to Programming
 * Assignment 2 - driver file
 * 
 * Author: Dennis Peters
 * Lab section: nn
 * Due date: 2013.06.03
 * 
 * ***********************************************************************/

#include <pololu/orangutan.h>
#include <pololu/3pi.h>

#include "util3pi.h"
#include "assign2.h"


int main() {
	displayWelcome();
	displayBattery();
	clear();
	print("Press B");
	lcd_goto_xy(0,1);
	print("for box.");
	waitForButton(BUTTON_B, beep_button_b);
	play_from_program_space(go);
	makeBox();
	play_from_program_space(success);
	clear();
	print("Done!");
	while (true); // prevent exit from main
	return 0; // dead code
}



