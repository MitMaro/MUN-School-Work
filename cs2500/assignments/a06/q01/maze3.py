#########################################################
##  CS 2500 (Fall 2008), Assignment #6, Question #1    ##
##   Script File Name: maze3.py                        ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
# maze3.py
# Written by Tim Oram for CS 2500 (Assignment 6, Question 1)
"""
Given a maze file, a moves file and a start position (x, y) each move is
 performed and its coordinates and status are printed out. If a move results in
 its status being in a wall the move-sequence is terminated. If all moves are
 valid the final position and status after all moves in the move file have been
 performed is printed. This time using functions in place of a class, kind of
 going backwards.

 Status is one of outside maze, in wall, in corridor or at goal
"""

import sys

# print a little usage message when the required parameters is not given
if len(sys.argv) != 5:
	print "usage: ", sys.argv[0], " mazefile movefile startX startY"
	sys.exit(1) # halt script


# returns a list representation of the maze given a maze file name
def readMaze(mazeFilename):
	
	# create a list
	maze = []
	
	# read the maze into a list
	for line in file(mazeFilename):
		maze.append(line.strip())
		
	return maze



# return the location of the current position
def getPostStatus(maze, x, y):
  
  # return the location
  if y < 0 or x < 0 or y > len(maze) - 1 or x > len(maze[y]):
      return "outside maze"
  elif(maze[y][x] == "#"):
      return "inside wall"
  elif(maze[y][x] == "-"):
      return "in corridor"
  elif(maze[y][x] == "*"):
      return "at goal"


# make a move in the maze
def makeMazeMove(maze, x, y, move):
  # move a position in maze
  move = move.strip()
  if(move == "up"):
      y -= 1
  elif(move == "down"):
      y += 1
  elif(move == "left"):
      x -= 1
  elif(move == "right"):
      x += 1
      
  return x, y

# prints the location in the maze and stops the script if position is inside wall
def printLocation(maze, x, y):
  
  # grab the location
  location = getPostStatus(maze, x, y)
  
  # if the location was found to be in a wall, and well we can't walk through walls (yet)
  if(location == "inside wall"):
    # print a location message
    print "( %d , %d ): inside wall: terminate moves" % (x + 1, len(maze) - y)
    sys.exit(1) #halt script
  else:
    # print a location message
    print "( %d , %d ): %s" % (x + 1, len(maze) - y, location)
        

# read the maze file
maze = readMaze(sys.argv[1])

# read the moves file
f = open(sys.argv[2])
moves = f.readlines()
f.close

# do some math cause my start is (0,0) in the top left corner
# but the maze starts at (1,1) in bottom left corner 
pos_x = int(sys.argv[3]) - 1
pos_y = len(maze) - int(sys.argv[4])

# print initial position (comma at end to stop newline)
print "Initial position",
printLocation(maze, pos_x, pos_y)

# make the moves besides the last
for move in xrange(len(moves) - 1):
	
	pos_x, pos_y = makeMazeMove(maze, pos_x, pos_y, moves[move])
	
	#print the position
	print "Position #", move + 1,
	printLocation(maze, pos_x, pos_y)


# make the last move
pos_x, pos_y = makeMazeMove(maze, pos_x, pos_y, moves[move + 1])

# print final position
print "Final position",
printLocation(maze, pos_x, pos_y)
