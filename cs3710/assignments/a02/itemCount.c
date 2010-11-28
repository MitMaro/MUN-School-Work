/*#########################################################
  ##  CS 3710 (Winter 2010), Assignment #2, Question #2  ##
  ##  Program File Name: itemCount.c                     ##
  ##       Student Name: Todd Wareham                    ##
  ##         Login Name: harold                          ##
  ##              MUN #: #######                         ##
  #########################################################*/

/*
 * Given as command-line arguments the number of distinct items in
 *  an item textfile and the name of an item textfile, use the
 *  functions in the dictionary data structure described in file
 *  "dict.h" to create and output a list of the number of times each
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "dict.h"

#define NAMELENGTH 100

int main(int argc, char **argv){

    FILE *itemFile;
    dict *D;
    char itemName[NAMELENGTH];


    if (argc != 3){
        printf("format: %s numDistinctItems itemfile\n", argv[0]);
        return 1;
    }

    D = init(atoi(argv[1]));

    itemFile = fopen(argv[2], "r");
    while (fgets(itemName, NAMELENGTH, itemFile) != NULL){
        itemName[strlen(itemName) - 1] = '\0';
        if (isKey(D, itemName) == -1){
            setKeyValue(D, itemName, 0);
        }
        setKeyValue(D, itemName, getKeyValue(D, itemName) + 1);
    }
    fclose(itemFile);

    printValues(D);
    return 0;
}
