/*************************************************************************
 * Memorial University of Newfoundland
 * Engineering 1020 Structured Programming
 * Assignment 8
 *
 * Author: Tim Oram
 * Student #: #########
 * E-mail (@mun.ca): toram@mun.ca
 * Lab section: 02
 * Due date: 2013.07.26
 *
 * assign8.cpp -- makes the robot follow a track keeping track of turns
 * ***********************************************************************/

#include <pololu/orangutan.h>
#include <pololu/3pi.h>
#include "util3pi.h"
#include "assign8.h"
#include <stdlib.h>

// global constants
namespace {
	unsigned const motor_speed = 30; // the max speed of the motors
	signed const sensor_threshold = 1000; // what +/- range from center is considered on the line
	unsigned const left_sensor = 0; // left sensor index
	unsigned const center_sensor = 2; // center sensor index
	unsigned const right_sensor = 4; // right sensor index
	// constants for the various turns
	char const turn_dead_end = 'A';
	char const turn_right = 'R';
	char const turn_left = 'L';
	char const turn_straight = 'S';
};

// set to true when at target
bool done = false;


/** makeTurn *******************************************
 * makes a turn either left or right
 *
 * @params left -- when true will turn left, else turn right
 */
void makeTurn(bool leftTurn) {

	unsigned int sensors[5];

	int sensorValue = 0;

	// turn one way for a left turn, other way for right
	if (leftTurn) {
		set_motors(-motor_speed, motor_speed);
	}
	else {
		set_motors(motor_speed, -motor_speed);
	}

	// take an initial reading of the sensors
	// could use a do..while loop here
	read_line(sensors, IR_EMITTERS_ON);

	// keep turning until we are off the line
	while (sensors[center_sensor] > 500) {
		read_line(sensors, IR_EMITTERS_ON);
	}

	// give it a small chance to ensure it is off the line
	delay_ms(10);

	// keep turning until we find a line again
	while (sensors[center_sensor] < 500) {
		read_line(sensors, IR_EMITTERS_ON);
	}
	play_from_program_space(beep_button_a);

	// keep turning until we center on the line again, fixes bug with 3pi
	// being unable to center before hitting an intersection if the
	// intersection is shortly after a dead end
	while (sensorValue > 2050 || sensorValue < 1950) {
		sensorValue = read_line(sensors, IR_EMITTERS_ON);
	}

	// all stop captain
	set_motors(0, 0);

}


/** solveMazeBest **********************************
 * Follows the left side of a maze until reaching the target while
 * recording the route taken while simplifying.
 *
 * @params route -- an array that is filled with the route taken
 *         maxLen -- the maximum number of intersections that can be recorded
 *
 * @returns -- the number of items in route
 */
int solveMazeBest(char route[], int maxLen) {
	int index = 0;

	// passed to follow segment to record if a left, right or straight intersection happened
	bool left = true;
	bool right = true;
	bool straight = true;

	// keep going while there is a turn or we can go straight and we are not on the target
	while (true) {

		// just follow the line until something interesting happens
		followSegment(left, right, straight);

		// if we are finished the maze we break out of the loop
		if (done) {
			break;
		}

		// stop when we reach max turns
		if (index == maxLen) {
			break;
		}

		// left turn found, record and make the turn
		if (left) {
			route[index] = turn_left;
			index++;
			turnLeft();
		}
		// no left but a straight, record and move ahead a bit to get away from the intersection
		else if (straight) {
			route[index] = straight;
			set_motors(motor_speed, motor_speed);
			delay_ms(200);
			set_motors(0,0);
			index++;
		}
		// right turn, record and make the turn
		else if (right) {
			route[index] = turn_right;
			index++;
			turnRight();
		}
		// dead end do a 180
		else {
			route[index] = turn_dead_end;
			index++;
			// move forward a fair bit before making the turn
			// this fixes a problem with a dead end being found shortly after
			// making a turn
			set_motors(motor_speed, motor_speed);
			delay_ms(425);

			// make a turn
			set_motors(0,0);
			makeTurn(false);
		}

		// remove any dead ends if found
		index = trimRoute(route, index);

	}

	// move forward a tiny bit at the end and stop
	set_motors(motor_speed, motor_speed);
	delay_ms(200);
	set_motors(0,0);

	return index;
}

