/*#########################################################
  ##  CS 3710 (Winter 2010), Assignment #1, Question #1  ##
  ##  Program File Name: addFiles.c                      ##
  ##       Student Name: Tim Oram                        ##
  ##         Login Name: oram                            ##
  ##              MUN #: #########                       ##
  ##               Date: January 22, 2010                ##
  #########################################################*/

#include <stdio.h>
#include <string.h>

int main(void){
	/* used for reading the filenames from the user input */
	char filename[1000];
	/* some file pointers */
	FILE* file_in1;
	FILE* file_in2;
	FILE* file_out;
	/* the numbers read from the files */
	int num1;
	int num2;

	/* prompt and open input file 1 for reading */
	printf("Input File #1 name?: ");
	fgets(filename, sizeof filename, stdin);
	/* trim off newline */
	filename[strlen(filename) - 1] = '\0';
	file_in1 = fopen(filename, "r");
	if(file_in1 == NULL){
		printf("Error opening: %s\n", filename);
		return 1;
	}

	/* prompt and open input file 2 for reading */
	printf("Input File #2 name?: ");
	fgets(filename, sizeof filename, stdin);
	/* trim off newline */
	filename[strlen(filename) - 1] = '\0';
	file_in2 = fopen(filename, "r");
	if(file_in2 == NULL){
		printf("Error opening: %s\n", filename);
		return 2;
	}

	/* prompt and open output file for writing */
	printf("       Output File?: ");
	fgets(filename, sizeof filename, stdin);
	/* trim off newline */
	filename[strlen(filename) - 1] = '\0';
	file_out = fopen(filename, "w");
	if(file_out == NULL){
		printf("Error opening %s\n", filename);
		return 3;
	}

	/* we could assume that both files will have the same number of lines but it
	   is very easy to check both files */
	while (fscanf(file_in1, "%d", &num1) != EOF && fscanf(file_in2, "%d", &num2) != EOF) {
		/* we get the sum and print it out */
		fprintf(file_out, "%d\n", num1 + num2);
	}

	/* what was open should be closed, though not necessary in this situation */
	fclose(file_in1);
	fclose(file_in2);
	fclose(file_out);

	return 0;
}