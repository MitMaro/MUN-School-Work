/*#########################################################
  ##  CS 3710 (Winter 2010), Assignment #2, Question #2  ##
  ##  Program File Name: dict.h                          ##
  ##       Student Name: Tim Oram                        ##
  ##         Login Name: oram                            ##
  ##              MUN #: #########                       ##
  ##               Date: February 02, 2010               ##
  #########################################################*/

/*
 * This file implements a simple dynamic dictionary data structure
 *  consisting of (key,value) pairs in which lookup can be done relative
 *  to a string key to retrieve its associated integer value. The pairs are
 *  stored in a pair of string and integer arrays, and a particular pair's
 *  key and value are stored at the same position in the two arrays.
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>


typedef struct {
	int  numKeys;
	char **key;
	int  *value;
} dict;

dict *init(int num_keys){
	dict *d;
	int i;

	/* allocate memory for the structure */
	d = (dict *) malloc(sizeof(dict));
	d->numKeys = num_keys;
	/* allocate memory for the keys */
	d->key = malloc(num_keys * sizeof(char *));
	/* fill default values for keys */
	for(i = 0; i < num_keys; ++i){
		d->key[i] = "";
	}
	/* allocate memory for the values */
	d->value = calloc(num_keys, sizeof (int));
	return d;
}

int isKey(dict *d, char *k){
	/* linear search for a key */
	int i;
	for(i = 0; i < d->numKeys; ++i){
		/* if found return the key index */
		if(strcmp(k, d->key[i]) == 0){
			return i;
		}
	}
	/* not found return a -1 */
	return -1;
}

int setKeyValue(dict *d, char *k, int v){
	int i;
	char *tmp_k;

	/* check if key is already used and set the value if so */
	if((i = isKey(d, k)) != -1){
		d->value[i] = v;
		return 1;
	}

	/* check for an empty key/value */
	for(i = 0; i < d->numKeys; ++i){
		/* empty string is an array with the zeroth index a null */
		if(d->key[i][0] == '\0'){
			/* allocate some memory for the value */
			tmp_k = malloc(strlen(k) * sizeof (char) + 1);
			/* copy the value into this new memory location so it does not get
			   over written later */
			strcpy(tmp_k, k);
			d->key[i] = tmp_k;
			d->value[i] = v;
			return 1;
		}
	}
	return -1;
}

int getKeyValue(dict *d, char *k){
	int i;
	/* check if key is used, if not return a -1 */
	if((i = isKey(d, k)) == -1){
		return -1;
	}
	/* key was used and thus has a value */
	return d->value[i];
}

void printValues(dict *d){
	int i;
	/* iterate over all the key/value pairs in the dictionary and print the
	   one that are not empty. */
	for(i = 0; i < d->numKeys; ++i){
		/* when an empty key is found we are at the end of the list and we can
		   stop printing. */
		if(d->key[i][0] == '\0'){
			return;
		}
		printf("%s: %d\n", d->key[i], d->value[i]);
	}
}
