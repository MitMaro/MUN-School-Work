/* **********************************************
 * Memorial University of Newfoundland
 * Engineering 1020 Introduction to Programming
 * 
 * util3pi.h
 *
 *  Created on: 2013-05-27
 *      Author: dpeters
 */

#ifndef UTIL3PI_H_
#define UTIL3PI_H_

// A couple of simple tunes, stored in program space.
const char welcome[] PROGMEM = ">g32>>c32";
const char beep_button_a[] PROGMEM = "!c32";
const char beep_button_b[] PROGMEM = "!e32";
const char beep_button_c[] PROGMEM = "!g32";
const char go[] PROGMEM = "L16 cdegreg4";
const char success[] PROGMEM = "! L32 drdrdrdrr L16 br cr dr cf";

// Some fixed messages for output.
const char welcome_line1[] PROGMEM = " Pololu";
const char welcome_line2[] PROGMEM = "3\xf7 Robot"; // \xf7 is a greek pi

void displayWelcome();
void displayBattery();
void waitForButton(unsigned char button, const char *tune);
unsigned char lineCalibration();
void load_custom_characters();
int readLineSensors();
int readSensorsBranches(bool& isLeft, bool& isRight);
void display_readings(const unsigned int calibrated_values[]);

#endif /* UTIL3PI_H_ */
