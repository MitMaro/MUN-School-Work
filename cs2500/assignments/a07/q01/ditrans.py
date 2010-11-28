#########################################################
##  CS 2500 (Fall 2008), Assignment #7, Question #1    ##
##   Script File Name: ditrans.py                      ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
# ditrans.py
# Written by Tim Oram for CS 2500 (Assignment 7, Question 1)
"""
A program to translate between two dialects of the same language that can be
specified by :
  i) a set of mapping rules of the form "X" => "Y" indicating that text X in
     dialect #1 maps onto text Y in dialect #2.
 ii) a (possibly empty) list of interjections that are randomly inserted before
     the beginnings of sentences in dialect #1 to create valid sentences in
     dialect #2.
iii) a mechanism that applies (i) and (ii) above to a piece of text from
     dialect #1 to create the corresponding piece of text in dialect #2.
     
 The mechanism in (iii) first applies the rules in (i) in the order in which
 they are specified to a sentence of dialect #1 text before inserting a
 random interjection from the list in (ii) in front of that translated
 sentence. Note that the list in (ii) is augmented by (iii) to include the empty
 string, which simulates the condition in which no interjection is added.
 
 Blank lines and comment-lines (indicated by initial hash marks (#)) which are
 ignored by the translation mechanisms in (iii). 
"""

import sys
import re
import random

# print a little usage message when the required parameters is not given
if len(sys.argv) != 3:
	print "usage: ", sys.argv[0], " transfile textfile"
	sys.exit(1) # halt script

# holds the translations
transMatrix = {}


# load translation file
dias = file(sys.argv[1]).read()
# load text file
text = file(sys.argv[2]).readlines()


## remove commented lines ##
dias = re.sub(r"#.*?(\n|.$)", "", dias) # will remove `#aaa"` from `"bbb#aaa"`


## generate interjections list ##
# find interjection line
interjections = re.search(r"INTERJECTIONS\s*=\s*\{(.*?)}", dias)


# if the interjection line was found
if interjections:
	# parse the interjection line (could use eval, but eval is dangerous)
	interjections = re.findall(r"\"(.*?)\"", interjections.group(1))
# if not found just an empty list
else:
	interjections = []
# add empty string to interjections
interjections.append("");


## generate translation matrix ## 
# could also use just re.findall but I wanna try this named groups thingy:
for t in re.finditer(r"\"(?P<eng>.*?)\"\s*=\>\s*\"(?P<pir>.*?)\"", dias):
	transMatrix[t.group('eng')] = t.group('pir')


## parse the text file and output ##
# for each line of text
for line in text:

	# print the english line
	print ">>> ", line,
	
	# replace all lines with the translations
	for r in transMatrix.keys():
		line = re.sub(r, transMatrix[r], line)
	
	# get random injection
	inj = random.sample(interjections, 1)[0]
	
	# if the injection was an empty string don't print it
	if len(inj) == 0:		
		print "    ", line,
	# else print the injection
	else:
		print "    ", inj, line,
