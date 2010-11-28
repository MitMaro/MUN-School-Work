#########################################################
##  CS 2500 (Fall 2008), Assignment #6, Question #2    ##
##   Script File Name: matm2.py                        ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
# matm2.py
# Written by Tim Oram for CS 2500 (Assignment 6, Question 2)
"""
Given a commands file performs read, print matrix, print dimensions, append
 (columns and rows) and add commands from the commands file on matrixes stored
 in files. This time using functions in place of a class.
 
 I don't have a getMatrixIndex function cause I use a dictionary in place of a 
 list.
 
 This version feels more clumsy then the last, this is because I am not printing
 anything out in the functions this time. This means more return checking and as
 such some somewhat redundant code.
"""

import sys

# print a little usage message when the required parameters is not given
if len(sys.argv) != 2:
	print "usage: ", sys.argv[0], " commandfile"
	sys.exit(1) # halt script

    
# read file into a dictionary 
def readMatrix(matList, matName):

	# try to read the file
	try:
		
		# initialize list for current matrix
		matList[matName] = []
		
		# for each line
		for line in file(matName + ".mat"):
			# split the columns and map integer conversion to it
			cols = map(int, line.split());
			# append the list of columns to the list
			matList[matName].append(cols)
			
		# matrix was read
		return True

	# on file read error
	except IOError:
		# delete the list create above for this matrix since it could not be read
		# this was not a problem with the test case given but it was still a problem
		# since reading a matrix that could not be read then printing that matrix
		# would print an empty string
		del matList[matName]
		return False



# Adds an amount to each value in a matrix
def incMatrix(matList, matName, val):
	
	# if matrix has yet to be read
	if(not matList.has_key(matName)):
		return False
	
	# for each value in the matrix
	for i in xrange(len(matList[matName])):
		for j in xrange(len(matList[matName][i])):
			# add amount
			matList[matName][i][j] += val

	# all went well lets return true
	return True

# prints out a given matrix
def getMatrixString(matList, matName):
	
	# if matrix has yet to be read, however I check this
	# before calling this function anyways
	if(not matList.has_key(matName)):
		return ""   
	
	# for each line and column print out the number
	rtn = ""
	for line in matList[matName]:
		for col in line:
			rtn += str(col) + " "
		rtn += "\n" # add a new line after each line

	# return the matrix description
	return rtn.rstrip()


# prints out the dimensions of the matrix
def getDimensions(matList, matName):

	# if matrix has yet to be read, however I check this
	# before calling this function anyways
	if(not matList.has_key(matName)):
		return ""   
	
	# return the matrix dimensions as a string
	return "# rows =  " + str(len(matList[matName])) + " / # columns =  " + str(len(matList[matName][0]))

	
                
# appends columns onto a matrix
def appendColumns(matList, matName1, matName2):
	
	# check is the number of rows in the source matrix is equal to the number of rows in the target
	if len(matList[matName2]) != len(matList[matName1]):
		return False
	
	# extend each row of the target matrix
	for i in xrange(len(matList[matName2])):
		# add the row of the source matrix
		matList[matName2][i].extend(matList[matName1][i])
		
	return True
       
# appends rows onto a matrix
def appendRows(matList, matName1, matName2):
	 
	# check is the number of columns in the source matrix is equal to the number of columns in the target
	if len(matList[matName2][0]) != len(matList[matName1][0]):
		return False
	
	# for each row append on the columns to the end of the matrix
	for row in matList[matName1]:
		# [:] creates a copy of the row, without this modifying source later on will modify target (and vice-versa)
		matList[matName2].append(row[:])
	
	return True


# this will hold all the matrices
matList = {}

# for each command in the commands file
for command in file(sys.argv[1]):

	# print the command
	print ">>>", command.strip()

	# split in two by white space
	cmd, args = command.strip().split(None, 1)


	if cmd == "read":
		if not readMatrix(matList, args):
			print "Matrix not found"
	
	elif cmd == "print":
		if(matList.has_key(args)):
			print getMatrixString(matList, args)
		else:
			print "Matrix not found"
			
	elif cmd == "dimensions":
		if(matList.has_key(args)):
			print getDimensions(matList, args)
		else:
			print "Matrix not found"
			
	elif cmd == "add":
		# split up the args
		amount, matrix = args.split(' to ')
		if not incMatrix(matList, matrix, int(amount)):
			print "Matrix not found"

	elif cmd == "append":	
		
		# split out the operation and the matrixes
		oper, matrixes = args.split(' of ')
		# split up the matrixes into source and target
		source, target = matrixes.split(' to ')

		# if source matrix has yet to be read
		if not matList.has_key(source):
			print "Source matrix not defined"
		
		# if target matrix has yet to be read
		elif not matList.has_key(target):
			print "Target matrix not defined"
		
		else:

			# if appending columns
			if oper == "columns":
				if not appendColumns(matList, source, target):
					print "Row dimensions do not match"
			# or appending rows
			elif oper == "rows":
				if not appendRows(matList, source, target):
					print "Column dimensions do not match"

