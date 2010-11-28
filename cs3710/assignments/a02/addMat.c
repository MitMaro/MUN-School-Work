/*#########################################################
  ##  CS 3710 (Winter 2010), Assignment #2, Question #1  ##
  ##  Program File Name: addMat.c                        ##
  ##       Student Name: Tim Oram                        ##
  ##         Login Name: oram                            ##
  ##              MUN #: #########                       ##
  ##               Date: February 02, 2010               ##
  #########################################################*/


#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]){

	/* argument check */
	if(argc <= 3){
		printf("format: a.out inputMat1File inputMat2File outputMatFile\n");
		return 1;
	}

	FILE *file_in_1;
	FILE *file_in_2;
	FILE *file_out;
	int **matrix;
	int i, j;
	int rows, columns;
	int num1, num2;


	/* open the files */
	file_in_1 = fopen(argv[1], "r");
	if(file_in_1 == NULL){
		printf("Error opening: %s\n", argv[1]);
		return 2;
	}
	file_in_2 = fopen(argv[2], "r");
	if(file_in_2 == NULL){
		printf("Error opening: %s\n", argv[2]);
		return 3;
	}
	file_out = fopen(argv[3], "w");
	if(file_in_2 == NULL){
		printf("Error opening: %s\n", argv[3]);
		return 4;
	}

	/* since m & n are in both files read twice, I could check to see if the
	   values are the same but it is not required */
	fscanf(file_in_1, "%d %d", &rows, &columns);
	fscanf(file_in_2, "%d %d", &rows, &columns);


	/* allocate the memory for the matrix */
	matrix = malloc(rows * sizeof(*matrix));
	if(matrix == NULL){
		printf("An error occurred allocating a block of memory for use");
		return 5;
	}
	for(i = 0; i < rows; ++i){
		matrix[i] = malloc(columns * sizeof(**matrix));
		if(matrix[i] == NULL){
			printf("An error occurred allocating a block of memory for use");
			return 5;
		}
	}

	/* this assumes a correctly formatted file, bad to do in practice */
	for(i = 0; i < rows; ++i){
		for(j = 0; j < columns; ++j){
			fscanf(file_in_1, "%d", &num1);
			fscanf(file_in_2, "%d", &num2);
			matrix[i][j] = num1 + num2;
		}
	}

	/* print the resultant matrix with dimensions*/
	fprintf(file_out, "%d %d\n", rows, columns);
	for(i = 0; i < rows; ++i){
		for(j = 0; j < columns; ++j){
			fprintf(file_out, "   %d ", matrix[i][j]);
		}
		fprintf(file_out, "\n");
	}

	/* do some clean up, not necessary but a good practice */
	for(i = 0; i < rows; ++i){
		free(matrix[i]);
	}
	free(matrix);

	fclose(file_in_1);
	fclose(file_in_2);
	fclose(file_out);
	return 0;
}

