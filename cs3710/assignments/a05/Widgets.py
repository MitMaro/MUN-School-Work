#########################################################
##  CS 3710 (Winter 2010), Assignment #5               ##
##   Script File Name: Widgets.py                      ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################

import Tkinter as Tk

class WidgetBase(Tk.Frame):
	def __init__(self, master = None):
		Tk.Frame.__init__(self, master)
		self.grid()

	def triggerEvent(self, event):
		""" generates an event that can be captures later and used """
		# create an event for the key that was clicked
		# this is awesome barebones tk
		self.tk.call('event', 'generate', self._w, "<<" + event + ">>")


# NumberPad MegaWidget
class NumberPad(WidgetBase):

	def __init__(self, master = None):
		""" Creates a standard number pad """
		WidgetBase.__init__(self, master)

		# create the number pad
		self.__populate()

		# bind the keys
		self.__bindKeys()


	def __populate(self):
		""" Create the Number Pad """
		# create the number buttons 0-9 and negation and decimal button
		self.buttons = [
			Tk.Button(self, width=2, height=2, text="7", command=lambda: self.triggerEvent("NumberPadClick-7")),
			Tk.Button(self, width=2, height=2, text="8", command=lambda: self.triggerEvent("NumberPadClick-8")),
			Tk.Button(self, width=2, height=2, text="9", command=lambda: self.triggerEvent("NumberPadClick-9")),
			Tk.Button(self, width=2, height=2, text="4", command=lambda: self.triggerEvent("NumberPadClick-4")),
			Tk.Button(self, width=2, height=2, text="5", command=lambda: self.triggerEvent("NumberPadClick-5")),
			Tk.Button(self, width=2, height=2, text="6", command=lambda: self.triggerEvent("NumberPadClick-6")),
			Tk.Button(self, width=2, height=2, text="1", command=lambda: self.triggerEvent("NumberPadClick-1")),
			Tk.Button(self, width=2, height=2, text="2", command=lambda: self.triggerEvent("NumberPadClick-2")),
			Tk.Button(self, width=2, height=2, text="3", command=lambda: self.triggerEvent("NumberPadClick-3")),
			Tk.Button(self, width=2, height=2, text="\\", command=lambda: self.triggerEvent("NumberPadClick-\\")),
			Tk.Button(self, width=2, height=2, text=u"\u00B1", command=lambda: self.triggerEvent("NumberPadClick-minus")),
			Tk.Button(self, width=2, height=2, text="0", command=lambda: self.triggerEvent("NumberPadClick-0"))
		]

		# place the buttons
		for i in xrange(3):
			for j in xrange(3):
				self.buttons[((i * 3) + j)].grid(column=j , row=i, sticky=Tk.N+Tk.E+Tk.S+Tk.W)
		self.buttons[9].grid(column=0, row=3, sticky=Tk.N+Tk.E+Tk.S+Tk.W)
		self.buttons[10].grid(column=1, row=3, sticky=Tk.N+Tk.E+Tk.S+Tk.W)
		self.buttons[11].grid(column=2, row=3, sticky=Tk.N+Tk.E+Tk.S+Tk.W)

	def __bindKeys(self):
		""" Bind the keys """
		# this could be done in a loop but take a bit more work
		self.bind_all("<KeyPress-9>", lambda e: self.triggerEvent("NumberPadClick-9"), add="+")
		self.bind_all("<KeyPress-8>", lambda e: self.triggerEvent("NumberPadClick-8"), add="+")
		self.bind_all("<KeyPress-7>", lambda e: self.triggerEvent("NumberPadClick-7"), add="+")
		self.bind_all("<KeyPress-6>", lambda e: self.triggerEvent("NumberPadClick-6"), add="+")
		self.bind_all("<KeyPress-5>", lambda e: self.triggerEvent("NumberPadClick-5"), add="+")
		self.bind_all("<KeyPress-4>", lambda e: self.triggerEvent("NumberPadClick-4"), add="+")
		self.bind_all("<KeyPress-3>", lambda e: self.triggerEvent("NumberPadClick-3"), add="+")
		self.bind_all("<KeyPress-2>", lambda e: self.triggerEvent("NumberPadClick-2"), add="+")
		self.bind_all("<KeyPress-1>", lambda e: self.triggerEvent("NumberPadClick-1"), add="+")
		self.bind_all("<KeyPress-0>", lambda e: self.triggerEvent("NumberPadClick-0"), add="+")
		self.bind_all("<Control-KeyPress-minus>", lambda e: self.triggerEvent("NumberPadClick-minus"), add="+")
		self.bind_all("<KeyPress-|>", lambda e: self.triggerEvent("NumberPadClick-|"), add="+")

