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

void display_readings(const unsigned int calibrated_values[]);

// Data for generating the characters used in load_custom_characters
// and display_readings.  By reading levels[] starting at various
// offsets, we can generate all of the 7 extra characters needed for a
// bargraph.  This is also stored in program space.
const char levels[] PROGMEM = {
	0b00000,
	0b00000,
	0b00000,
	0b00000,
	0b00000,
	0b00000,
	0b00000,
	0b11111,
	0b11111,
	0b11111,
	0b11111,
	0b11111,
	0b11111,
	0b11111
};

/** displayWelcome ******************************************
 * Output welcome message, play tune and delay 2s.
 * @modifies - lcd displays welcome message, tune is played
 */
void displayWelcome() {
	clear();
	print_from_program_space(welcome_line1);
	lcd_goto_xy(0,1);
	print_from_program_space(welcome_line2);
	//play_from_program_space(welcome);
	delay_ms(2000);
}

/** displayBattery ****************************************
 * Output battery level, wait for button B
 * @modifies lcd display has battery level output.
 */
void displayBattery() {
	int bat;
	const int LOW_BATTERY = 3500;
	const int FULL_BATTERY = 6000;
	double batPer;

	while (!button_is_pressed(BUTTON_B)) {// as long as B is not pressed
		bat = read_battery_millivolts();// read battery voltage
		batPer = (double)(bat - LOW_BATTERY)/(FULL_BATTERY-LOW_BATTERY)*100; // compute percent of full value
		clear();
		print_long(batPer); // display value
		print("%");
		lcd_goto_xy(0,1);
		print("Press B");
		delay_ms(100);	// wait a little bit
	}
	//play_from_program_space(beep_button_b);
	while (button_is_pressed(BUTTON_B)) ; // wait for button up
	delay_ms(200);
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
	//play_from_program_space(tune);
	while (button_is_pressed(button)) ; // wait for button up
	delay_ms(200);
}

/**lineCalibration*****************************************
* @descrip: a function to calibrate the sensors and display
* the results in the form of a bar graph. It waits for a button
* to be pressed.
* @param: none
* @returns: BUTTON_A, BUTTON_B or BUTTON_C depending on which was
*           pressed after the calibration
*
***********************************************************/
unsigned char lineCalibration() {

	const int TURN_SPEED = 40; // motor speed for turning
	const int TURN_STEPS = 20; // number of samples in a half turn
	const int SAMPLE_DELAY = 20; // ms between samples
	unsigned char button; // which button is pressed at the end

	set_motors(-TURN_SPEED, TURN_SPEED); // start turning left
	for (int i = 0; i < TURN_STEPS; i++) {// some number of times
		calibrate_line_sensors(IR_EMITTERS_ON);
		delay_ms(SAMPLE_DELAY);           // 		wait 20ms
	}
	set_motors(TURN_SPEED, -TURN_SPEED); // start turning right
	for (int i = 0; i < 2*TURN_STEPS; i++) {// twice as many times
		calibrate_line_sensors(IR_EMITTERS_ON);
		delay_ms(SAMPLE_DELAY);           // 		wait 20ms
	}
	set_motors(-TURN_SPEED, TURN_SPEED); // start turning left
	for (int i = 0; i < TURN_STEPS; i++) {// some number of times
		calibrate_line_sensors(IR_EMITTERS_ON);
		delay_ms(SAMPLE_DELAY);           // 		wait 20ms
	}
	set_motors(0,0);

	// Display calibrated values as a bar graph.
	unsigned int sensor[5]; // place to store sensor readings
	while ((button = button_is_pressed(ANY_BUTTON)) == 0) {// as long as a button is not pressed
		unsigned int position = read_line(sensor, IR_EMITTERS_ON); //    read the sensors
		clear();
		print_long(position); //    display the value on the top line
		display_readings(sensor); 	//    display the bar graph on the bottom line
		delay_ms(100);             //    wait a bit
	}
	//play_from_program_space(beep_button_b); // beep for B button
	while (button_is_pressed(button)) ;   // empty loop - wait until button released
	delay_ms(200); // wait a bit more
	return button;
}


