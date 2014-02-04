/*************************************************************************
 * Memorial University of Newfoundland
 * Engineering 1020 Structured Programming
 * Assignment 3 Test Driver
 * 
 * Author: Dennis Peters
 * Engineering login name: dpeters
 * Due date: 2008.012.04
 * 
 * a3main.cpp -- Simple test driver for A3. 
 * ***********************************************************************/
#include <iostream>
using namespace std;

#include "assign3.h"

void testPhoneKey();
void testDayNumber();

/** main *************************************************************
 * @modifies cout -- output appended
 *           cin -- read input
 * 
 * @returns 0
 * ***********************************************************************/
int main() {
	
	int response = 9;
	cout << "1020 Assignment 3 Testing harness." << endl;
	while (response != 0) {
		cout << "\nAvailable options:\n";
		cout << "   1 - Test phoneKey\n";
		cout << "   2 - Test dayNumber\n";
		cout << "   0 - Quit\n\n";
		cout << " Please enter a selection:" << endl;
		cin >> response;
		switch (response) {
		case 0:
			cout << "Program exit." << endl;
			break;
			
		case 1:
			testPhoneKey();
			break;
			
		case 2:
			testDayNumber();
			break;
			
		default: // Unexpected input
			if (cin.fail()) {
			    cin.clear();
			    cin.ignore(80, '\n');
			}
		}
		
	}
	return 0;
}

/** testPhoneKey *********************************************
 *
 * Test driver for phoneKey()
 * @modifies cin -- reads a letter
 *           cout -- outputs prompt and results.
 * ***********************************************************************/
void testPhoneKey() {
	char l;
	cout << "Enter the letter: ";
	cin >> l;
	cout << "\nphoneKey( '" << l << "') = ";
	cout << phoneKey(l) << endl;
}

/** testDayNumber *********************************************************
 *
 * Test driver for dayNumber()
 * @modifies cin -- reads three ints
 *           cout -- outputs prompt and results.
 * ***********************************************************************/
void testDayNumber() {
	int d, m, y;
	cout << "Enter the day (1 -- 31): ";
	cin >> d;
	cout << "Enter the month as a number (1 -- 12): ";
	cin >> m;
	cout << "Enter the year: ";
	cin >> y;
	cout << "\ndayNumber(" << d << ", " << m << ", " << y << ") = ";
	cout << dayNumber(d, m, y) << endl;
}

