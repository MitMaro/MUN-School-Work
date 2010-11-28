#########################################################
##  CS 2500 (Fall 2008), Assignment #5, Question #1    ##
##   Script File Name: vocProg.py                      ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
# vocProg.py
# Written by Tim Oram for CS 2500 (Assignment 5, Question 1)
"""
Given a utterance-file, that consists of list of word & date pairs, as a command-line
argument computes and outputs, sorted by age, a list of the sets of words spoken for the
first time by the child
"""

import sys

# print a little usage message when the required parameter(s) is not given
if len(sys.argv) != 2:
    print "usage: ", sys.argv[0], " wordfile"
    sys.exit(1) # halt script

vocs = {} # contains a list of words in a dictionary by date
firstuse = {} # contains a use flag for each word

# read file
for line in file(sys.argv[1]):
    # split each line into the word and age
    word, age = line.split()
    
    # if this is the first time this age was seen
    if(not vocs.has_key(age)):
        # create a list for this age
        vocs[age] = []
    
    # add word to the list for this age 
    vocs[age].append(word)
    
    # add the word to the first use array
    # this will be used later to check if word has already been
    # listed at an earlier date
    firstuse[word] = True

# loop through the now sorted ages
for age in sorted(vocs):
    
    # reset these on each loop
    words = ""
    count = 0
    
    # for each word for the age
    for word in vocs[age]:
        # if this is the first time this word has been seen
        if firstuse[word]:
            # set the first use flag for this word to false
            firstuse[word] = False
            # add word to the words list
            words += word + " "
            # and increase the count
            count += 1
    
    # print out the information for this age
    print count, " word(s) newly acquired at ", age, " days :", words.rstrip()
    