/** isTarget *******************************************
 * Checks if the 3pi is at the target location
 *
 * @returns -- true if at target, else false
 */
bool isTarget() {

	int i;
	unsigned int sensors[5];

	// get a reading of what's under the 3pi
	read_line(sensors, IR_EMITTERS_ON);

	// for each sensor
	for (i = 0; i < 5; ++i) {
		// if sensor is not over a black area break out
		if (sensors[i] <= 500) {
			return false;
		}
	}

	// move forward enough to clear the tape in a T or cross intersection
	set_motors(motor_speed, motor_speed);
	delay_ms(150);
	set_motors(0, 0);

	// take another reading
	read_line(sensors, IR_EMITTERS_ON);

	// for each sensor
	for (i = 0; i < 5; ++i) {
		// if sensor is not over a black area
		if (sensors[i] <= 500) {

			// move back onto the line undoing the previous move forward
			set_motors(-motor_speed, -motor_speed);
			delay_ms(150);
			set_motors(0, 0);
			return false;
		}
	}

	// all sensors are over a black area for the second time; we are at target
	return true;
}

/** gotoEnd *******************************************
 * Goes to the end of a segment, using sensor data to know when to stop
 */
void gotoEnd() {
	unsigned int sensors[5];


	// stop and read the initial sensor values
	set_motors(0, 0);
	read_line(sensors, IR_EMITTERS_ON);
	bool left = sensors[left_sensor] > 500;
	bool right = sensors[right_sensor] > 500;

	// keep moving forward
	set_motors(motor_speed, motor_speed);

	// if we have left and right sensor values we move forward until they are both clear of a line
	if (left && right) {
		// move forward until we more off the line either on both sides
		while (((sensors[left_sensor] > 500) == left) && ((sensors[right_sensor] > 500) == right)) {
			read_line(sensors, IR_EMITTERS_ON);
		}
	}
	// only wait for the left sensor to clear
	else if (left) {
		while (((sensors[left_sensor] > 500) == left)) {
			read_line(sensors, IR_EMITTERS_ON);
		}
	}
	// only wait for the right sensor to clear
	else if (right) {
		while (((sensors[right_sensor] > 500) == right)) {
			read_line(sensors, IR_EMITTERS_ON);
		}

	}

	// move a bit further forward then stop
	delay_ms(200);
	set_motors(0,0);

}

/** turnRight *******************************************
 * turns the robot to the right
 */
void turnRight() {
	gotoEnd();
	makeTurn(false);
}

/** turnLeft *******************************************
 * turns the robot to the left
 */
void turnLeft() {
	gotoEnd();
	makeTurn(true);
}

/** followSegment ****************************************
 * follow segment until a turn is found, an end of the line
 * or the target
 *
 * @params left -- by reference if a left branches was found
 *         right -- by reference if a right branches was found
 *         straight -- by reference if a straight line was found
 */
