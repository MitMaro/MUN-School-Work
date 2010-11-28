#########################################################
##  CS 3710 (Fall 2008), Assignment #4, Question #1    ##
##   Script File Name: normalize.py                    ##
##       Student Name: Tim Oram                        ##
##         Login Name: mitmaro                         ##
##              MUN #: #########                       ##
#########################################################

import sys

# print a little usage message when the required paramaters are not given
if len(sys.argv) < 2:
    print "crossCheck.py <crossword-file>"
    sys.exit(1) # halt script

# the puzzle class because classes are fun in python and we don't get to learn
# them in python...
class Puzzle:
	def __init__(self, x, y):
		# list comprehensions for the win, initialize the maze
		self.puzzle = [["." for i in range(x)] for j in range(y)]

	def insertWordAtPosition(self, word, x, y, direction):
		# determine the direction
		if direction == "DOWN":
			# insert characters into the maze from location x,y going down
			for c in word:
				self.setCharacter(c, x, y)
				y += 1;
		elif direction == "ACROSS":
			# insert characters into the maze from location x,y going across
			for c in word:
				self.setCharacter(c, x, y)
				x += 1;

	# set the character at position x, y
	def setCharacter(self, char, x, y):
		# only update the character in the maze is it is empty
		if self.puzzle[y][x] == ".":
			self.puzzle[y][x] = char
		# if there is a conflict in characters
		elif self.puzzle[y][x] != char:
			self.puzzle[y][x] = "*"

	# the magic string method, makes things easier
	def __str__(self):
		# build the maze, use a list and join because it is faster than string
		# concatenation
		rtn = []
		for row in self.puzzle:
			rtn.append("    ")
			for col in row:
				rtn.append(col)
			rtn.append("\n")
		return "".join(rtn[:-1])

f = file(sys.argv[1])
# create the puzzle
[size_y, size_x] = map(int, f.readline().split())
p = Puzzle(size_x, size_y)

# read the lines in the file and follow the directions there in
for line in f.readlines():
	[direction, start_y, start_x, word] = line.split()
	p.insertWordAtPosition(word, int(start_x) - 1, int(start_y) - 1, direction)

# print the maze
print p
