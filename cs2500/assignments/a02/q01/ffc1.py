#########################################################
##  CS 2500 (Fall 2008), Assignment #2, Question #1    ##
##   Script File Name: ffc1.py                         ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
# ffc1.py
# Written by Tim Oram for CS 2500 (Assignment 2, Question 1)
"""
Given a multi-column file of integers and an ffs and outputs error
 messages including line number and column position if any column entry
 is not an integer or does not have the appropriate value as specified
 by the ffs. If no error is detected in the file, a message is printed
 to that effect. 
"""

import sys

# print a little usage message when the required parameters is not given
if len(sys.argv) != 3:
    print "usage:", sys.argv[0], "filename {+,-,p,i}N"
    sys.exit(1) # halt script

# open the given file, grab the lines and then close the file    
f = open(sys.argv[1])
lines = f.readlines()
f.close

error = False
for i in xrange(len(lines)):
    
    line = lines[i].split()
    for j in xrange(len(line)):
    
        # check to see if character is a number first
        try:
            value = int(line[j])
            # check for a positive integer but not 0
            if sys.argv[2][j] == "+":
                if value < 1:
                    error = True
                    print "error: line ", i + 1, " / column ", j + 1, ": bad integer type"
            # check for negative or 0 integer
            elif sys.argv[2][j] == "-":
                if value > -1:
                    error = True
                    print "error: line ", i + 1, " / column ", j + 1, ": bad integer type"
            # check for a positive integer including 0
            elif sys.argv[2][j] == "p":
                if value < 0:
                    error = True
                    print "error: line ", i + 1, " / column ", j + 1, ": bad integer type"
                    
        except ValueError:
            # if there is an error in casting line to integer print a message
            # saying non integer value
            print "error: line ", i + 1, " / column ", j + 1, ": not an integer"
            error = True

# if there was no error in the file then we better say it
if not error:
    print "File is in specified format"
