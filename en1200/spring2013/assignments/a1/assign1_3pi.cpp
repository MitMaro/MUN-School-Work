/*************************************************************************
 * Memorial University of Newfoundland
 * Engineering 1020 Introduction to Programming
 * Assignment 1 - driver file
 * 
 * Author: Dennis Peters
 * Lab section: nn
 * Due date: 2013.05.27
 * 
 * ***********************************************************************/

#include <pololu/orangutan.h>
#include <pololu/3pi.h>

#include "assign1.h"

// A couple of simple tunes, stored in program space.
const char welcome[] PROGMEM = ">g32>>c32";
const char beep_button_a[] PROGMEM = "!c32";
const char beep_button_b[] PROGMEM = "!e32";
const char beep_button_c[] PROGMEM = "!g32";
const char go[] PROGMEM = "L16 cdegreg4";

// Some fixed messages for output.
const char welcome_line1[] PROGMEM = " Pololu";
const char welcome_line2[] PROGMEM = "3\xf7 Robot"; // \xf7 is a greek pi

void displayWelcome();

int main() {
	displayWelcome();
	displayBattery();
	clear();
	print("Hello");
	lcd_goto_xy(0,1);
	print("World");
	while (true); // prevent exit from main
	return 0; // dead code
}

/** displayWelcome ******************************************
 * Output welcome message, play tune and delay 2s.
 * @modifies - lcd displays welcome message, tune is played
 */
void displayWelcome() {
	clear();
	print_from_program_space(welcome_line1);
	lcd_goto_xy(0,1);
	print_from_program_space(welcome_line2);
	play_from_program_space(welcome);
	delay_ms(2000);
}


/** waitForB **********************************************
 * Pause until B is pressed.
 */
void waitForB() {
	while(!button_is_pressed(BUTTON_B)) {
		delay_ms(100);
	}
	play_from_program_space(beep_button_b);
	while (button_is_pressed(BUTTON_B)) ; // wait for button up
	delay_ms(200);
}
