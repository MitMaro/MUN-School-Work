#########################################################
##  CS 2500 (Fall 2008), Assignment #8, Question #1    ##
##   Script File Name: CalculatorValues.py             ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
#    File: CalculatorValues.py
# Project: CS 2500 (Assignment 8, Question 1)
#      By: Tim Oram [t.oram@mitmaro.ca]
# Created: November 26, 2008; Updated December 01, 2008
# Purpose: Wraps a list that stores values for the calculator
"""
The CalculatorValues class is a class that wraps around a list of values thats
used by the Calculator class.

It performs the following tasks:
  - Makes sure there is always one value in the list (could be an empty value)
  - Performs validation on data added to the list (numbers are always stored as
    Numbers)
  - Returns a list of Decimals that have the equivalent values
  - An pop method that will not allow you to remove the last item in the list
	- add, update and get methods.
	- Iterable functionality.
 
The reason I created this class was that I was doing most of the functionality
that this class is doing in the Calculator class and I was repeating the same
data check over and over again. As well when there was an error in one of those
data checks I would have to search the whole code to fix it.
"""

# the important datatypes
from decimal import *
from Number import *


class CalculatorValues:
	
	def __init__(self, values = None):
		
		# the iteration index
		self.iteri = 0
		
		# if values are given then add them
		if(values != None):
			self.values = []
			for v in values:
				self.addValue(v)
		else:
			# else we add one value as a number
			self.values = [Number()]
			
				
	def addValue(self, value = None):
		""" Adds a value to the values list assuring that it is a Number
		"""
		# if given value is None (or not given)
		if(value == None):
			self.values.append(Number())
		# if the given value is already a Number just append it
		elif(isinstance(value, Number)):
			self.values.append(value)
		# if the given value is a Decimal convert it to Number
		elif(isinstance(value, Decimal)):
			self.values.append(Number(str(value)))
		else:
			self.values.append(Number())
			# this error is not checked for and is only used for debugging and
			# development purposes. This error being raised is means a bug in the
			# script
			raise TypeError("Type must be of type Number or Decimal")
			
	def popValue(self):
		""" Removes a value from the values list but does a check first """
		
		# if the list index is not yet 0
		if(len(self.values) > 1):
			# pop a value from the list
			self.values.pop()
		else:
			# else if it is at 0 reset the value to a number again
			self.values[0] = Number()
	
	def updateValue(self, value, index = None):
		""" Updates a value in the values list and validates the value is one is
		given """
		
		# if no index is given then set index to the last value in the list
		if(index == None):
			index = len(self.values) - 1
		
		# raise an error if the index is out of bounds (again this is never checked
		# for and if risen then is considered a bug in the script)
		if(len(self.values) <= index):
			# set index to last
			index = len(self.values) - 1
			raise IndexError()

		# if given value is None (or not given)
		if(value == None):
			self.values[index] = Number()
		# if the given value is already a Number just append it
		elif(isinstance(value, Number)):
			self.values[index] = value
		# if the given value is a Decimal convert it to Number
		elif(isinstance(value, Decimal)):
			self.values[index] = Number(str(value))
		else:
			# if this error is risen there is a bug in the script
			raise TypeError("Type must be of type Number or Decimal")
	
	def getValue(self, index = None):
		""" Returns the last values if no index is given or the value of the index
		at the provided index. This method is kinda pointless since its
		functionality is equivalent to the __getitem__ method below. I think I don't
		even use this method anymore."""
		
		# if index is none then return the last value
		if(index == None):
			index = len(self.values) - 1
		
		# if the index is an invalid one raise an exception
		if(len(self.values) <= index):
			raise IndexError()
		
		# return the requested value
		return self.values[index]
	
	def getValues(self):
		""" Returns the internal list of values """
		return self.values
	
	def getValueAsDecimal(self, index = None):
		""" Returns a value at index as a decimal """
		return Decimal(self.getValue(index).getNumber())

	def valuesAsDecimal(self):
		""" Returns the whole list of values as Decimal """
		rtn = []
		for v in self.values:
			rtn.append(Decimal(v.getNumber()))
		return rtn
	
	# the length of the list of values
	def __len__(self):
		return len(self.values)
	
	# next method that is used while iterating
	def next(self):
		# if the iterable index is beyond the list
		if self.iteri >= len(self.values):
			# reset the index back to 0
			self.iteri = 0
			# raise a StopIteration exception
			raise StopIteration
		else:
			# else increase iterator index
			self.iteri = self.iteri + 1
			# and return a value
			return self.values[self.iteri - 1]
	
	# basic getitem method
	def __getitem__(self, item):
		return self.values[item]
	
	# basic setitem method
	def __setitem__(self, key, item):
		self.values[key] = item
	
	# since this is iterable we can return its self to python for iteration
	def __iter__(self):
		return self
		
	