/*#########################################################
  ##  CS 3710 (Winter 2010), Assignment #6               ##
  ##   Script File Name: ratMach.cpp                     ##
  ##       Student Name: Tim Oram                        ##
  ##         Login Name: oram                            ##
  ##              MUN #: #########                       ##
  #########################################################*/

#include <vector>
#include <iterator>
#include <sstream>
#include <iostream>
#include <fstream>
#include <string>
#include "rational.h"

using namespace std;

// some helper functions
void help(void);
void invalid(void);
int strtoint(const string);

// the types of operations that can be performed
enum Operation {NONE, ADD, SUBTRACT, CLEAR};

int main(int argc, char *argv[]){

	string input; // the input from the user
	vector<string> input_params; // tokenized input
	string prompt = "$"; // the user prompt string

	int num_registers; // the number of registers to register
	int register_index; // the register to store the result of an operation
	Rational reg(0, 1); // a register to use when it is needed
	vector<Rational> registers(0); // the registers

	istringstream iss; // for parsing strings
	stringstream ss; // for parsing strings
	string tmp; // used as a temporary string when I need one
	int i, j; // some integers that are used in misc. places

	Operation op = NONE; // the operation to perform

	// if number of registers were not given print the help message and quit
	if(argc != 2){
		help();
		return 1;
	}

	// size the list
	tmp.assign(argv[1]);
	num_registers = strtoint(tmp);
	if(num_registers < 1){
		help();
		return 1;
	}
	registers.resize(num_registers, Rational(0,1));

	while(true){
		// reset some values
		input_params.clear();
		reg.assign(0,1);
		iss.clear();
		op = NONE;
		register_index = 0;

		// display user prompt
		cout << prompt << " ";

		// get input from user and exit if end of file (ctrl^d) or exit was given
		if(getline(cin, input).eof() || input.compare("exit") == 0){;
			// exit program
			break;
		}

		// load the input into a string stream for processing
		iss.str(input);

		// tokenize user input
		copy(istream_iterator<string>(iss),
		     istream_iterator<string>(),
		     back_inserter(input_params));


		switch(input_params.size()){
			// ignore no input
			case 0:
				break;
			// handle commands with no paramaters
			case 1:
				if(input_params[0].find("=R") == 0){
					// get register index
					register_index = strtoint(input_params[0].substr(2));

					// validation check on index
					if(register_index >= num_registers){
						cout << "Invalid Register. Please enter register from 0 and "
						     << num_registers - 1 << endl;
						break;
					}

					// output register value
					cout << registers[register_index] << endl;
				}
				else if(input_params[0].find("0R") == 0){
					// get register value and validate
					register_index = strtoint(input_params[0].substr(2));
					if(register_index >= num_registers){
						cout << "Invalid Register. Please enter register from 0 and "
						     << num_registers - 1 << endl;
						break;
					}
					// reset register
					registers[register_index].assign(0, 1);
				}
				else if(input_params[0].find("info") == 0){
					// output some stats on the registers
					cout << "Number of Registers: " << num_registers << endl;
					cout << "Register Values:" << endl;
					for(vector<Rational>::size_type r = 0; r < registers.size(); ++r) {
						cout << "   Register: " << r
						     << "   Value: " << registers[r] << endl;
					}
				}
				else if(input_params[0].find("help") == 0){
					help();
				}
				else {
					// since no other statement was able to handle the input it
					// is invalid
					invalid();
				}
				break;
			case 2:

				if(input_params[0].compare("load") == 0){
					// open file
					ifstream file;
					file.open(input_params[1].c_str());

					// check if file exists
					if(!file.is_open()){
						cout << "File does not exist" << endl;
						file.close();
						break;
					}

					// get the number of registers from the file and validate
					getline(file, tmp);
					if(strtoint(tmp) != num_registers){
						cout << "Error: File contains " << tmp
						     << " registers, you have " << num_registers << endl;
						file.close();
						break;
					}

					// read the register info from the file
					for(register_index = 0; register_index < num_registers; register_index++){
						// numerator
						getline(file, tmp, '/');
						i = strtoint(tmp);

						// denominator
						getline(file, tmp);
						j = strtoint(tmp);

						registers[register_index].assign(i, j);
					}
					file.close();

					break;
				}
				else if(input_params[0].compare("save") == 0){
					// open file
					ofstream file;
					file.open(input_params[1].c_str());
					// write the number of registers
					file << num_registers << "\n";
					// write each value in the registers
					for(vector<Rational>::iterator r = registers.begin();
					  r != registers.end(); ++r) {
						file << r->getNumerator() << "/" << r->getDenominator() << "\n";
					}
					file.close();
					break;
				}
				// for fun: change the prompt text
				else if(input_params[0] == "prompt"){
					prompt = input_params[1];
					break;
				}
				// if a rational number function
				else if(input_params[0][1] == 'R'){
					// grab the register index from the input
					register_index = strtoint(input_params[0].substr(2));
					if(register_index >= num_registers){
						cout << "Invalid Register. Please enter register from 0 and "
						     << num_registers - 1 << endl;
						break;
					}

					// find the operation
					switch(input_params[0][0]){
						case '+': op = ADD; break;
						case '-': op = SUBTRACT; break;
						default:
							cout << "Invalid Operation" << endl;
						break;
					}
				}
				// invalid input for command, print a message
				else{
					invalid();
					break;
				}

				// parse the paramater
				// if a register
				if(input_params[1][0] == 'R'){
					i = strtoint(input_params[1].substr(1));
					if(i >= num_registers){
						cout << "Invalid Register. Please enter register from 0 and "
						     << num_registers - 1 << endl;
						break;
					}
					reg = registers[i];
				}
				else{
					// parse the value
					iss.clear();
					iss.str(input_params[1]);

					// numerator
					getline(iss, tmp, '/');
					i = strtoint(tmp);

					// don't parse denominator unless there is one
					if(input_params[1].find("/") != string::npos){
						// denominator
						getline(iss, tmp);
						j = strtoint(tmp);
					}
					else{
						j = 1;
					}

					reg.assign(i, j);
				}

				// perform operations, more could go here if we want. Like
				// multiplication and division
				switch(op){
					case ADD:
						registers[register_index] + reg;
					break;
					case SUBTRACT:
						registers[register_index] - reg;
					break;
				}

				break;
			default:
				// to many arguments so input is invalid
				invalid();
		}
	}
	return 0;
}

