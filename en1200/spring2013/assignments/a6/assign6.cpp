/*************************************************************************
 * Memorial University of Newfoundland
 * Engineering 1020 Structured Programming
 * Assignment 6
 *
 * Author: Tim Oram
 * Student #: #########
 * E-mail (@mun.ca): toram@mun.ca
 * Lab section: 02
 * Due date: 2013.07.08
 *
 * assign6.cpp -- makes the robot follow a track keeping track of turns
 * ***********************************************************************/

#include <pololu/orangutan.h>
#include <pololu/3pi.h>
#include "util3pi.h"
#include "assign6.h"
#include <stdlib.h>

// global constants
namespace {
	unsigned const motor_speed = 50;
	unsigned const acceleration_tick = 3;
	signed const sensor_threshold = 1000;
};

struct Status {
	char function;
	bool lastTurnLeft;
} status;

struct Motor {
	int current_speed_left;
	int current_speed_right;
	signed int final_speed_left;
	int final_speed_right;
	int acceleration;
} motor;


void printStatus() {
	clear();
	lcd_goto_xy(0,0);
	print_long(motor.current_speed_left);
	lcd_goto_xy(0,1);
	print_long(motor.final_speed_left);
	lcd_goto_xy(3,0);
	print_long(motor.current_speed_right);
	lcd_goto_xy(3,1);
	print_long(motor.final_speed_right);
	lcd_goto_xy(6,0);
	print_long(motor.acceleration);
	lcd_goto_xy(6,1);
	char tmp[3];
	tmp[0] = status.function;
	tmp[1] = ((status.lastTurnLeft) ? 'L' : 'R');
	tmp[2] = '\0';
	print(tmp);
}

void accelerate() {

	signed int speed_left = motor.current_speed_left;
	signed int speed_right = motor.current_speed_right;

	signed int accelerate_left;
	signed int accelerate_right;

	status.function = 'A';
	printStatus();

	if (motor.current_speed_left > motor.final_speed_left) {
		accelerate_left = -abs(motor.acceleration);
	}
	else {
		accelerate_left = abs(motor.acceleration);
	}

	if (motor.current_speed_right > motor.final_speed_right) {
		accelerate_right = -abs(motor.acceleration);
	}
	else {
		accelerate_right = abs(motor.acceleration);
	}

	do {

		speed_left += accelerate_left;
		speed_right += accelerate_right;

		if (accelerate_left > 0 && speed_left > motor.final_speed_left) {
			speed_left = motor.final_speed_left;
		}
		else if (accelerate_left < 0 && speed_left < motor.final_speed_left) {
			speed_left = motor.final_speed_left;
		}
		if (accelerate_right > 0 && speed_right > motor.final_speed_right) {
			speed_right = motor.final_speed_right;
		}
		else if (accelerate_right < 0 && speed_right < motor.final_speed_right) {
			speed_right = motor.final_speed_right;
		}

		motor.current_speed_left = speed_left;
		motor.current_speed_right = speed_right;

		set_motors(speed_left, speed_right);
		delay_ms(acceleration_tick);

		printStatus();


	} while (speed_left != motor.final_speed_left || speed_right != motor.final_speed_right);

	motor.current_speed_left = motor.final_speed_left;
	motor.current_speed_right = motor.final_speed_right;
	motor.acceleration = 0;

	printStatus();

}

/** makeTurn *******************************************
 * makes a turn either left or right
 *
 * @params left -- when true will turn left, else turn right
 */
void makeTurn(bool left) {

	unsigned int sensors[5];
	int result = read_line(sensors, IR_EMITTERS_ON);

	int time;

	status.function = 'T';
	status.lastTurnLeft = left;

	time = get_ms();

	motor.acceleration = motor_speed / 4;
	motor.final_speed_left = motor_speed;
	motor.final_speed_right = motor_speed;

	// move forward
	accelerate();

	status.function = 'T';

	printStatus();

	// keep going forward until the sensors are off the line
	while (result > 0 && result < 4000) {
		result = read_line(sensors, IR_EMITTERS_ON);
	}

	time = (100 - get_ms() - time);

	// and just move forward a little bit more
	if (time > 0) {
		delay_ms(time);
	}

	motor.final_speed_left = 0;
	motor.final_speed_right = 0;
	motor.acceleration = 3;
	accelerate();

	status.function = 'q';

	printStatus();

	// turn one way for a left turn, other way for right
	if (left) {
		motor.final_speed_left = -motor_speed;
		motor.final_speed_right = motor_speed;
	}
	else {
		motor.final_speed_left = motor_speed;
		motor.final_speed_right = -motor_speed;
	}

	motor.acceleration = motor_speed;
	accelerate();

	status.function = 't';

	printStatus();
	// keep turning until we center on a line again
	while (result < 1800 || result > 2200) {
		result = read_line(sensors, IR_EMITTERS_ON);

		play_from_program_space(beep_button_b);
	}

	motor.final_speed_left = 0;
	motor.final_speed_right = 0;
	motor.acceleration = 3;
	accelerate();
}

