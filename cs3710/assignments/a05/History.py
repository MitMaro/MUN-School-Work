#########################################################
##  CS 3710 (Winter 2010), Assignment #5               ##
##   Script File Name: History.py                      ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
"""
Contains a list of histories. Allows for iteration over the list (both forward
and back). The standard Python iterator only allowed forward transversal only
and I wanted both. The history class also stores the current value from the
calculator and a method that returns true when the history is being used.
"""

class History:

	def __init__(self):
		# the list the holds the histories
		self._list = []

		# the iterable index
		self._iter = -1

		# the current value of the calculator
		self.current = None

	# insert a history at the beginning of the list
	def insert(self, value):
		self._list.insert(0, value)
		self._iter = -1

	# returns true if there is a next value
	def isNext(self):
		return len(self._list) > self._iter + 1

	# returns the next value in the list
	def next(self):
		# if not at the end of the list (guess I could call isNext() here)
		if len(self._list) > self._iter + 1:
			# increase the iterator
			self._iter += 1
			# and return the value
			return self._list[self._iter]

		# else return none
		return None

	# true when there is a previous value
	def isPrev(self):
		return self._iter - 1 >= 0

	def prev(self):
		# check for next value in list (again could use isPrev() here)
		if self._iter > 0:
			# decrease the iterator
			self._iter -= 1
			# return the value
			return self._list[self._iter]
		# else we return none
		return None

	# returns the current value
	def currentValue(self):
		# if the iterable index is not -1
		if self._iter >= 0:
			# return the current value
			return self._list[self._iter]
		# else return None
		return None

	# reset the iterable index and set current no none
	def reset(self):
		self._iter = -1
		self.current = None

	# returns true when the history is in use
	def usingHistory(self):
		return self._iter != -1

