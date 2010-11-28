/*#########################################################*/
/*##  CS 3710 (Winter 2010), Assignment #7, Question #2  ##*/
/*##  Program File Name: testSM.cpp                      ##*/
/*##       Student Name: Todd Wareham                    ##*/
/*##         Login Name: harold                          ##*/
/*##              MUN #: #######                         ##*/
/*#########################################################*/

/*
 * Test program for 2D sparse matrix class SprseMatrix2D.
 */

#include <iostream>
#include "SparseMatrix2D.h"

using namespace std;


int main(int argc, char **argv) {
    SparseMatrix2D M1, M2, M3;

    cout << "Read in matrix #1" << endl;
    M1.read("M1.1.dat");
    cout << "Matrix #1:" << endl;
    M1.print();
    cout << "Scale matrix #1 by 5.5" << endl;
    M1.scale(5.5);
    cout << "Matrix #1:" << endl;
    M1.print();
    cout << "Write out matrix #1" << endl;
    M1.write("M1.2.dat");

    cout << "Read in matrix #2" << endl;
    M2.read("M1.2.dat");
    cout << "Matrix #2:" << endl;
    M2.print();
    cout << "Add 25.3 to matrix #2" << endl;
    M2.add(25.3);
    cout << "Matrix #2:" << endl;
    M2.print();
    cout << "Substract 25.1 from matrix #2" << endl;
    M2.subtract(25.1);
    cout << "Matrix #2:" << endl;
    M2.print();

    cout << "Add matrix #1 to matrix #2" << endl;
    M2.add(M1);
    cout << "Matrix #2:" << endl;
    M2.print();
    cout << "Substract matrix #2 from matrix #1" << endl;
    M1.subtract(M2);
    cout << "Matrix #1:" << endl;
    M1.print();

    cout << "Read in matrix #3" << endl;
    M3.read("M3.1.dat");
    cout << "Matrix #3:" << endl;
    M3.print();
    cout << "Add matrix #3 to matrix #2" << endl;
    M2.add(M3);
    cout << "Matrix #2:" << endl;
    M2.print();
    cout << "Substract matrix #1 from matrix #3" << endl;
    M3.subtract(M1);
    cout << "Matrix #3:" << endl;
    M3.print();
}
