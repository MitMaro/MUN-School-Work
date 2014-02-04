/*************************************************************************
 * Memorial University of Newfoundland
 * Engineering 1020 Structured Programming
 * Assignment 4
 * 
 * Author: Tim Oram
 * Student #: #########
 * E-mail (@mun.ca): toram@mun.ca
 * Lab section: 02
 * Due date: 2011.01.27
 * 
 * assign4.cpp -- moves the robot around several laps of a track
 * ***********************************************************************/

#include <pololu/orangutan.h>
#include <pololu/3pi.h>
#include "util3pi.h"
#include "assign4.h"

namespace {
   unsigned const motor_speed = 60;
   unsigned const turn_coefficient = 0.05;
   unsigned const poll_delay = 100;
};

/** readLineSensors ******************************************
 * Read the IR line sensors and return an interpreted result.
 *
 * Sensors are numbered 0 - 4 starting at the left
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

void followLineOneLap() {
	
	int sensorValue;
	
	int adjustment;

	while (sensorValue > 0 && sensorValue < 4000) {
		
		sensorValue = readLineSensors();
		
		adjustment = (int)((sensorValue - 2000) * turn_coefficient);
		
		set_motors(motor_speed + adjustment, -motor_speed - adjustment);
		
		delay_ms(poll_delay);
	}
	
	while (sensorValue < 0 || sensorValue > 4000) {
		set_motors(motor_speed, motor_speed);
		delay_ms(poll_delay);
	}
	set_motors(0, 0);
}



void followLineLaps(int count) {

	int i;

	for (i = 0; i < count; ++i) {
		followLineOneLap();
		play(beep_button_b);
	}

}

