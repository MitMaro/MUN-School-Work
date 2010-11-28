#########################################################
##  CS 3710 (Fall 2008), Assignment #3, Question #1    ##
##   Script File Name: normalize.py                    ##
##       Student Name: Tim Oram                        ##
##         Login Name: mitmaro                         ##
##              MUN #: #########                       ##
#########################################################

import sys

# print a little usage message when the required paramaters are not given
if len(sys.argv) < 3:
    print "format: normalize.py <input-file> <output-file>"
    sys.exit(1) # halt script


# read in and convert lines to floats
input_numbers = map(float, file(sys.argv[1], "r"))

# I did this in one line to challenge my python skills, see below for a
# more verbose version. I could have gotten the file read line above into this
# if I had read the file twice
file(sys.argv[2], 'w').writelines(map(lambda number: str(number) + "\n",
            [x - sum(input_numbers)/len(input_numbers) for x in input_numbers]))



# get the average of the list of numbers
average = sum(input_numbers)/len(input_numbers)
f = file(sys.argv[2], 'w')
# foreach line
for x in input_numbers:
	# fine the difference
	diff = x - average
	# write it to the file
	f.write(str(diff) + "\n")
