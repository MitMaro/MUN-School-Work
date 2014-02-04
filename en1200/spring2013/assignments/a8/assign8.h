/* **********************************************
 * Memorial University of Newfoundland
 * Engineering 1020 Introduction to Programming
 * 
 * assign8.h
 *
 *  Created on: 2013-07-16
 *      Author: dpeters
 */

#ifndef ASSIGN8_H_
#define ASSIGN8_H_

int solveMazeBest(char route[], int maxLen);
int trimRoute(char route[], int len);
bool followRoute(char route[], int len);

bool isTarget();
void followSegment(bool& left, bool& right, bool& straight);
void turnLeft();
void turnRight();

#endif /* ASSIGN8_H_ */