// used to print the help message
void help(void){
	cout << "    usage: ratMach num_registers" << endl << endl
 << "    Commands:" << endl
 << "        +Ri X: Add rational number X to internal register i." << endl
 << "        -Ri X: Subtract rational number X from internal register i." << endl
 << "        +Ri Rj: Add the value in internal register j to internal register i." << endl
 << "        -Ri Rj: Subtract the value in internal register j from internal register i." << endl
 << "        =Ri: Display the value stored in internal register i." << endl
 << "        0Ri: Clear internal register i, i.e., set the value of internal register i to 0.." << endl
 << "        load fname: Read in values of all internal registers from file fname." << endl
 << "        save fname: Save values of all internal registers to file fname." << endl
 << "        prompt prompt_string: Set the prompt string." << endl
 << "        info: List information on the number and values of all registers." << endl
 << "        help: List all available commands and their formats." << endl
 << "        exit: Shut down calculator interface session. " << endl
 << "    X can be specified as an explicit rational, ie. 1/2, 10/3, 4/2, 22/7 " << endl
 << "    or implicit rational, ie. 5, 0, 3.14." << endl;
}

// print the invalid message
void invalid(void){
	cout << "    Invalid input provided, type help for command list" << endl;
}

// helper function to convert a string to a number, like atoi but handles unicode
int strtoint(const string s){
	stringstream ss(s); // for parsing strings
	int rtn;
	ss >> rtn;
	return rtn;
}
