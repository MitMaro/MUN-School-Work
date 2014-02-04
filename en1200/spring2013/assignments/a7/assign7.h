/* **********************************************
 * Memorial University of Newfoundland
 * Engineering 1020 Introduction to Programming
 * 
 * assign7.h
 *
 *  Created on: 2013-07-03
 *      Author: dpeters
 */

#ifndef ASSIGN7_H_
#define ASSIGN7_H_

int solveMaze(char route[], int maxLen);
bool isTarget();

void followSegment(bool& left, bool& right, bool& straight);
void turnLeft();
void turnRight();

#endif /* ASSIGN7_H_ */
