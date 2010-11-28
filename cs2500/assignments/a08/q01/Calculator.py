#########################################################
##  CS 2500 (Fall 2008), Assignment #8, Question #1    ##
##   Script File Name: Calculator.py                   ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
#    File: Calculator.py
# Project: CS 2500 (Assignment 8, Question 1)
#      By: Tim Oram [t.oram@mitmaro.ca]
# Created: November 24, 2008; Updated December 03, 2008
# Purpose: A class that contains everything to perform any form of calculation.
"""
This file contains the Calculator class which is the heart of the calculator
application. A more extensive description of what this class does is provided
within the class.
"""

# import the decimal modules to avoid most float problems
from decimal import *

# Very Java like a different file for each class. :(
from Number import *
from CalculatorValues import *
from History import *

class Calculator:
	""" Calculator Class (framework)

	This class contains within the basic functionality of a calculator. Includes
	both function and answer history, display interaction, input validation, Clear
	Entry, Clear, Input Deletion (backspace).

	The calculator framework allows operations & functions to be added easily.
	All that is required is to add the operation/function to the functions
	dictionary (see below) and then create a bind to the funcHandle method.
	History, user input (with validation) and the display of values, and the
	calculation will automatically be handled by this class.

	To add a operator or function to the class you need to add a list to the
	functions dictionary in the format:
	   'name': [function, "Display Text", Number of Arguments, type]
	   And then bind funcHandle('name') to a button (or something else).
	   Examples:
	      Add Operator:
	      The function: def add(a, b): return a + b
	      Add 'add':[add, "+", 2, Calculator.TYPE_OPERATOR] to the functions
	      dictionary. This example will allow the addition of a value to the
	      current result. `a` will automatically be assigned the value of the
	      current result and `b` will contain the input from the user. The add
	      function will then be called and its return value becomes the new
	      result. For example if the current result is 4 and the user selects
	      the + operator and inputs 3 and then equals. The new result will be 7.

	      Volume of Box Function:
	      The function: def box_volume(h, w, l): return h * w * l
	      Add 'boxvol': [box_volume, "BoxVolume", 3, Calculator.TYPE_FUNCTION]
	      to the functions dictionary. When this function is selected the user
	      will be given the opportunity to supply the arguments and then the
	      answer will be calculated. For example with a box thats 2x3x1 a user
	      using this function would select the BoxVolume function and input
	      2, 3, 1 and a result of 6 will be given.

				Example binds for the two examples above are:
				self.bind_all("<<StandOps-+>>", lambda e: self.funcHandle('add'))
				self.bind_all("<<GeoFuncs-BVolume>>", lambda e: self.funcHandle('boxvol'))

				The event is not important but the parameter of the funcHandle
				method must be the correct index in the functions dictionary.


	Methods that should be binded in the Application using this class:
	  funcHandle('name') - Bind to all operations and functions. (See Above)
	  equals() - Bind to the equals button/key
	  argsSeparatorHandle() - Bind this to a ',' comma button/key
	  setValue(value) - set the current value (useful for constants)
	  inputHandle() - Bind to 0-9, e, decimal and unary minus buttons/key
	  clear() - bind to the clear button (clears everything)
	  clearEntry() - bind to the clear entry button (clears current entry)
	  back() - bind to the backspace button/key. Deletes one character in the input
	  funcHistoryPrev(), funcHistoryNext(), ansHistoryPrev(), ansHistoryNext()
	    - bind to buttons to handle history.

	"""

	# some constants (well as about as close as you can get to a constant)
	# names of elements in function description list/dictionary
	FUNC_FUNCTION = 0
	FUNC_TEXT = 1
	FUNC_NUM_ARGS = 2
	FUNC_TYPE = 3

	# function types
	TYPE_OPERATOR = 0
	TYPE_FUNCTION = 1

	def __init__(self, display):
		# holds the result of calculations
		self.result = Decimal()

		# the function and answer history. I don't have a limit on the history and
		# I probably should. But I doubt we will run out of memory for what is
		# stored.
		self.funcsHistory = History()
		self.ansHistory = History()

		# set the display (Should be a CalcWidgets Display but as long as it has a
		# method setText(line1, line2) it should work fine)
		self.display = display

		# calculator functions (Empty till filled in by the Application class)
		self.functions = {}

		# reset (or in this case initialize) some values
		self.__resetValues()

		# set the text of the display
		self.__setText()


	def __resetValues(self):
		""" Resets some values that need to reset often and reset together """

		# values information
		self.values = CalculatorValues()

		# current function
		self.function = None

		# reset history
		self.funcsHistory.reset()
		self.ansHistory.reset()

		# becomes something other then None when there is an error to be shown
		self.error = None

	def funcHandle(self, funcName):
		""" Handles all function/operator calls. """

		# remove error on other input
		if(self.error != None):
			self.error = None

		# perform the last function (virtual equals)
		self.__performFunction()

		# if the current supplied function is in the functions array
		if(funcName in self.functions):

			# set the value of the function
			self.function = self.functions[funcName]

			# if the type is operator (Turns an operator into a function)
			# a op b becomes func(a, b)
			if(self.function[self.FUNC_TYPE] == self.TYPE_OPERATOR):
				# and add room for another value (1st will contain current result)
				self.values.addValue()

		else:
			# The final product should never end up here. But just in case.
			self.error = "Function Not Defined"

		# refresh display
		self.__setText()

	def equals(self):
		""" Function for the equals button. """
		# just call the __performFunction method
		self.__performFunction()

	def __performFunction(self):
		""" The equals method. Takes the given input from the user and gives an
		answer."""
		# if there is a function waiting
		if(self.function != None):
			# if the function if of type function
			if(self.function[self.FUNC_TYPE] == self.TYPE_FUNCTION):
				# check number of arguments given to number required
				# could use inspect.getargspec(self.function[self.FUNC_FUNCTION])
				if(len(self.values) == self.function[self.FUNC_NUM_ARGS] and not self.values[-1].isEmpty()):

					# try this and catch any error. The possible errors are endless since
					# every funciton has a different one.
					try:
						# set the result to the return of the function
						self.result = self.function[self.FUNC_FUNCTION](*self.values.valuesAsDecimal())
						# if the returned value is not a Decimal then convert it (normally the
						# only other possible type would be a float)
						if(not isinstance(self.result, Decimal)):
							self.result = Decimal(str(self.result))

						# record some history
						self.funcsHistory.insert((self.function, CalculatorValues(self.values)))
						self.ansHistory.insert(self.result)
					# this will catch any errors that occur. Since the calculators functions
					# can produce any type of error then I have to catch them all.
					except Exception, e:
						self.result = Decimal("NAN");
						self.error = e.message
						self.__setText()
						return



				# not enough arguments so show error
				elif(len(self.values) > 1  or not self.values[0].isEmpty()):
					# reset values since there was an error
					self.__resetValues()
					# set the error message
					self.error = "ARGUMENTS ERROR"
					# refresh display
					self.__setText()
					return # early exit
			# if the function type is an operator
			elif(self.function[self.FUNC_TYPE] == self.TYPE_OPERATOR):

				# if there was a value given
				if(len(self.values) >= 2 and not self.values[1].isEmpty()):

					# set the first value to be the current result
					self.values.updateValue(self.result, 0)

					try:
						# perform operation, make sure to convert values from Numbers to
						# Decimals first.
						self.result = self.function[self.FUNC_FUNCTION](*self.values.valuesAsDecimal()[:2])
					# this will catch any errors that occur. Since the calculators functions
					# can produce any type of error then I have to catch them all.
					except Exception, e:
						self.result = Decimal("NAN");
						self.error = e.message
						self.__setText()
						return

					# if the returned value is not a Decimal then convert it (normally the
					# only other possible type would be a float)
					if(not isinstance(self.result, Decimal)):
						self.result = Decimal(str(self.result))

					# create the values to add to history
					tmp =  CalculatorValues()
 					# add the user supplied value
					tmp.addValue(self.values[1])
					# add this the history lists
					self.funcsHistory.insert((self.function, tmp))
					self.ansHistory.insert(self.result)
					# delete tmp cause its no longer needed
					del tmp

		# if no function but there is a value
		elif(not self.values[-1].isEmpty()):
			# set the result to the value
			self.result = Decimal(self.values[0].getNumber())
			# and add to the answer history
			self.ansHistory.insert(self.result)

		# reset the values
		self.__resetValues()

		# and update the display
		self.__setText()

	def setValue(self, value):
		""" Allows you to set the value. """
		# set the value
		self.values.updateValue(Number(value))

		# set the display text
		self.__setText()

	def argsSeparatorHandle(self):
		""" This code runs when the function argument separator if clicked."""
		# if a function is set
		if(self.function != None and
				# check that we don't already have enough arguments
				self.function[self.FUNC_NUM_ARGS] != len(self.values)):

			# increase the value of the value (argument) index
			self.values.addValue()

			# reset history index since this is a new value
			self.opsHistoryIndex = 0

			# set the display text
			self.__setText()

	def inputHandle(self, input):
		""" The method that handles user input """

		# disable number pad input if using history for value
		if(self.funcsHistory.usingHistory() or self.ansHistory.usingHistory()):
			return

		# remove error on other input
		if(self.error != None):
			self.error = None

		# if decimal
		if(input == "."):
			# add decimal
			self.values[-1].addDecimal()
		# if exponent
		elif(input == "e"):
			# add exponent
			self.values[-1].addExponent()
		# if negative
		elif(input == "-"):
			# negate
			self.values[-1].negate()
		# for all else assume its a number
		else:
			try:
				self.values[-1].appendNumber(input)
			except NonNumericError:
				# No need to show an error, this should never error and if it did I just
				# want to ignore it
				pass

		# set the text of the display
		self.__setText()

		# scroll the display to the far right unless it was a negate
		if(input == "-"):
			self.display.scrollFarLeft()
		else:
			self.display.scrollFarRight()

	def __setText(self):
		""" Sets the text thats in the display."""

		# All this does is displays one of the following (for line2 anyways):
		#  1) Empty string ("")
		#  2) A number. ie. 1234
		#  3) A operator (with optional number) ie. +, + 23
		#  4) Function (with optional argument(s)) ie `Log` or `Log 2, 4`
		#  5) An error string if there was an error
		#  6) A Postfix operator ie !

		# Line 1 is always the current value of self.result

		# empty line starting off, might add to it
		line = ""

		# if there is an error then I better show it
		if(self.error != None):
			line += self.error

		# if a function was given
		elif(self.function != None):
			# add the function text (makes `Log ` or `+ `)
			line += self.function[self.FUNC_TEXT] + " "
			# if the function is of type function
			if(self.function[self.FUNC_TYPE] == self.TYPE_FUNCTION):
				# for each value
				for v in self.values:
					# add the value to line unless empty Number (makes `Log 2, 3, `)
					if(not v.isEmpty()):
						line += v.getNumber(trimZero = True) + u"\u002C "

				# if there are no more arguments to be given
				if(not self.values[-1].isEmpty()):
					# trim the leading comma and space (makes `Log 2, 3`)
					line = line[:-2]

			# if the function is an operator function `+ a`, `* a`, etc...
			elif(self.function[self.FUNC_TYPE] == self.TYPE_OPERATOR):
				# if a value was given add the value (makes `+ 2`)
				if(len(self.values) != 1 and not self.values[-1].isEmpty()):
					# add the value to the line
					line += self.values[-1].getNumber(trimZero = True)

			# a postfix operator needs no further output

		# if no function (or operator) and just a value
		elif(self.values[-1] != None):
			# add value to line  (makes `2`)
			line += self.values[-1].getNumber(trimZero = True)

		# set the display text
		self.display.setText(str(self.result), line)

	def clear(self):
		""" Resets calculator to start state """
		self.__resetValues()

		# reset the result
		self.result = Decimal()

		# as always refresh the display
		self.__setText()

	def clearEntry(self):
		""" Clears the current entry in its entirety"""
		self.__resetValues()
		# as always refresh the display
		self.__setText()

	def back(self):
		""" Performs a backspace. """

		# if using history then just rest values
		if(self.funcsHistory.usingHistory() or self.ansHistory.usingHistory()):
			self.__resetValues()

		if self.function != None:
				# for operators functions
			if self.function[self.FUNC_TYPE] == self.TYPE_OPERATOR:
				# if the second value is an empty one
				if self.values[1].isEmpty():
					# reset he whole input
					self.__resetValues()
				else:
					# pop a number off the last value
					self.values[-1].popNumber()
			# for function like functions
			elif self.function[self.FUNC_TYPE] == self.TYPE_FUNCTION:
				# if the first value is empty
				if self.values[0].isEmpty():
					# reset the user input
					self.__resetValues()
				# if the current value is empty pop it off
				elif(self.values[-1].isEmpty()):
					self.values.popValue()
				# else pop a number of the value
				else:
					# if the number popped was the last one for that value
					self.values[-1].popNumber()
		# for numbers only
		else:
			# if the first value is empty
			if self.values[0].isEmpty():
				# reset the user input
				self.__resetValues()
			# else pop a number of the value
			else:
				# if the number popped was the last one for that value
				if self.values[-1].popNumber():
					# pop the value off
					self.values.popValue()

		# refresh the display
		self.__setText()
		# and scroll the text so we can see what happened
		self.display.scrollFarRight()

	def funcsHistoryPrev(self):
		""" Gets a previous function from history """

		# check for use of answer history
		if(not self.ansHistory.usingHistory()):
			self.ansHistory.reset()

		# on starting viewing history
		if(not self.funcsHistory.usingHistory()):
			# save the current input
			self.funcsHistory.current = [self.function, self.values]
		# if not at the end of the history list
		if(self.funcsHistory.isNext()):

			# get the current function and value
			self.function, self.values = self.funcsHistory.next()

			# if the function is an operator then set the first value to the current
			# result
			if(self.function[self.FUNC_TYPE] == self.TYPE_OPERATOR):
				self.values[0] = self.result

			# refresh the display
		self.__setText()

	def funcsHistoryNext(self):
		# check for use of answer history
		if(not self.ansHistory.usingHistory()):
			self.ansHistory.reset()
		# if we are not at the beginning of the history
		if(self.funcsHistory.isPrev()):
			# get the function and value from history
			self.function, self.values = self.funcsHistory.prev()
		# as long as there is something in current then
		elif(self.funcsHistory.current != None):
			# set the function and value from current value
			self.function, self.values = self.funcsHistory.current
			self.funcsHistory.reset()
		self.__setText()


	def ansHistoryPrev(self):
		""" Gets a previous answer from history """
		# check for use of function history
		if(not self.funcsHistory.usingHistory()):
			# and reset the history index
			self.funcsHistory.reset()

		# on starting viewing history
		if(not self.ansHistory.usingHistory()):
			# save the current input
			self.ansHistory.current = self.values[-1]

		# if not at the end of the history list
		if(self.ansHistory.isNext()):
			# get the current value
			self.values.updateValue(self.ansHistory.next())

		# refresh the display
		self.__setText()

	def ansHistoryNext(self):
		""" Gets the next answer from history """
		# check for use of function history
		if(not self.funcsHistory.usingHistory()):
			# and reset the history index
			self.funcsHistory.reset()

		# if we are not at the beginning of the history
		if(self.ansHistory.isPrev()):
			# update the current value to a history value
			self.values.updateValue(self.ansHistory.prev())
		# if there was a value saved in current
		elif(self.ansHistory.current != None):
			# set the value to the current (stored value)
			self.values.updateValue(self.ansHistory.current)
			# reset the answer history
			self.ansHistory.reset()
		self.__setText()

