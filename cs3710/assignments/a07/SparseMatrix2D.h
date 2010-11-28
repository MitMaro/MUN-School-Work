#ifndef GUARD_SparseMatrix2D_h
#define GUARD_SparseMatrix2D_h
/*#########################################################
  ##  CS 3710 (Winter 2010), Assignment #7 Question #2   ##
  ##   Program File Name: SparseMatrix2D.h               ##
  ##       Student Name: Tim Oram                        ##
  ##         Login Name: oram                            ##
  ##              MUN #: #########                       ##
  #########################################################*/

#include <string>
#include <map>

using namespace std;
class SparseMatrix2D {
	public:
		void read(string filename);
		void write(string filename) const;
		void print() const;
		void add(SparseMatrix2D X);
		void add(double v);
		void subtract(SparseMatrix2D X);
		void subtract(double v);
		void scale(double v);
	private:
		map<pair<int, int> , double> matrix;
};

#endif
