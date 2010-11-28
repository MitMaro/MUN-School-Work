/*#########################################################
  ##  CS 3710 (Winter 2010), Assignment #7 Question #1   ##
  ##   Program File Name: similarity.cpp                 ##
  ##       Student Name: Tim Oram                        ##
  ##         Login Name: oram                            ##
  ##              MUN #: #########                       ##
  #########################################################*/

#include <vector>
#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>

using namespace std;

int main(int argc, char *argv[]){

	if(argc != 3){
		cout << "format: a.out <wordList1-file> <wordList2-file>" << endl;
		return 1;
	}

	string input;

	ifstream f1;
	ifstream f2;

	f1.open(argv[1]);
	f2.open(argv[2]);

	vector<string> words1;
	vector<string> words2;
	vector<string> working_vector;
	vector<string>::iterator it;

	int union_size;
	int intersection_size;

	// read files
	while(!getline(f1, input).eof()){
		words1.push_back(input);
	}
	while(!getline(f2, input).eof()){
		words2.push_back(input);
	}

	// sort vectors
	sort(words1.begin(), words1.end());
	sort(words2.begin(), words2.end());

	// take the union and intersection and get their size
	working_vector.resize(max(words1.size(), words2.size()));
	it = set_intersection(words1.begin(), words1.end(), words2.begin(),
	                              words2.end(), working_vector.begin());
	intersection_size = (int)(it - working_vector.begin());
	working_vector.clear();
	working_vector.resize(words1.size() + words2.size());
	it = set_union(words1.begin(), words1.end(), words2.begin(),
	                       words2.end(), working_vector.begin());
	union_size = (int)(it - working_vector.begin());

	cout << "Similarity: " << (double)intersection_size / (double)union_size << endl;
}