# Display MegaWidget, taken from my cs2500 calculator
class Display(WidgetBase):
	def __init__(self, master = None):
		WidgetBase.__init__(self, master)

		# reduce the width by 2 to make room for the scroll indicators
		self.width = 25

		# contains the actual text of the display (not the text thats displayed)
		self.text = ""

		# the scroll index (the index in the string of the first character)
		self.scrollIndex = 0

		# control variable for the label
		self.ctext = Tk.StringVar()

		# create the display
		self.__populate(self.width + 2)

		# bind keys if it was asker for
		self.__bindKeys()

		# set the text
		self.__setText()

	def __populate(self, width):
		""" Creates the display """
		# create the display
		self.display = Tk.Label(self,
			width=width,
			text="",
			anchor=Tk.E,
			relief=Tk.GROOVE,
			textvariable=self.ctext,
			justify=Tk.RIGHT)
		# set the display
		self.display.grid()

	def setText(self, text):
		"""
		Set the text in the Display, line1 set the first line
		"""

		# reset the scroll index to 0 since I don't determine if the index would be
		# valid for the new lines of text.
		self.scrollIndex = 0

		# set the actual lines of text
		self.text = text

		# set the displayed text
		self.__setText()

	def __setText(self):
		""" Sets the text to be shown in the display. Takes into account scroll"""

		# check if the text is longer then the width
		if len(self.text) > self.width:
			# if scroll is not at the far right (which is as right as possible to
			# still show a full line of text)
			if self.scrollIndex + self.width < len(self.text):
				# if index is at the start of the line
				if self.scrollIndex == 0:
					# grab a string of width and add an >> arrow
					text = self.text[self.scrollIndex: self.scrollIndex + self.width] + u"\u00BB"
				# index is somewhere in the middle of the actual line
				else:
					# grab a section of string of width from the middle of the line
					# starting at scrollIndex. Add << and >> arrows.
					text = u"\u00AB" + self.text[self.scrollIndex: self.scrollIndex + self.width] + u"\u00BB"
			# since we are at the far right grab enough characters from the end to
			# fill the display
			else:
				# line padded with a << arrow and a no break space (has different width
				# then an ASCII space)
				text = u"\u00AB" + self.text[-self.width:] + u"\u00A0"
		# text is not longer then width
		else:
			# the line is the whole text. I also add a space to keep padding ok
			text = self.text + u"\u00A0"

	def scrollRight(self):
		""" Scrolls the text of the display by one character to the right """
		# only scroll if the index is not already far to the right
		if self.scrollIndex < len(self.text) - self.width:
			# add to the scroll index
			self.scrollIndex += 1
			# calculate the new displayed text
			self.__setText()

	def scrollLeft(self):
		""" Scrolls the text of the display by one character to the left """
		# only scroll if the index is not already to the far left
		if self.scrollIndex > 0:
			# subtract from the scroll index
			self.scrollIndex -= 1
			# calculate the new displayed text
			self.__setText()

	def scrollFarRight(self):
		""" Scrolls the text to the far right (making sure to keep a full line) """
		# calculate scroll index
		self.scrollIndex = len(self.text) - self.width
		# calculate the new displayed text
		self.__setText()

	def scrollFarLeft(self):
		""" Scrolls the text to the far left """
		self.scrollIndex = 0
		# calculate the new displayed text
		self.__setText()

	def __bindKeys(self):
		""" bind some keys """
		# bind the left and right arrow keys to the right and left scroll methods
		self.bind_all("<KeyPress-Left>", lambda e: self.scrollLeft())
		self.bind_all("<KeyPress-Right>", lambda e: self.scrollRight())

