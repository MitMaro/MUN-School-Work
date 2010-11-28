#########################################################
##  CS 2500 (Fall 2008), Assignment #8, Question #1    ##
##   Script File Name: History.py                      ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
#    File: History.py
# Project: CS 2500 (Assignment 8, Question 1)
#      By: Tim Oram [t.oram@mitmaro.ca]
# Created: November 26, 2008; Updated December 02, 2008
# Purpose: Creates a list of iterable histories
"""
Contains a list of histories. Allows for iteration over the list (both forward
and back). The standard Python iterator only allowed forward transversal only
and I wanted both. The history class also stores the current value from the
calculator and a method that returns true when the history is being used.
"""

class History:
	
	def __init__(self):
		# the list the holds the histories
		self.list = []
		
		# the iterable index
		self.iter = -1
		
		# the current value of the calculator
		self.current = None
	
	# insert a history at the beginning of the list
	def insert(self, value):
		self.list.insert(0, value)
		self.iter = -1
	
	# returns true if there is a next value
	def isNext(self):
		return len(self.list) > self.iter + 1
	
	# returns the next value in the list
	def next(self):
		# if not at the end of the list (guess I could call isNext() here)
		if(len(self.list) > self.iter + 1):
			# increase the iterator
			self.iter += 1
			# and return the value
			return self.list[self.iter]
		
		# else return none
		return None
	
	# true when there is a previous value
	def isPrev(self):
		return self.iter - 1 >= 0

	def prev(self):
		# check for next value in list (again could use isPrev() here)
		if(self.iter > 0):
			# decrease the iterator
			self.iter -= 1
			# return the value
			return self.list[self.iter]
		# else we return none
		return None
	
	# returns the current value
	def currentValue(self):
		# if the iterable index is not -1
		if(self.iter >= 0):
			# return the current value
			return self.list[self.iter]
		# else return None
		return None
		
	# reset the iterable index and set current no none
	def reset(self):
		self.iter = -1
		self.current = None
	
	# returns true when the history is in use
	def usingHistory(self):
		return self.iter != -1
			