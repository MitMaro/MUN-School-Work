#########################################################
##  CS 2500 (Fall 2008), Assignment #8, Question #1    ##
##   Script File Name: CalcWidgets.py                  ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
#    File: CalcWidgets.py
# Project: CS 2500 (Assignment 8, Question 1)
#      By: Tim Oram [t.oram@mitmaro.ca]
# Created: November 20, 2008; Updated December 03, 2008
# Purpose: Contains the various MegaWidgets used in the calculator

"""
This file contains Tkinter megawidgets for the calculator.

Contains the NumberPad, Display, StandardOperations, ControlOperations and
ExtendedOperation MegaWidgets.

Each megawidget generates a virtual event when one of its buttons are clicked.
The display widget has no virtual events associated with it because currently
its only use is to display data. I choose to use virtual events over callback
functions because the number of callback functions that would need to be given
to the various megawidgets would be high.

The MegaWidgets are written so that they can easily be used in any TKinter based
application.

"""

# Can't use Tkinter without Tkinter
from Tkinter import *
# Cause I use fonts import tkFont
import tkFont


# NumberPad MegaWidget
class NumberPad(Frame):
	
	def __init__(self, master = None, bindKeys = True, font = None):
		""" Creates a standard number pad """
		Frame.__init__(self, master)
		self.grid()
		
		# set the font to use in the widget
		if(font !=  None):
			self.font = font
		else:
			self.font = tkFont.Font(family="Courier", size="14")
		
		# create the number pad
		self.__populate()
		
		# if we want to bind keys
		if(bindKeys):
			# bind the keys
			self.__bindKeys()

	
	def __populate(self):
		""" Create the Number Pad """	
		# create the number buttons 0-9 and negation and decimal button
		self.buttons = [
			Button(self, font=self.font, width=2, height=2, text=u"\u03C0", command=lambda: self.triggerEvent("Pi")),
			Button(self, font=self.font, width=2, height=2, text="EE", command=lambda: self.triggerEvent("Exponent")),
			Button(self, font=self.font, width=2, height=2, text=",", command=lambda: self.triggerEvent("FuncSep")),
			Button(self, font=self.font, width=2, height=2, text="7", command=lambda: self.triggerEvent("7")),
			Button(self, font=self.font, width=2, height=2, text="8", command=lambda: self.triggerEvent("8")),
			Button(self, font=self.font, width=2, height=2, text="9", command=lambda: self.triggerEvent("9")),
			Button(self, font=self.font, width=2, height=2, text="4", command=lambda: self.triggerEvent("4")),
			Button(self, font=self.font, width=2, height=2, text="5", command=lambda: self.triggerEvent("5")),
			Button(self, font=self.font, width=2, height=2, text="6", command=lambda: self.triggerEvent("6")),
			Button(self, font=self.font, width=2, height=2, text="1", command=lambda: self.triggerEvent("1")),
			Button(self, font=self.font, width=2, height=2, text="2", command=lambda: self.triggerEvent("2")),
			Button(self, font=self.font, width=2, height=2, text="3", command=lambda: self.triggerEvent("3")),
			Button(self, font=self.font, width=2, height=2, text="0", command=lambda: self.triggerEvent("0")),
			Button(self, font=self.font, width=2, height=2, text=u"\u00B1", command=lambda: self.triggerEvent("minus")),
			Button(self, font=self.font, width=2, height=2, text=".", command=lambda: self.triggerEvent(".")),
		]

		# place the buttons
		for i in xrange(5):
			for j in xrange(3):
				self.buttons[((i * 3) + j)].grid(column=j , row=i, sticky=N+E+S+W)
		
	def __bindKeys(self):
		""" Bind the keys """
		# this could be done in a loop but take a bit more work
		self.bind_all("<KeyPress-9>", lambda e: self.triggerEvent("9"), add="+")
		self.bind_all("<KeyPress-8>", lambda e: self.triggerEvent("8"), add="+")
		self.bind_all("<KeyPress-7>", lambda e: self.triggerEvent("7"), add="+")
		self.bind_all("<KeyPress-6>", lambda e: self.triggerEvent("6"), add="+")
		self.bind_all("<KeyPress-5>", lambda e: self.triggerEvent("5"), add="+")
		self.bind_all("<KeyPress-4>", lambda e: self.triggerEvent("4"), add="+")
		self.bind_all("<KeyPress-3>", lambda e: self.triggerEvent("3"), add="+")
		self.bind_all("<KeyPress-2>", lambda e: self.triggerEvent("2"), add="+")
		self.bind_all("<KeyPress-1>", lambda e: self.triggerEvent("1"), add="+")
		self.bind_all("<KeyPress-0>", lambda e: self.triggerEvent("0"), add="+")
		self.bind_all("<Control-KeyPress-minus>", lambda e: self.triggerEvent("minus"), add="+")
		self.bind_all("<KeyPress-period>", lambda e: self.triggerEvent("."), add="+")
		self.bind_all("<KeyPress-comma>", lambda e: self.triggerEvent("FuncSep"), add="+")
		self.bind_all("<KeyPress-e>", lambda e: self.triggerEvent("Exponent"), add="+")
		self.bind_all("<KeyPress-p>", lambda e: self.triggerEvent("Pi"), add="+")
	
	def triggerEvent(self, key):
		""" generates an event that can be captures later and used """
		# create an event for the key that was clicked
		self.tk.call('event', 'generate', self._w, "<<NumberPadClick-" + key + ">>")

