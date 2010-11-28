#########################################################
##  CS 2500 (Fall 2008), Assignment #8, Question #1    ##
##   Script File Name: Number.py                       ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
#    File: Number.py
# Project: CS 2500 (Assignment 8, Question 1)
#      By: Tim Oram [t.oram@mitmaro.ca]
# Created: November 26, 2008; Updated December 01, 2008
# Purpose: Creation and manipulation of a properly formatted numerical input
"""
This file holds a class that allows you to build a number one character at a
time. At any time you can get a string representation of this number that is
parseable by Float() and Decimal().

If I had my time back I would have called this class NumericalInput because it 
is a better description of what this class is but I don't want to risk breaking
something this late in the game by refactoring.
"""

# allows the creation of a valid string representation of a number.
class Number:
	def __init__(self, number = None, allowExponent = True):
		# some flags
		self.negative = False
		self.decimal = False

		# an exponent when set is another Number (without an exponent)
		self.exponent = None
		
		# the number parts (before and after decimal)
		self.number = ""
		self.real= ""
		
		# allow exponents flag 
		# used to stop exponents from containing an exponent part
		self.allowExponent = allowExponent
		
		# if a string was given parse it
		if(number != None):
			
			# handle floats being passed
			if(isinstance(number, float)):
				number = str(number)
			
			# for each character handle it 
			for c in number:
				if(c == "-"):
					# negate on a dash
					self.negate()
				elif(c == "+"):
					# ignore a positive
					pass
				elif(c == "e" or c == "E"):
					# add exponent 
					self.addExponent()
				elif(c == "."):
					# add a decimal
					self.addDecimal()
				else:
					# else append the character
					self.appendNumber(c)
	
	def appendNumber(self, num):
		""" append a number to end of the string number """
		
		# if an exponent is used add number to exponent
		if(self.exponent != None):
			self.exponent.appendNumber(num)
			return
		
		# check if the character is a number
		if(48 <= ord(num) <= 57):
			# add it to real part if need be
			if(self.decimal):
				self.real += num
			# else add to the number 
			else:
				self.number += num
		# if the character wasn't a number throw an exception. I don't normally
		# handle this exception it is here more for debugging and development
		else:
			raise NonNumericError()
		
	def popNumber(self):
		"""
		Pops the number, removing exponents, then numbers after decimal, the decimal
		and then the numbers before the decimal. This function returns true when the
		string representation of the number is "".
		""" 
		# first remove the exponent part
		if(self.exponent != None):
			# if the exponent number is empty then the exponent can be set to none
			if(self.exponent.popNumber()):
				self.exponent = None
				
		# if there is a decimal part
		elif(self.decimal):
			# if there are no numbers after the decimal
			if(self.real == ""):
				# remove the decimal
				self.decimal = False
			else:
				# else remove one character from end of the real part
				self.real = self.real[:-1]
		# only the number left
		else:
			# remove a character
			self.number = self.number[:-1]
			# if there is no characters left in the number
			if(self.number == ""):
				# return true
				return True
			
		# else return false
		return False
	
	
	def addDecimal(self):
		""" Add a decimal point """
		# set the decimal flag to true
		self.decimal = True
		
	def negate(self):
		""" Negate the number """
		
		# if there is an exponent negate the exponent not this number
		if(self.exponent != None):
			self.exponent.negate()
		# not the negate flag (a negative of a negative is positive)
		else:
			self.negative = not self.negative
		
	def addExponent(self):
		""" Adds an exponent part to the number """
		# if the exponent part is not set and this number is allowed an exponent
		if(self.exponent == None and self.allowExponent):
			# set the exponent to another number (disallowing exponents since we can't
			# have an exponent with an exponent
			self.exponent = Number(allowExponent = False)
			
	def getNumber(self, trimZero = False):
		""" Returns a string that is guaranteed to be parseable by Decimal. Unless
		the trimZero flag is set to true. trimZero flag trims the leading zero after
		the decimal (if the zero was not set explicitly). ie 1.0 will be 1. with the
		flag set to true"""
		
		# if there is nothing in the number part add a 0 but take care of the
		# trimZero flag
		if(self.number == ""):
			# if trimZero empty string else a 0
			if(trimZero):
				rtn = ""
			else:
				rtn = "0"
		# else add the number string
		else:
			rtn = self.number
		
		# if there is a decimal part
		if(self.decimal):
			# add the decimal point
			rtn += "."
			# if the real part of the number is not empty
			if(self.real != ""):
				# add the real part
				rtn += self.real
			# else if the real part if empty and they trimZero flag is false
			elif(not trimZero):
				# add a leading 0
				rtn += "0"
		# if there is an exponent part
		if(self.exponent != None):
			# add the e character and call this method on the exponent number
			rtn += "e" + self.exponent.getNumber()
		
		# append a negative on the front of the string if the number is negative
		if(self.negative):
			rtn = "-" + rtn
		
		# return the string
		return rtn
	
	def isEmpty(self):
		""" True when the number is empty"""
		
		# if number and real are empty numbers and the decimal flag is false and
		# there is no exponent
		if(self.number == "" and self.real == "" and not self.decimal and self.exponent == None):
			# return true
			return True
		# else return false
		return False
	
	# i only used this in testing but I don't see a reason to remove it
	def __str__(self):
		return "Number(" + self.getNumber() + ")"

# custom exception for a Non number being passed. doesn't do anything special.
class NonNumericError(Exception): pass

