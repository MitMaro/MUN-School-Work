#########################################################
##  CS 3710 (Winter 2010), Assignment #5               ##
##   Script File Name: Values.py                       ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
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
"""

# the important datatype
from RationalNumber import RationalNumber

class Values:

	def __init__(self, values = None):

		# the iteration index
		self._iter = 0

		# if values are given then add them
		if values != None:
			self._values = []
			for v in values:
				self.addValue(v)
		else:
			# else we add one value as a number
			self._values = [RationalNumber()]


	def addValue(self, value = None):
		""" Adds a value to the values list assuring that it is a Number
		"""
		# if given value is None (or not given)
		if value == None:
			self._values.append(RationalNumber())
		# if the given value is already a Number just append it
		elif isinstance(value, RationalNumber):
			self._values.append(value)
		elif isinstance(value, Input):
			self._values.append(value.getRational())
		else:
			print "Value Error"

	def popValue(self):
		""" Removes a value from the values list but does a check first """

		# if the list index is not yet 0
		if len(self._values) > 1:
			# pop a value from the list
			self._values.pop()
		else:
			# else if it is at 0 reset the last value in the list
			self._values[0] = RationalNumber()

	def updateValue(self, value, index = None):
		""" Updates a value in the values list and validates the value if one is
		given """

		# if no index is given then set index to the last value in the list
		if index == None:
			index = len(self._values) - 1

		# if index is out of bounds then set it to last
		if len(self._values) <= index:
			# set index to last
			index = len(self._values) - 1

		# if given value is None (or not given)
		if value == None:
			self._values[index] = RationalNumber()
		# if the given value is already a RationalNumber just append it
		elif isinstance(value, RationalNumber):
			self._values[index] = value
		elif isinstance(value, Input):
			self._values[index] = value.getRational()
		else:
			print "Value Error"

	def getValues(self):
		""" Returns the internal list of values """
		return self._values

	# the length of the list of values
	def __len__(self):
		return len(self._values)

	# next method that is used while iterating
	def next(self):
		# if the iterable index is beyond the list
		if self._iter >= len(self._values):
			# reset the index back to 0
			self._iter = 0
			# raise a StopIteration exception
			raise StopIteration
		else:
			# else increase iterator index
			self._iter = self._iter + 1
			# and return a value
			return self._values[self._iter - 1]

	# basic getitem method
	def __getitem__(self, item):
		return self._values[item]

	# basic setitem method
	def __setitem__(self, key, item):
		self._values[key] = item

	# since this is iterable we can return its self to python for iteration
	def __iter__(self):
		return self


