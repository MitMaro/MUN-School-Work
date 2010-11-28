#########################################################
##  CS 2500 (Fall 2008), Assignment #4, Question #2    ##
##   Script File Name: matm1.py                        ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
# matm1.py
# Written by Tim Oram for CS 2500 (Assignment 4, Question 2)
"""
Given a commands file performs read, print matrix, print dimensions, append (columns and rows) and add commands
from the commands file on matrixes stored in files
"""

import sys

# print a little usage message when the required parameters is not given
if len(sys.argv) != 2:
    print "usage: ", sys.argv[0], " commandfile"
    sys.exit(1) # halt script


# class that contains the list files 
class MatrixManipulator:
    
    # constructor, or well a instantiation function
    def __init__(self):
        self.files = {}
    
    # read file into a dictionary 
    def readMatrix(self, matrix):
        # try to read the file
        try:
            
            # initialize list for current matrix
            self.files[matrix] = []
            
            # for each line
            for line in file(matrix + ".mat"):
                # split the columns and map integer conversion to it
                cols = map(int, line.split());
                
                # append the list of columns to the list
                self.files[matrix].append(cols)
        
        # on file read error
        except IOError:
            print "Matrix Not Found"
            
    # prints out a given matrix
    def printMatrix(self, matrix):
        
        # if matrix has yet to be read
        if(not self.files.has_key(matrix)):
            print "Matrix not found"
            return
            
        # for each line and column print out the number
        for line in self.files[matrix]:
            for col in line:
                print col,
            print # add a new line after each line
    
    # prints out the dimensions of the matrix
    def printDimensions(self, matrix):
        
        # if matrix has yet to be read
        if(not self.files.has_key(matrix)):
            print "Matrix not found"
            return
        
        # print dimensions
        print "# rows = ", len(self.files[matrix]), "/ # columns = ", len(self.files[matrix][0])
        
    # Adds an amount to each value in a matrix
    def addTo(self, matrix, amount):
        
        # if matrix has yet to be read
        if(not self.files.has_key(matrix)):
            print "Matrix not found"
            return
        
        # for each value in the matrix (I wonder does python have another way to do this...)
        for i in xrange(len(self.files[matrix])):
            for j in xrange(len(self.files[matrix][i])):
                # add amount
                self.files[matrix][i][j] += amount
                
    # appends columns onto a matrix
    def appendCols(self, source, target):
        
        # if source matrix has yet to be read
        if not self.files.has_key(source):
            print "Source matrix not defined"
            return
        
        # if target matrix has yet to be read
        if not self.files.has_key(target):
            print "Target matrix not defined"
            return
        
        # check is the number of rows in the source matrix is equal to the number of rows in the target
        if len(self.files[target]) != len(self.files[source]):
            print "Row dimensions do not match"
            return
        
        # extend each row of the target matrix
        for i in xrange(len(self.files[target])):
            self.files[target][i].extend(self.files[source][i])  
            
    # appends rows onto a matrix
    def appendRows(self, source, target):
        
        # if source matrix has yet to be read
        if not self.files.has_key(source):
            print "Source matrix not defined"
            return
        
        # if target matrix has yet to be read
        if not self.files.has_key(target):
            print "Target matrix not defined"
            return
        
        # check is the number of columns in the source matrix is equal to the number of columns in the target
        if len(self.files[target][0]) != len(self.files[source][0]):
            print "Column dimensions do not match"
            return
        
        # for each row append on the columns to the end of the matrix
        for row in self.files[source]:
            # [:] creates a copy of the row, without this modifying source later on will modify target (and vice-versa)
            self.files[target].append(row[:])

# end MatrixManipulator class


# create a instance of the class
mm = MatrixManipulator()

# for each command in the commands file
for command in file(sys.argv[1]):
    
    # print the command
    print ">>>", command.strip()
    
    # split in two by white space
    cmd, args = command.strip().split(None, 1)
    
    
    if cmd == "read":
        mm.readMatrix(args)
    elif cmd == "print":
        mm.printMatrix(args)
    elif cmd == "dimensions":
        mm.printDimensions(args)
    elif cmd == "add":
        # split up the args
        amount, matrix = args.split(' to ')
        mm.addTo(matrix, int(amount))
    elif cmd == "append":
        
        # split out the operation and the matrixes
        oper, matrixes = args.split(' of ')
        
        # split up the matrixes into source and target
        source, target = matrixes.split(' to ')
        
        # if appending columns
        if oper == "columns":
            mm.appendCols(source, target)
        # or appending rows
        elif oper == "rows":
            mm.appendRows(source, target)
    
