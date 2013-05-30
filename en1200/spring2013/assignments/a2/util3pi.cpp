/* **********************************************
 * Memorial University of Newfoundland
 * Engineering 1020 Introduction to Programming
 * 
 * util3pi.cpp
 * Utility functions for the 3pi.
 *
 *  Created on: 2013-05-27
 *      Author: dpeters
 */

#include <pololu/orangutan.h>
#include <pololu/3pi.h>
#include "util3pi.h"

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

/** displayBattery ****************************************
 * Output battery level, wait for button B
 * @modifies lcd display has battery level output.
 */
void displayBattery() {
	int bat = read_battery_millivolts();
	const int LOW_BATTERY = 3500;
	const int FULL_BATTERY = 6000;
	double batPer = (double)(bat - LOW_BATTERY)/(FULL_BATTERY-LOW_BATTERY)*100;

	clear();
	print_long(batPer);
	print("%");
	lcd_goto_xy(0,1);
	print("Press B");
	waitForButton(BUTTON_B, beep_button_b);
}

/** waitForButton **********************************************
 * Pause until a button is pressed.
 * @params button -- identifier of button to wait for
 *         tune -- sequence of notes played when button is pressed
 */
void waitForButton(unsigned char button, const char *tune) {
	while(!button_is_pressed(button)) {
		delay_ms(100);
	}
	play_from_program_space(tune);
	while (button_is_pressed(button)) ; // wait for button up
	delay_ms(200);
}
