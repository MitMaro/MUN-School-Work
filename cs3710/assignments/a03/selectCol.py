#########################################################
##  CS 3710 (Fall 2008), Assignment #3, Question #2    ##
##   Script File Name: selectCol.py                    ##
##       Student Name: Tim Oram                        ##
##         Login Name: mitmaro                         ##
##              MUN #: #########                       ##
#########################################################

import sys

# print a little usage message when the required paramaters are not given
if len(sys.argv) < 4:
    print "format: selectCol.py <input-file> <output-file> <col1-spec> <col2-spec> ..."
    sys.exit(1) # halt script

format = ""
cols = []
# create the format string
for f in sys.argv[3:]:
	f = f.split(":")
	# left/right alignment
	if(f[1] == "L"):
		format += "%-" + f[2] + "s"
	else:
		format += "%" + f[2] + "s"
	# keep track of the number and order of columns
	cols.append(int(f[0]))
# end the format string with a newline
format += "\n"

f = file(sys.argv[2], 'w')

# write out the lines
for row in map(str.split, file(sys.argv[1])):
	# why list comprehensions won't take a list I do not know
	f.write(format % tuple([row[i - 1] for i in cols]))

f.close()
