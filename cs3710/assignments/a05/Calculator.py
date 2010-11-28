#########################################################
##  CS 3710 (Winter 2010), Assignment #5               ##
##   Script File Name: Calculator.py                   ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
"""
This file contains the Calculator class which is the heart of the calculator
application.

This class contains within the basic functionality of a calculator. Includes
both history, display interaction, input validation, Clear Entry, Clear,
Input Deletion (backspace).
"""

from Values import Values
from History import History

class Calculator:
	def __init__(self, display, registers = 5):
		# holds the rational numbers
		self.registers = [RationalNumber(0, 0) in range(registers)]

		# set the display
		self.display = display

		# calculator functions (Empty till filled in by the Application class)
		self.functions = {}

		# history storing
		self.history = History()

		# values for functions
		self.values = Values()

		# reset (or in this case initialize) some values
		self.__reset()

		# set the text of the display
		self.__setText()

	def __reset(self):
		""" Resets some values that need to reset often and reset together """

		# current function
		self.function = None

		# current register in operation
		self.register = 0

		# becomes something other then None when there is an error to be shown
		self.error = None

		# the current input as a string
		self.input_value = Input()

		# is handling a register
		self.is_register = False

		# the result of the last calculation
		self.result = None

	def __pushValue(self):
		""" Pushes the current input_value onto the values list and resets the
		input value """
		if !self.input_value.isEmpty():
			self.values.addValue(self.input_value)
			self.input_value = Input()

	def handleFunction(self, function_name):
		""" Handles all function/operator calls. """

		# remove error on other input
		self.error = None

		# perform the last function (virtual equals/return)
		self.__performFunction()

		# if the current supplied function is in the functions array
		if funcName in self.functions:
			# set the value of the function
			self.function = self.functions[funcName]

		else:
			# The final product should never end up here. But just in case.
			self.error = "Function Not Defined"

		# refresh display
		self.__setText()

	def handleRegister(self):


	def equals(self):
		""" Function for the equals button. """
		# just call the __performFunction method
		self.__performFunction()

	def __performFunction(self):
		""" The equals method. Takes the given input from the user and gives an
		answer."""
		# if there is a function waiting
		if(self.function != None):

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
		if self.history.usingHistory():
			return

		# remove error on other input
		self.error = None

		# handle input
		self.input_value.appendCharacter(input)

		# set the text of the display
		self.__setText()

		# scroll the display to the far right unless it was a negate
		if(input == "-"):
			self.display.scrollFarLeft()
		else:
			self.display.scrollFarRight()

	def __setText(self):
		""" Sets the text thats in the display."""
		return
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
		self.__reset()

		# reset the result
		self.result = RationalNumber()

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