# Standard Operations MegaWidget
class StandardOperations(WidgetBase):
	def __init__(self, master = None):
		WidgetBase.__init__(self, master)

		# create the buttons
		self.__populate()

		# bind some keys if wanted
		self.__bindKeys()

	def __populate(self):
		""" Create some buttons and show them """
		# create the buttons and set their command to trigger an event
		self.buttonAdd = Tk.Button(self, width=2, height=2, text="+", command=lambda:self.triggerEvent("StandardOperation-+"))
		self.buttonSubtract = Tk.Button(self, width=2, height=2, text="-", command=lambda:self.triggerEvent("StandardOperation-minus"))
		self.buttonMultiply = Tk.Button(self, width=2, height=2, text="*", command=lambda:self.triggerEvent("StandardOperation-*"))
		self.buttonDivide = Tk.Button(self, width=2, height=2, text=u"\u00F7", command=lambda:self.triggerEvent("StandardOperation-/"))
		self.buttonOff = Tk.Button(self, width=2, height=2, text="Off", command=lambda:self.triggerEvent("StandardOperation-off"))
		self.buttonClear = Tk.Button(self, width=2, height=2, text="Clear", command=lambda:self.triggerEvent("StandardOperation-clear"))
		self.buttonShow = Tk.Button(self, width=2, height=2, text="Show ", command=lambda:self.triggerEvent("StandardOperation-show"))
		self.buttonEqual = Tk.Button(self, width=2, height=2, text="=", command=lambda:self.triggerEvent("StandardOperation-="))

		# set the location and stuff for the buttons
		self.buttonOff.grid(column=1, row=0, sticky=Tk.N+Tk.E+Tk.S+Tk.W)
		self.buttonClear.grid(column=1, row=1, sticky=Tk.N+Tk.E+Tk.S+Tk.W)
		self.buttonShow.grid(column=1, row=2, sticky=Tk.N+Tk.E+Tk.S+Tk.W)
		self.buttonEqual.grid(column=1, row=3, sticky=Tk.N+Tk.E+Tk.S+Tk.W)
		self.buttonDivide.grid(column=0, row=0, sticky=Tk.N+Tk.E+Tk.S+Tk.W)
		self.buttonMultiply.grid(column=0, row=1, sticky=Tk.N+Tk.E+Tk.S+Tk.W)
		self.buttonSubtract.grid(column=0, row=2, sticky=Tk.N+Tk.E+Tk.S+Tk.W)
		self.buttonAdd.grid(column=0, row=3, sticky=Tk.N+Tk.E+Tk.S+Tk.W)

	def __bindKeys(self):
		""" Bind some keys to some buttons """
		# bind some keys for the buttons
		self.bind_all("<KeyPress-plus>", lambda e: self.triggerEvent("StandardOperation-+"), add="+")
		self.bind_all("<KeyPress-minus>", lambda e: self.triggerEvent("StandardOperation-minus"), add="+")
		self.bind_all("<KeyPress-asterisk>", lambda e: self.triggerEvent("StandardOperation-*"), add="+")
		self.bind_all("<KeyPress-slash>", lambda e: self.triggerEvent("StandardOperation-/"), add="+")
		self.bind_all("<KeyPress-equal>", lambda e: self.triggerEvent("StandardOperation-="), add="+")
		self.bind_all("<KeyPress-Return>", lambda e: self.triggerEvent("StandardOperation-="), add="+")

