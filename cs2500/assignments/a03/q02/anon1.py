#########################################################
##  CS 2500 (Fall 2008), Assignment #3, Question #2    ##
##   Script File Name: anon1.py                        ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
# anon1.py
# Written by Tim Oram for CS 2500 (Assignment 3, Question 2)
"""
Takes as command line arguments a text file and a file of single-word proper
names, and creates a file in which each occurrence of a specified proper name
is replaced by the string "A#####", where # is the line right-justified,
zero-filled line number of that in the name-file. 
"""

import sys
import re

# print a little usage message when the required parameters are not given
if len(sys.argv) < 3:
    print "usage:", sys.argv[0],  "textfile namefile"
    sys.exit(1) # halt script

# read in the whole text file to be anonymized
f = open(sys.argv[1])
text = f.read()
f.close()

# read in the names to be anonymized
f = open(sys.argv[2])
names = f.readlines()
f.close()

# for each name in the list of names
for name in xrange(len(names)):
    # create a regex to look for the name
    reg = re.compile('([.,()\\\' ])' + names[name].rstrip() + '([.,()\\\' ])')
    # replace the name with A#####
    text = reg.sub('\\1A%(#)05d\\2' % {'#':name+1}, text) # Don't you love regular expressions

# open the file to write the data too and write
f = open(sys.argv[1] + '.anon', 'w')
f.write(text)
f.close()