/** turnRight *******************************************
 * turns the robot to the right
 */
void turnRight() {
	makeTurn(false);
}

/** turnLeft *******************************************
 * turns the robot to the left
 */
void turnLeft() {
	makeTurn(true);
}


/** followTrack ****************************************
 * follows a track with a robot
 *
 * @params turns -- array of the turns the robot takes, will contain L and Rs
 *         maxTurns -- the max number of turns the robot can take
 */
int followTrack(char turns[], int maxTurns) {
	int index = 0;

	bool left = true;
	bool right = true;
	bool straight = true;


	// initialize
	motor.acceleration = 11;
	motor.current_speed_left = 22;
	motor.current_speed_right = 33;
	motor.final_speed_left = 44;
	motor.final_speed_right = 55;
	status.function = 'F';
	status.lastTurnLeft = false;

	printStatus();

	// keep going while there is a turn or we can go straight
	while (left || right || straight) {

		// just follow the line until something interesting happens
		followSegment(left, right, straight);

		status.function = 'F';
		printStatus();
		// stop when we reach max turns
		if (index == maxTurns) {
			break;
		}

		// if a turn was found record it and make the turn
		if (left) {
			play_from_program_space(beep_button_b);
			turns[index] = 'L';
			index++;
			turnLeft();
		}
		else if (right) {
			play_from_program_space(beep_button_a);
			turns[index] = 'R';
			index++;
			turnRight();
		}
		status.function = 'F';
		printStatus();


	}

	// bring to full speed right away
	motor.acceleration = motor_speed;
	motor.final_speed_left = motor_speed;
	motor.final_speed_right = motor_speed;
	accelerate();

	// now bring to a smooth stop
	motor.acceleration = 2;
	motor.final_speed_left = 0;
	motor.final_speed_right = 0;
	accelerate();


	status.function = 'f';
	printStatus();

	while(true);

	return index;

}


/** followSegment ****************************************
 * follow segment until a turn is found or an end of the line
 *
 * @params left -- by reference if a left branches was found
 *         right -- by reference if a right branches was found
 *         straight -- by reference if a straight line was found
 */
void followSegment(bool& left, bool& right, bool& straight) {

	status.function = 'S';
	printStatus();

	// sensor values for left and right branches
	bool branchRightFound = false;
	bool branchLeftFound = false;

	// the value returned from the line sensors
	int sensorValue = 2000;

	// the change in speed
	signed int delta_speed = 0;


	// while we have sensor data and not at a branch
	while (sensorValue > 0 && sensorValue < 4000 && !branchLeftFound && !branchRightFound) {

		// we need to update the sensor values of the 3pi will just repeat forever
		sensorValue = readLineSensors();
		readSensorsBranches(branchLeftFound, branchRightFound);

		// normalize the
		delta_speed = ((motor_speed - (-motor_speed)) * (sensorValue - 0.0))/(4000.0 - 0.0) + (-motor_speed);

		// detect that we are off line and stop doing stuff
		if (sensorValue > (2000 + sensor_threshold) || sensorValue < (2000 - sensorValue)) {
			break;
		}

		motor.final_speed_left = motor_speed + delta_speed;
		motor.final_speed_right = motor_speed - delta_speed;
		motor.acceleration = 3;

		accelerate();
		status.function = 'S';
		printStatus();
	}

	// update the references
	left = branchLeftFound;
	right = branchRightFound;
	straight = sensorValue > 1800 && sensorValue < 2200;
	// stop the 3pi when we are done
}

