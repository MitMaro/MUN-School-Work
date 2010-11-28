#########################################################
##  CS 3710 (Winter 2010), Assignment #5               ##
##   Script File Name: Input.py                        ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
"""
This file holds a class that allows you to build a fraction one character at a
time.
"""

# the important datatype
from RationalNumber import RationalNumber

class Input:
	def __init__(self):
		self.numerator = ""
		self.denominator = ""
		self.negative = False
		self._is_numerator = True

	def appendCharacter(self, character):
		# check for some invalid input, leading 0 and non character
		if(self.denominator == "" && self.character == 0) || len(self.character) != 1:
			return

		# allow only the numbers 0 - 9
		if 48 <= ord(character) <= 57:
			self.numerator += character
		elif character == "/":
			self._is_numerator = False
		elif character == "-":
			self.negative = True

	def getRational(self):
		if self.numerator == "":
			n = 0
		else:
			n = int(self.numerator)
		if self.denominator == "":
			d = 1
		else:
			d = int(self.numerator)

		if self.negative:
			return [-n, d]
		else
			return [n,d]

	def isEmpty(self):
		return self.denominator == "" and self.numerator == ""