/** display_readings ****************************************
 * displays the sensor readings using a bar graph
 *
 * @params calibrated_values -- array of 5 sensor readings @pre [0,1000]
 */
void display_readings(const unsigned int calibrated_values[])
{
	const char display_characters[10] = {' ',0,0,1,2,3,4,5,6,255};

	lcd_goto_xy(0,1);
	for (int s = 0; s < 5; s++) {
		int scaled_value = calibrated_values[s]/101;
		print_character(display_characters[scaled_value]);
	}
}

/** readLineSensors ******************************************
 * Read the IR line sensors and return an interpreted result.
 *
 * Sensors are numbered 0 - 4 starting at the left. The interpretation
 * assumes that the robot is following a 3/4" wide black line with no
 * sharp turns and no branches.
 *
 * @returns 0           -- line sensed under leftmost sensor (0) only
 *                         or no line detected
 *          1 - 999     -- line sensed under left two sensors (0 and 1)
 *          1000 - 1999 -- line sensed under sensors 1 and 2
 *          2000        -- line sensed under sensors 1, 2 and 3
 *          2001 - 2999 -- line sensed under sensors 2 and 3
 *          3000 - 3999 -- line sensed under sensors 3 and 4
 *          4000        -- line sensed under rightmost sensor (4) only
 *                         or no line detected
 */
int readLineSensors() {
	unsigned int sensors[5];
	int result = read_line(sensors, IR_EMITTERS_ON);
	display_readings(sensors);
	return result;
}

/** readSensorsBranches ******************************************
 * Read the IR line sensors and return an interpreted result.
 *
 * Sensors are numbered 0 - 4 starting at the left. This version
 * returns a value as for readLineSensors(), but also sets isLeft
 * and isRight to indicate if there is a line under sensor 0 (isLeft) and/or
 * sensor 4 (isRight). If either isLeft or isRight is true then the value returned
 * shouldn't be trusted for steering since
 *
 * @modifies isLeft is true if and only if there is a line detected directly
 *               under the left sensor (0)
 *           isRight is true if and only if there is a line detected directly
 *               under the right sensor (4)
 * @returns if !(isLeft || isRight) then the value can be interpreted as follows:
 *          0           -- line sensed under leftmost sensor (0) only
 *                         or no line detected
 *          1 - 999     -- line sensed under left two sensors (0 and 1)
 *          1000 - 1999 -- line sensed under sensors 1 and 2
 *          2000        -- line sensed under sensors 1, 2 and 3
 *          2001 - 2999 -- line sensed under sensors 2 and 3
 *          3000 - 3999 -- line sensed under sensors 3 and 4
 *          4000        -- line sensed under rightmost sensor (4) only
 *                         or no line detected
 */
int readSensorsBranches(bool& isLeft, bool& isRight) {
	unsigned int sensors[5];
	int result = read_line(sensors, IR_EMITTERS_ON);
	isLeft = sensors[0] > 500;
	isRight = sensors[4] > 500;
	display_readings(sensors);
	return result;
}

// This function loads custom characters into the LCD.  Up to 8
// characters can be loaded; we use them for 7 levels of a bar graph.
void load_custom_characters()
{
	lcd_load_custom_character(levels+0,0); // no offset, e.g. one bar
	lcd_load_custom_character(levels+1,1); // two bars
	lcd_load_custom_character(levels+2,2); // etc...
	lcd_load_custom_character(levels+3,3);
	lcd_load_custom_character(levels+4,4);
	lcd_load_custom_character(levels+5,5);
	lcd_load_custom_character(levels+6,6);
	clear(); // the LCD must be cleared for the characters to take effect
}
