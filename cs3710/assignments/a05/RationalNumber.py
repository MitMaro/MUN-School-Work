#########################################################
##  CS 3710 (Winter 2010), Assignment #5               ##
##   Script File Name: RationalNumber.py               ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
"""
A representation of a Rational Number (ie. fraction) and some common methods
that can be performed on it.
"""

class RationalNumber:
	def __init__(self, numerator = 0, denominator = 1):

		# make negative denominator, a negative numerator
		if denominator < 0:
			self.numerator = -numerator
			self.denominator = -denominator
		else:
			self.numerator = numerator
			self.denominator = denominator

	def gcd(self):
		a = self.numerator
		b = self.denominator
		while a:
			a, b = b%a, a
		return b

	def add(self, numerator = 0, denominator = 0):
		if isinstance(numerator, RationalNumber):
			self.numerator += numerator.numerator
			self.denominator += numerator.denominator
		else:
			self.numerator += numerator
			self.denominator += denominator

	def subtract(self, numerator = 0, denominator = 0):
		if isinstance(numerator, RationalNumber):
			self.numerator -= numerator.numerator
			self.denominator -= numerator.denominator
		else:
			self.numerator -= numerator
			self.denominator -= denominator

	def multiply(self, numerator = 1, denominator = 1):
		if isinstance(numerator, RationalNumber):
			self.numerator *= numerator.numerator
			self.denominator *= numerator.denominator
		else:
			self.numerator *= numerator
			self.denominator *= denominator

	def division(self, numerator = 1, denominator = 1):
		if isinstance(numerator, RationalNumber):
			self.numerator *= numerator.denominator
			self.denominator *= numerator.numerator
		else:
			# cross multiply
			self.numerator *= denominator
			self.denominator *= numerator

	def simplfy(self):
		gcd = self.gcd()
		self.numerator /= gcd
		self.denominator /= gcd

	def toDecimal(self):
		return self.numerator / self.denominator

	def __str__(self):
		return str(self.numerator) + "/" + str(self.denominator)