void followSegment(bool& left, bool& right, bool& straight) {

	// the value returned from the line sensors
	int sensorValue = 2000;

	// the raw sensor values from the 3pi
	unsigned int sensors[5];

	// the change in speed
	signed int delta_speed = 0;



	// inital read of the sensor value
	sensorValue = read_line(sensors, IR_EMITTERS_ON);
	left = right = false;

	// while we have a line at center and not at a branch
	while (!left && !right) {

		// could have this in the while but this way is slightly easier to read
		// if we go off the line stop we break out of the loop
		if (sensorValue > 2000 + sensor_threshold || sensorValue < 2000 - sensor_threshold) {
			break;
		}

		// normalize the sensor value to a motor delta value and turn motors on
		// delta is between 0 and motor_speed
		delta_speed = ((motor_speed - (-motor_speed)) * (sensorValue - 0.0))/(4000.0 - 0.0) + (-motor_speed);
		set_motors(motor_speed + delta_speed, motor_speed - delta_speed);

		// we need to update the sensor values of the 3pi will just repeat forever
		sensorValue = read_line(sensors, IR_EMITTERS_ON);
		left = sensors[left_sensor] > 500;
		right = sensors[right_sensor] > 500;

		// if we have a reading to the left or right keep moving forward a bit
		// this is to fix a big where the 3pi would read one part of a branch but
		// miss the other because it read one side too fast
		if (left || right) {

			// move forward some and read the sensors again
			set_motors(motor_speed, motor_speed);
			delay_ms(55);
			sensorValue = read_line(sensors, IR_EMITTERS_ON);
			left = sensors[left_sensor] > 500;
			right = sensors[right_sensor] > 500;

		}


		// if we are at target we stop and return from the function
		if (isTarget()) {
			set_motors(0,0);
			done = true;
			return;
		}

	}

	set_motors(motor_speed, motor_speed);


	// if we have left and right sensor values we move forward until they are both clear of a line
	// if only left we wait for the left sensor to clear
	// if only right we wait for the right sensor to clear
	// we always break out when we move off the center of the line
	if (left && right) {
		while (sensors[center_sensor] > 500 && ((sensors[left_sensor] > 500) == left) && ((sensors[right_sensor] > 500) == right)) {
			read_line(sensors, IR_EMITTERS_ON);
		}
	}
	else if (left) {
		while (sensors[center_sensor] > 500 && ((sensors[left_sensor] > 500) == left)) {
			read_line(sensors, IR_EMITTERS_ON);
		}
	}
	else if (right) {
		while (sensors[center_sensor] > 500 && ((sensors[right_sensor] > 500) == right)) {
			read_line(sensors, IR_EMITTERS_ON);
		}

	}

	// check straight now since we are off the intersection
	straight = (sensors[center_sensor] > 500);

	// let us know what you are doing Mr. 3pi
	play_from_program_space(beep_button_b);
	set_motors(0,0);
}

/** trimRoute ****************************************
 * remove dead ends from the route
 *
 * @params route -- an array that is filled with the route taken
 *         maxLen -- the length of the route
 *
 * @returns -- the new length of the route
 */
int trimRoute(char route[], int len) {

	// we only simplify if the second last turn was a dead end
	// also check to make sure we have more than 3 turns in our route
	if(len < 3 || route[len-2] != turn_dead_end) {
		return len;
	}

	int total_angle = 0;

	int i;

	// this could be unrolled but any decent compiler should do that anyways
	for(i = 1; i <= 3; ++i) {
		switch(route[len - i]) {
		case turn_right:
			total_angle += 90;
			break;
		case turn_left:
			total_angle += 270;
			break;
		case turn_dead_end:
			total_angle += 180;
			break;
		}
	}

	// limit to range between 0 and 360 degrees
	total_angle %= 360;

	// determine the final angle of the previous 3 turns
	switch(total_angle)
	{
	case 0:
		route[len - 3] = turn_straight;
		break;
	case 90:
		route[len - 3] = turn_right;
		break;
	case 180:
		route[len - 3] = turn_dead_end;
		break;
	case 270:
		route[len - 3] = turn_left;
		break;
	}

	return len - 2;
}


/** followRoute ****************************************
 * follow the provided route
 *
 * @params route -- an array that is filled with the route taken
 *         maxLen -- the length of the route
 *
 * @returns -- true if route is followed successfully, else false
 */
bool followRoute(char route[], int len) {

	int i;

	bool left;
	bool right;
	bool straight;

	// for each turn in route
	for (i = 0; i < len; ++i) {
		// just follow the line until something interesting happens
		followSegment(left, right, straight);

		// for the turn if it's left, turn left; right, turn right and straight, keep going
		switch (route[i]) {
		case turn_right:
			if (!right) {
				return false;
			}
			turnRight();
			break;
		case turn_left:
			if (!left) {
				return false;
			}
			turnLeft();
			break;
		case turn_straight:
			if (!straight) {
				return false;
			}

			set_motors(motor_speed, motor_speed);
			delay_ms(200);
			set_motors(0,0);

			break;
		}
	}

	// we are past the last turn, keep going until end
	followSegment(left, right, straight);

	return true;
}