# Display MegaWidget
class Display(Frame):
	def __init__(self, master = None, width = 32, bindKeys = True, font = None):
		Frame.__init__(self, master)
		self.grid()
		
		# set the font to use in the widget
		if(font !=  None):
			self.font = font
		else:
			self.font = tkFont.Font(family="Courier", size="14")
		
		# reduce the width by 2 to make room for the scroll indicators
		self.width = width - 2
		
		# contains the actual text of the display (not the text thats displayed)
		self.line1 = ""
		self.line2 = ""
		
		# the scroll index (the index in the string of the first character)
		self.scrollIndex = 0
		
		# control variable for the label
		self.ctext = StringVar()
		
		# create the display
		self.__populate(width)

		# bind keys if it was asker for
		if(bindKeys):
			self.__bindKeys()

		# set the text
		self.__setText()

	def __populate(self, width):
		""" Creates the display """
		# create the display
		self.display = Label(self,
												width=width,
												height=2,
												text="",
												anchor=E,
												relief=GROOVE,
												textvariable=self.ctext,
												justify=RIGHT,
												font = self.font)
		# set the display
		self.display.grid()
	
	def setText(self, line1, line2):
		"""
		Set the text in the Display, line1 set the first line and line2 the second
		"""
		
		# reset the scroll index to 0 since I don't determine if the index would be
		# valid for the new lines of text.
		self.scrollIndex = 0
		
		# set the actual lines of text
		self.line1 = line1
		self.line2 = line2
		
		# set the displayed text
		self.__setText()
	
	def __setText(self):
		""" Sets the text to be shown in the display. Takes into account scroll"""
		
		# the scroll for each line is calculated independent of the other, one line
		# will scroll further then other if necessary 
		
		# line 1
		# check if the text is longer then the width
		if(len(self.line1) > self.width):
			# if scroll is not at the far right (which is as right as possible to
			# still show a full line of text)
			if(self.scrollIndex + self.width < len(self.line1)):
				# if index is at the start of the line
				if(self.scrollIndex == 0):
					# grab a string of width and add an >> arrow
					line1 = self.line1[self.scrollIndex: self.scrollIndex + self.width] + u"\u00BB"
				# index is somewhere in the middle of the actual line
				else:
					# grab a section of string of width from the middle of the line 
					# starting at scrollIndex. Add << and >> arrows.
					line1 = u"\u00AB" + self.line1[self.scrollIndex: self.scrollIndex + self.width] + u"\u00BB"
			# since we are at the far right grab enough characters from the end to
			# fill the display
			else:
				# line padded with a << arrow and a no break space (has different width
				# then an ASCII space) 
				line1 = u"\u00AB" + self.line1[-self.width:] + u"\u00A0"
		# text is not longer then width
		else:
			# the line is the whole text. I also add a space to keep padding ok
			line1 = self.line1 + u"\u00A0"
		
		# sane deal as code above but for line 2
		if(len(self.line2) > self.width):
			if(self.scrollIndex + self.width < len(self.line2)):
				if(self.scrollIndex == 0):
					line2 = self.line2[self.scrollIndex: self.scrollIndex + self.width] + u"\u00BB"
				else:
					line2 = u"\u00AB" + self.line2[self.scrollIndex: self.scrollIndex + self.width] + u"\u00BB"
			else:
				line2 = u"\u00AB" + self.line2[-self.width:] + u"\u00A0"
		else:
			line2 = self.line2 + u"\u00A0"
		
		# set the value of the control variable
		self.ctext.set(line1 + "\n" + line2)

	def scrollRight(self):
		""" Scrolls the text of the display by one character to the right """
		# only scroll if the index is not already far to the right
		if(self.scrollIndex < max(len(self.line1), len(self.line2)) - self.width):
			# add to the scroll index
			self.scrollIndex += 1
			# calculate the new displayed text
			self.__setText()
				
	def scrollLeft(self):
		""" Scrolls the text of the display by one character to the left """
		# only scroll if the index is not already to the far left
		if(self.scrollIndex > 0):
			# subtract from the scroll index
			self.scrollIndex -= 1
			# calculate the new displayed text
			self.__setText()
	
	def scrollFarRight(self):
		""" Scrolls the text to the far right (making sure to keep a full line) """
		# calculate scroll index
		self.scrollIndex = max(len(self.line1), len(self.line2)) - self.width
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
class StandardOperations(Frame):
	def __init__(self, master = None, bindKeys = True, font = None):
		Frame.__init__(self, master)
		self.grid()
					
		# the font to use
		if(font !=  None):
			self.font = font
		else:
			self.font = tkFont.Font(family="Courier", size="14")
		
		# create the buttons
		self.__populate()
		
		# bind some keys if wanted
		if(bindKeys):
			self.__bindKeys()
		
	def __populate(self):
		""" Create some buttons and show them """
		# create the buttons and set their command to trigger an event
		self.buttonAdd = Button(self, font=self.font, width=2, height=2, text="+", command=lambda:self.triggerEvent("+"))
		self.buttonSubtract = Button(self, font=self.font, width=2, height=2, text="-", command=lambda:self.triggerEvent("minus"))
		self.buttonMultiply = Button(self, font=self.font, width=2, height=2, text="*", command=lambda:self.triggerEvent("*"))
		self.buttonDivide = Button(self, font=self.font, width=2, height=2, text=u"\u00F7", command=lambda:self.triggerEvent("/"))
		self.buttonEqual = Button(self, font=self.font, width=2, height=2, text="=", command=lambda:self.triggerEvent("="))
		
		# set the location and stuff for the buttons
		self.buttonDivide.grid(column=0, row=0, sticky=N+E+S+W)
		self.buttonMultiply.grid(column=0, row=1, sticky=N+E+S+W)
		self.buttonSubtract.grid(column=0, row=2, sticky=N+E+S+W)
		self.buttonAdd.grid(column=0, row=3, sticky=N+E+S+W)
		self.buttonEqual.grid(column=0, row=4, sticky=N+E+S+W)
	
	def __bindKeys(self):
		""" Bind some keys to some buttons """
		# bind some keys for the buttons
		self.bind_all("<KeyPress-plus>", lambda e: self.triggerEvent("+"), add="+")
		self.bind_all("<KeyPress-minus>", lambda e: self.triggerEvent("minus"), add="+")
		self.bind_all("<KeyPress-asterisk>", lambda e: self.triggerEvent("*"), add="+")
		self.bind_all("<KeyPress-slash>", lambda e: self.triggerEvent("/"), add="+")
		self.bind_all("<KeyPress-equal>", lambda e: self.triggerEvent("="), add="+")
		self.bind_all("<KeyPress-Return>", lambda e: self.triggerEvent("="), add="+")

	def triggerEvent(self, op):
		""" Triggers a virtual event for a operation """
		# trigger virtual event
		self.tk.call('event', 'generate', self._w, '<<StandardOperation-' + op +'>>')

