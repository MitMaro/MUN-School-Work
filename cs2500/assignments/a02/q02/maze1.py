#########################################################
##  CS 2500 (Fall 2008), Assignment #2, Question #2    ##
##   Script File Name: maze1.py                        ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
# maze1.py
# Written by Tim Oram for CS 2500 (Assignment 2, Question 2)
"""
Given a maze file, a moves file and a start position (x, y) computes the final position 
 after all moves in the move file have been performed and prints this final position and
 its status. (outside maze, in wall, in corridor or at goal)
"""

import sys

# print a little usage message when the required parameters is not given
if len(sys.argv) != 5:
    print "usage: python", sys.argv[0], "mazefile movefile startX startY"
    sys.exit(1) # halt script
    
# initialize maze just in case an empty maze is given
maze = []

# read the maze and store it into a multi dimensional array
# well kinda, since a string is a character array I am going
# to use it as just that, an array
for mazeline in file(sys.argv[1]):
    maze.append(mazeline.rstrip())
    # print mazeline.rstrip()

# set current position (x, y)
posx = int(sys.argv[3])
posy = int(sys.argv[4])

# for each move in the moves file move the position
for move in file(sys.argv[2]):
    move = move.rstrip()
    if(move == "up"):
        posy += 1
    elif(move == "down"):
        posy -= 1
    elif(move == "left"):
        posx -= 1
    elif(move == "right"):
        posx += 1

# print the final position. note the ',' at the end it suppresses the ending newline
print "Final position (", posx, ",", posy, "): ",


# print out where the last position is 
# if position is out of the maze
# For the y position we need to subtract the length of maze from posy
# because we start in the bottom left corner not the top left
if posy < 0 or posx < 0 or posy > len(maze) or posx > len(maze[posy - 1]):
    print "outside maze"
elif(maze[len(maze) - posy][posx - 1] == "#"):
    print "inside wall"
elif(maze[len(maze) - posy][posx - 1] == "-"):
    print "in corridor"
elif(maze[len(maze) - posy][posx - 1] == "*"):
    print "at goal"
else:
    print "Invalid Maze"
