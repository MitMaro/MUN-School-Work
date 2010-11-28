#########################################################
##  CS 2500 (Fall 2008), Assignment #1, Question #1    ##
##   Script File Name: sumfiles.py                     ##
##       Student Name: Tim Oram                        ##
##         Login Name: mitmaro                         ##
##              MUN #: #########                       ##
#########################################################
# sumfiles.py
# Written by Tim Oram for CS 2500 (Assignment 1, Question 2)
"""
Given a file that contains a list of files. Retrieve the data from those files and
 display the count, sum and average of all positive non-zero integers found in the
 files
"""

import sys

# print a little usage message when the required parameter is not given
if len(sys.argv) != 2:
    print "usage: python", sys.argv[0], "filename"
    sys.exit(1) # halt script
    

# create two variables for the sum and count and set them to 0
sum = 0
count = 0

# for each file given in the supplied parameter file
for filename in file(sys.argv[1]):

	# for each line in the data file
	for line in file(filename.rstrip()):
		
		# for each number in that line
		for number in line.split():
		
			number = int(number) # cast to int
			
			# only work with numbers greater the 0. Ignore the rest
			if number > 0:
				sum += int(number)
				count += 1
				
# check to make sure that count is greater then 0. This is done because we cannot divide
# by 0
if count > 0:
	print "Count = ", count, "/ Sum = ", sum,  "/ Average = ", sum / count
else:
	# the default message when no valid data is given
	print "Count =  0 / Sum =  0 / Average =  0"