# Control Operations MegaWidget
class ControlOperations(Frame):
	def __init__(self, master = None, bindKeys = True, font = None):
		Frame.__init__(self, master)
		self.grid()
		
		# the font to use
		if(font !=  None):
			self.font = font
		else:
			self.font = tkFont.Font(family="Courier", size="14")
		
		# create the buttons
		self.__populate()
		
		# bind some keys if wanted
		if(bindKeys):
			self.__bindKeys()
		
	def __populate(self):
		""" Create some buttons and show them """

		# labels to show above the history buttons
		self.labelHistAns = Label(self, font=self.font, text="Ans Hist")
		self.labelHistOps = Label(self, font=self.font, text="Ops Hist")

		# the clear buttons
		self.buttonClear = Button(self, font=self.font, width=2, height=2, text="C", command=lambda:self.triggerEvent("Clear"))
		self.buttonClearEntry = Button(self, font=self.font, width=2, height=2, text="CE", command=lambda:self.triggerEvent("ClearEntry"))
		self.buttonBack = Button(self, font=self.font, width=2, height=2, text="Del", command=lambda:self.triggerEvent("Back"))
		
		# the exit button
		self.buttonExit = Button(self, font=self.font, width=2, height=2, text="Off", command=lambda:self.triggerEvent("Quit"))
		
		# the history navigation buttons
		self.prevOp = Button(self, font=self.font, width=2, height=2, text=u"\u00AB", command=lambda:self.triggerEvent("OpPrev"))
		self.nextOp = Button(self, font=self.font, width=2, height=2, text=u"\u00BB", command=lambda:self.triggerEvent("OpNext"))
		self.prevFunc = Button(self, font=self.font, width=2, height=2, text= u"\u00AB", command=lambda:self.triggerEvent("FuncPrev"))
		self.nextFunc = Button(self, font=self.font, width=2, height=2, text= u"\u00BB", command=lambda:self.triggerEvent("FuncNext"))
		

		# show the buttons and labels
		self.labelHistOps.grid(column=0, columnspan=2, row=0, sticky=W+E)
		self.labelHistAns.grid(column=2, columnspan=2, row=0, sticky=W+E)
		self.prevFunc.grid(column=0, row=1, sticky=N+E+S+W)
		self.nextFunc.grid(column=1, row=1, sticky=N+E+S+W)
		self.prevOp.grid(column=2, row=1, sticky=N+E+S+W)
		self.nextOp.grid(column=3, row=1, sticky=N+E+S+W)
		self.buttonClear.grid(column=4, row=1, sticky=N+E+S+W)
		self.buttonClearEntry.grid(column=5, row=1, sticky=N+E+S+W)
		self.buttonBack.grid(column=6, row=1, sticky=N+E+S+W)
		self.buttonExit.grid(column=7, row=1, sticky=N+E+S+W)
	
	def __bindKeys(self):
		""" Bind some keys to some buttons """
		# bind some keys for the buttons
		self.bind_all("<Shift-KeyPress-Escape>", lambda e: self.triggerEvent("Clear"), add="+")
		self.bind_all("<KeyPress-Delete>", lambda e: self.triggerEvent("Back"), add="+")
		self.bind_all("<KeyPress-Escape>", lambda e: self.triggerEvent("ClearEntry"), add="+")
		
		# bind the history operations
		self.bind_all("<KeyPress-Up>", lambda e: self.triggerEvent("FuncNext"))
		self.bind_all("<KeyPress-Down>", lambda e: self.triggerEvent("FuncPrev"))
		self.bind_all("<Shift-KeyPress-Up>", lambda e: self.triggerEvent("OpNext"))
		self.bind_all("<Shift-KeyPress-Down>", lambda e: self.triggerEvent("OpPrev"))
		
		# no key for exit
		
	def triggerEvent(self, op):
		""" Triggers a virtual event for a operation """
		# trigger virtual event
		self.tk.call('event', 'generate', self._w, '<<ControlOperation-' + op +'>>')

