#########################################################
##  CS 2500 (Fall 2008), Assignment #3, Question #1    ##
##   Script File Name: ffc2.py                         ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
# ffc2.py
# Written by Tim Oram for CS 2500 (Assignment 3, Question 1)
"""
A file-format checker that takes as an argument a file to be checked.
Optional arguments are -r for number of rows in the file, -c for number
of columns in the file and -s which specifies the format of the rows.
If only a file name is given then the file is checked for integer values.
If -s is given the value of -c is ignored.
"""

import sys

# print a little usage message when the required parameters are not given
if len(sys.argv) < 2:
    print "usage:", sys.argv[0], "filename {-rNR, -cNC, -s{+,-,p,i}^NC}"
    sys.exit(1) # halt script

# open the given file, grab the lines and then close the file    
f = open(sys.argv[1])
lines = f.readlines()
f.close

# set some default values
numrows = None
numcols = None
format = None


# parse arguments
for arg in sys.argv[2:]:
    # check for '-' at beginning of argument
    if arg[0] != "-":
        print "error: argument ", arg, " invalid: ignored"
    elif arg[1] == "r": # row argument
        numrows = int(arg[2:])
    elif arg[1] == "c": # column argument
        numcols = int(arg[2:])
    elif arg[1] == "s": # format argument
        format = arg[2:]
    # unrecognized argument
    else:
        print "error: argument ", arg, " unrecognized: ignored"

# print an error message if the number of columns don't match the length of the format
if format != None and numcols != None and len(format) != numcols:
    print "error: len(format) != # columns: # columns ignored"
    
# if format is set then override numcols
if format != None:
    numcols = len(format)

error = False

# for each line
for i in xrange(len(lines)):
    
    # split the line into the individual values
    line = lines[i].split()

    # if numcols was supplied and the length is invalid
    if numcols != None and len(line) != numcols:
        error = True
        print "error: line ", i + 1, ": number of columns incorrect"
        continue

    # for each value in the row
    for j in xrange(len(line)):
        
        # check to see if character is a number first
        try:
            value = int(line[j])        
        except ValueError:
            # if there is an error in casting line to integer print a message
            # saying non integer value
            print "error: line ", i + 1, " / column ", j + 1, ": not an integer"
            error = True
            continue
            
        if format != None:
            # check for a positive integer but not 0
            if format[j] == "+":
                if value < 1:
                    error = True
                    print "error: line ", i + 1, " / column ", j + 1, ": bad integer type"
            # check for negative or 0 integer
            elif format[j] == "-":
                if value > -1:
                    error = True
                    print "error: line ", i + 1, " / column ", j + 1, ": bad integer type"
            # check for a positive integer including 0
            elif format[j] == "p":
                if value < 0:
                    error = True
                    print "error: line ", i + 1, " / column ", j + 1, ": bad integer type"

    
# check the number of rows is correct
if (numrows != None) and (len(lines) != numrows):
    error = True
    print "error: number of rows incorrect"

# if there was no error in the file then we better say it
if not error:
    print "File is in specified format"
