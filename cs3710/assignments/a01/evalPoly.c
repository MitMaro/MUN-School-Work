/*#########################################################
  ##  CS 3710 (Winter 2010), Assignment #1, Question #2  ##
  ##  Program File Name: evalPoly.c                      ##
  ##       Student Name: Tim Oram                        ##
  ##         Login Name: oram                            ##
  ##              MUN #: #########                       ##
  ##               Date: January 22, 2010                ##
  #########################################################*/

#include <stdio.h>
#include <stdlib.h>

int power(int a, int b);

int main(int argc, char *argv[]){

	int total = 0;
	int i;
	int x;

	/* argument check */
	if(argc <= 2){
		printf("format: %s x coeff(x^n) coeff(x^{n - 1}) ... coeff(x^0)\n", argv[0]);
		return 1;
	}

	/* convert the value of x provided to an integer */
	x = atoi(argv[1]);

	/* calculate the polynomial */
	for(i = 2; i < argc; ++i){
		total += atoi(argv[i]) * power(x, argc - i - 1);
	}
	printf("Value of specified polynomial is %d\n", total);
	return 0;
}


/* integer only power function */
int power(int a, int b){
	int i;
	int total = 1;
	for(i = 0; i < b; i++){
		total *= a;
	}
	return total;
}