# Extended Control Operations
class ExtendedOperations(Frame):
	def __init__(self, master = None, bindKeys = True, font = None):
		Frame.__init__(self, master)
		self.grid()
							
		# the font 
		if(font !=  None):
			self.font = font
		else:
			self.font = tkFont.Font(family="Courier", size="14")
		
		# create the buttons
		self.__populate()
		
		# bind some keys if wanted
		if(bindKeys):
			self.__bindKeys()
		
	def __populate(self):
		""" Create some buttons and show them """
		
		# trig function buttons
		self.sin = Button(self, font=self.font, width=2, height=2, text="Sin", command=lambda:self.triggerEvent("sin"))
		self.cos = Button(self, font=self.font, width=2, height=2, text="Cos", command=lambda:self.triggerEvent("cos"))
		self.tan = Button(self, font=self.font, width=2, height=2, text="Tan", command=lambda:self.triggerEvent("tan"))
		self.asin = Button(self, font=self.font, width=2, height=2, text="aSin", command=lambda:self.triggerEvent("asin"))
		self.acos = Button(self, font=self.font, width=2, height=2, text="aCos", command=lambda:self.triggerEvent("acos"))
		self.atan = Button(self, font=self.font, width=2, height=2, text="aTan", command=lambda:self.triggerEvent("atan"))
		self.deg = Button(self, font=self.font, width=2, height=2, text="Deg", command=lambda:self.triggerEvent("deg"))
		self.rad = Button(self, font=self.font, width=2, height=2, text="Rad", command=lambda:self.triggerEvent("rad"))
		
		# some extra operators
		self.mod = Button(self, font=self.font, width=2, height=2, text="%", command=lambda:self.triggerEvent("mod"))
		self.pow = Button(self, font=self.font, width=2, height=2, text="^", command=lambda:self.triggerEvent("pow"))
		self.factorial = Button(self, font=self.font, width=2, height=2, text="!", command=lambda:self.triggerEvent("factorial"))
		
		# and some basic math functions
		self.sqrt = Button(self, font=self.font, width=2, height=2, text=u"\u221A", command=lambda:self.triggerEvent("sqrt"))
		self.exp = Button(self, font=self.font, width=2, height=2, text=u"e^x", command=lambda:self.triggerEvent("exp"))
		self.log = Button(self, font=self.font, width=2, height=2, text=u"log", command=lambda:self.triggerEvent("log"))
		self.ln = Button(self, font=self.font, width=2, height=2, text=u"ln", command=lambda:self.triggerEvent("ln"))
		
		
		# and set their places
		self.sin.grid(column=0, row=0, sticky=N+E+S+W)
		self.asin.grid(column=1, row=0, sticky=N+E+S+W)
		self.cos.grid(column=0, row=1, sticky=N+E+S+W)
		self.acos.grid(column=1, row=1, sticky=N+E+S+W)
		self.tan.grid(column=0, row=2, sticky=N+E+S+W)
		self.atan.grid(column=1, row=2, sticky=N+E+S+W)
		self.log.grid(column=0, row=3, sticky=N+E+S+W)
		self.ln.grid(column=1, row=3, sticky=N+E+S+W)
		self.deg.grid(column=0, row=4, sticky=N+E+S+W)
		self.rad.grid(column=1, row=4, sticky=N+E+S+W)		
		self.mod.grid(column=2, row=0, sticky=N+E+S+W)
		self.pow.grid(column=2, row=1, sticky=N+E+S+W)
		self.sqrt.grid(column=2, row=2, sticky=N+E+S+W)
		self.exp.grid(column=2, row=3, sticky=N+E+S+W)
		self.factorial.grid(column=2, row=4, sticky=N+E+S+W)
	
	def __bindKeys(self):
		""" Bind some keys to the buttons """
		
		# bind the operators only, could bind the rest as well
		self.bind_all("<KeyPress-percent>", lambda e: self.triggerEvent("mod"), add="+")
		self.bind_all("<KeyPress-asciicircum>", lambda e: self.triggerEvent("pow"), add="+")
		self.bind_all("<KeyPress-exclam>", lambda e: self.triggerEvent("factorial"), add="+")
	
	def triggerEvent(self, op):
		""" Triggers a virtual event for a operation """
		# trigger virtual event
		self.tk.call('event', 'generate', self._w, '<<ExtendedOperations-' + op +'>>')
