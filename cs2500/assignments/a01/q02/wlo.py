#########################################################
##  CS 2500 (Fall 2008), Assignment #1, Question #2    ##
##   Script File Name: wlo.py                          ##
##       Student Name: Tim Oram                        ##
##         Login Name: mitmaro                         ##
##              MUN #: #########                       ##
#########################################################
# wlo.py
# Written by Tim Oram for CS 2500 (Assignment 1, Question 2)
"""
Searches for a given needle(s) (word) in a haystack (lines). The haystack is taken from
 a supplied file and the needle(s) are given as parameters after the filename.
"""

import sys

# print a little usage message when the required parameters are not given
if len(sys.argv) < 3:
    print "usage: python", sys.argv[0], "filename word1 word2 ..."
    sys.exit(1) # halt script


# open the given file, grab the lines and then close the file    
f = open(sys.argv[1])
lines = f.readlines()
f.close

# for each needle given in the command line
for needle in sys.argv[2:]:
	print needle, ":"
	
	# found flag, goes to true when the needle is found. If this is false after every line
	# has been searched the 'No occurrences' message is printed
	found = False
	
	# loop through the lines
	for i in xrange(len(lines)):
		
		# check the line and for each hay compare it against the needle if they are same
		# print a nice message.
		for hay in lines[i].split(): # needle in the hay stack :)
			if hay == needle:
				found = True # set our flag to true
				print "  >>> Line #", i + 1, ": ", lines[i].rstrip()
				break # i hate breaks...
				
	# if our needle wasn't found in any line then print a nice message
	if not found:
		print "  >>> No occurrences"
