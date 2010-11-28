#########################################################
##  CS 2500 (Fall 2008), Assignment #4, Question #1    ##
##   Script File Name: maze2.py                        ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
# maze2.py
# Written by Tim Oram for CS 2500 (Assignment 4, Question 1)
"""
Given a maze file, a moves file and a start position (x, y) each move is performed and its coordinates and status are printed out.
 If a move results in its status being in a wall the move-sequence is terminated. If all moves are valid the final position 
 and status after all moves in the move file have been performed is printed.

 Status is one of outside maze, in wall, in corridor or at goal
"""

import sys

# print a little usage message when the required parameters is not given
if len(sys.argv) != 5:
    print "usage: ", sys.argv[0], " mazefile movefile startX startY"
    sys.exit(1) # halt script



# I made this a class cause for one it makes things nicer and well
# I also wanted to give classes in python a shot

# Maze class. Holds the maze and some functions that will help.
class Maze:

    # constructor, or well a instantiation function
    def __init__(self):
        # set some defaults
        self.maze = []
        self.x = 0
        self.y = 0
        
    # adds a line to the maze
    def addMazeLine(self, line):
        self.maze.append(line.strip())
    
    # return the location of the current position
    def getLocation(self):
        
        # return the location
        if self.y < 0 or self.x < 0 or self.y > len(self.maze) - 1 or self.x > len(self.maze[self.y]):
            return "outside maze"
        elif(self.maze[self.y][self.x] == "#"):
            return "inside wall"
        elif(self.maze[self.y][self.x] == "-"):
            return "in corridor"
        elif(self.maze[self.y][self.x] == "*"):
            return "at goal"
        # because (almost) every if should have a else
        else:
            return "Invalid Maze"

    # sets the start position
    def setMazeStart(self, x, y):
        
        # do some math cause my start is (0,0) in the top left corner
        # but the maze starts at (1,1) in bottom left corner 
        self.x = x - 1
        self.y = len(self.maze) - y
    
    # prints the location in the maze and stops the script if position is inside wall
    def printLocation(self):
        
        # grab the location
        location = self.getLocation()
        
        # if the location was found to be in a wall, and well we can't walk through walls (yet)
        if(location == "inside wall"):
            # print a location message
            print "( %d , %d ): inside wall: terminate moves" % (self.x + 1, len(self.maze) - self.y)
            sys.exit(1) #halt script
        else:
            # print a location message
            print "( %d , %d ): %s" % (self.x + 1, len(self.maze) - self.y, location)
        
    # make a move in the maze
    def makeMove(self, move):
        # move a position in maze
        move = move.strip()
        if(move == "up"):
            self.y -= 1
        elif(move == "down"):
            self.y += 1
        elif(move == "left"):
            self.x -= 1
        elif(move == "right"):
            self.x += 1

# end Maze class


# read the moves file
f = open(sys.argv[2])
moves = f.readlines()
f.close

# create a new instance of the maze class
maze = Maze()

# read the maze into the maze class
for line in file(sys.argv[1]):
    maze.addMazeLine(line)

# set current position (x, y)
maze.setMazeStart(int(sys.argv[3]), int(sys.argv[4]))

# print initial position (comma at end to stop newline)
print "Initial position",
maze.printLocation()

# make the moves besides the last
for move in xrange(len(moves) - 1):
    maze.makeMove(moves[move])
    
    #print the position
    print "Position #", move + 1,
    maze.printLocation()


# make the last move
maze.makeMove(moves[move + 1])

# print final position
print "Final position",
maze.printLocation()
