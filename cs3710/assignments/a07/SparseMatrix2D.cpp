/*#########################################################
  ##  CS 3710 (Winter 2010), Assignment #7 Question #2   ##
  ##   Program File Name: SparseMatrix2D.cpp             ##
  ##       Student Name: Tim Oram                        ##
  ##         Login Name: oram                            ##
  ##              MUN #: #########                       ##
  #########################################################*/

#include <fstream>
#include <map>
#include <string>
#include <iostream>
#include "SparseMatrix2D.h"

using namespace std;

void SparseMatrix2D::read(string filename){
	// open file
	ifstream file;
	file.open(filename.c_str());
	string input;
	int i, j;
	double k;

	while(file >> i){
		file >> j;
		file >> k;
		this->matrix[pair<int, int>(i, j)] = k;
	}
	file.close();
}

void SparseMatrix2D::write(string filename) const {
	// open file
	ofstream file;
	file.open(filename.c_str());
	for(map<pair<int, int>, double>::const_iterator it = this->matrix.begin();
	                                           it != this->matrix.end(); it++) {
		file << it->first.first << " " << it->first.second << " " << it->second << endl;
	}
	file.close();
}

void SparseMatrix2D::print() const {
	for(map<pair<int, int>, double>::const_iterator it = this->matrix.begin();
	                                           it != this->matrix.end(); it++) {
		cout << it->first.first << " " << it->first.second << " " << it->second << endl;
	}
}

void SparseMatrix2D::add(SparseMatrix2D X) {
	map<pair<int, int> , double>::iterator it;

	for(it = X.matrix.begin(); it != X.matrix.end(); it++) {
		// check if value exists
		if(this->matrix.count(pair<int, int>(it->first.first, it->first.second)) == 0){
			this->matrix[pair<int, int>(it->first.first, it->first.second)] = 0;
		}
	}

	for(it = X.matrix.begin(); it != X.matrix.end(); it++) {
		this->matrix[pair<int, int>(it->first.first, it->first.second)] += it->second;
	}
}

void SparseMatrix2D::add(double v) {

	for(map<pair<int, int> , double>::iterator it = this->matrix.begin();
	                                      it != this->matrix.end(); it++) {
		it->second += v;
	}
}

void SparseMatrix2D::subtract(SparseMatrix2D X) {
	map<pair<int, int> , double>::iterator it;

	for(it = X.matrix.begin(); it != X.matrix.end(); it++) {
		// check if value exists
		if(this->matrix.count(pair<int, int>(it->first.first, it->first.second)) == 0){
			this->matrix[pair<int, int>(it->first.first, it->first.second)] = 0;
		}
	}

	for(it = X.matrix.begin(); it != X.matrix.end(); it++) {
		this->matrix[pair<int, int>(it->first.first, it->first.second)] -= it->second;
	}
}

void SparseMatrix2D::subtract(double v) {
	map<pair<int, int> , double>::iterator it;

	for(it = this->matrix.begin(); it != this->matrix.end(); it++) {
		it->second -= v;
	}
}

void SparseMatrix2D::scale(double v) {
	map<pair<int, int> , double>::iterator it;

	for(it = this->matrix.begin(); it != this->matrix.end(); it++) {
		it->second *= v;
	}
}


