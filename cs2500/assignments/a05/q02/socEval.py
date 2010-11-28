#########################################################
##  CS 2500 (Fall 2008), Assignment #5, Question #2    ##
##   Script File Name: socEval.py                      ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
# socEval.py
# Written by Tim Oram for CS 2500 (Assignment 5, Question 2)
"""
Given an opinion file as a command line argument, computes the group-opinion resulting from the opinions in the file
and outputs the final group opinions of all members of the group sorted in reverse order by group opinion value.
"""

import sys

# print a little usage message when the required parameter(s) is not given
if len(sys.argv) != 2:
    print "usage: ", sys.argv[0], " socialfile"
    sys.exit(1) # halt script


# open file
f = open(sys.argv[1])

# holds the group matrixes
soc = {}
# holds the average opinion of each person
avgs= {}

# read in the first line of the file and split it
names = f.readline().split()

# initialize the names matrix 
for name in names:
    soc[name] = {}
    for other in names:
        soc[name][other] = 0.0


# read the rest of the lines and iterate over them
for line in f.readlines():
    # split the line into its parts
    p1, cond, p2 = line.split()
    
    # depending on the opinion of p1 to p2 set the value in the matrix
    if cond == "likes":
        soc[p1][p2] = 1.0
    elif cond == "ignores":
        soc[p1][p2] = 0.0
    elif cond == "dislikes":
        soc[p1][p2] = -1.0

# close the file
f.close()

# for each person calculate the average opinion
for name in soc:
    total = 0.0
    # total up opinions
    for other in soc:
        total += soc[other][name]
    
    # divide by the number of people less one
    avgs[name] = total / (len(soc[name]) - 1.0) 

# create a list we can sort
avgs = [(avgs[avg], avg) for avg in avgs]

# sort and reverse
avgs.sort()
avgs.reverse()

# print it all out
for p in avgs:
    print '% .3f : %s' % (p[0], p[1])




    