
import Tkinter as Tk

import Widgets

class App(Tk.Frame):
	def __init__(self, master = None):
		# call the frame constructor
		Tk.Frame.__init__(self, master)

		# give the calculator a 10 pixel border all around
		self.grid(padx=10, pady=10)

		# populate the widgets
		self.__populate()

		# set the binds (virtual and keys)
		#self.__setBinds()

	def __populate(self):
		""" Populate the widgets. """
		# the calculators display
		self.display = Widgets.Display(self)
		self.display.grid(row=0, columnspan=2, pady=5)

		# add the standard operations
		self.ops = Widgets.StandardOperations(self)
		self.ops.grid(row=2, column=1, padx=5)

		# add the number pad
		self.numpad = Widgets.NumberPad(self)
		self.numpad.grid(row=2,column=0, padx=5)

	def __setBinds(self):
		""" Binds the virtual events"""
		# bind numbers
		self.bind_all("<<NumberPadClick-0>>", lambda e: self.inputHandle("0"))
		self.bind_all("<<NumberPadClick-1>>", lambda e: self.inputHandle("1"))
		self.bind_all("<<NumberPadClick-2>>", lambda e: self.inputHandle("2"))
		self.bind_all("<<NumberPadClick-3>>", lambda e: self.inputHandle("3"))
		self.bind_all("<<NumberPadClick-4>>", lambda e: self.inputHandle("4"))
		self.bind_all("<<NumberPadClick-5>>", lambda e: self.inputHandle("5"))
		self.bind_all("<<NumberPadClick-6>>", lambda e: self.inputHandle("6"))
		self.bind_all("<<NumberPadClick-7>>", lambda e: self.inputHandle("7"))
		self.bind_all("<<NumberPadClick-8>>", lambda e: self.inputHandle("8"))
		self.bind_all("<<NumberPadClick-9>>", lambda e: self.inputHandle("9"))

		# bind the special inputs
		self.bind_all("<<NumberPadClick-.>>", lambda e: self.inputHandle("."))
		self.bind_all("<<NumberPadClick-minus>>", lambda e: self.inputHandle("-"))
		self.bind_all("<<NumberPadClick-Exponent>>", lambda e: self.inputHandle("e"))
		self.bind_all("<<NumberPadClick-FuncSep>>", lambda e: self.argsSeparatorHandle())
		self.bind_all("<<NumberPadClick-Pi>>", lambda e: self.setValue(str(math.pi)))
		self.bind_all("<<StandardOperation-=>>", lambda e: self.equals())

		# function binds
		self.bind_all("<<StandardOperation-+>>", lambda e: self.funcHandle('add'))
		self.bind_all("<<StandardOperation-*>>", lambda e: self.funcHandle('multiply'))
		self.bind_all("<<StandardOperation-minus>>", lambda e: self.funcHandle('subtract'))
		self.bind_all("<<StandardOperation-/>>", lambda e: self.funcHandle('divide'))

		# bind the history operations (next goes to prev cause the prev history is
		# next in the list)
		self.bind_all("<<ControlOperation-FuncPrev>>", lambda e: self.funcsHistoryNext())
		self.bind_all("<<ControlOperation-FuncNext>>", lambda e: self.funcsHistoryPrev())
		self.bind_all("<<ControlOperation-OpPrev>>", lambda e: self.ansHistoryNext())
		self.bind_all("<<ControlOperation-OpNext>>", lambda e: self.ansHistoryPrev())

		# bind the control operations
		self.bind_all("<<ControlOperation-Clear>>", lambda e: self.clear())
		self.bind_all("<<ControlOperation-ClearEntry>>", lambda e: self.clearEntry())
		self.bind_all("<<ControlOperation-Back>>",  lambda e: self.back())
		self.bind_all("<<ControlOperation-Quit>>",  lambda e: sys.exit())

		# bind the function panel
		self.bind_all("<<ExtendedOperations-sin>>", lambda e: self.funcHandle('sin'))
		self.bind_all("<<ExtendedOperations-asin>>", lambda e: self.funcHandle('asin'))
		self.bind_all("<<ExtendedOperations-cos>>", lambda e: self.funcHandle('cos'))
		self.bind_all("<<ExtendedOperations-acos>>", lambda e: self.funcHandle('acos'))
		self.bind_all("<<ExtendedOperations-tan>>", lambda e: self.funcHandle('tan'))
		self.bind_all("<<ExtendedOperations-atan>>", lambda e: self.funcHandle('atan'))
		self.bind_all("<<ExtendedOperations-log>>", lambda e: self.funcHandle('log'))
		self.bind_all("<<ExtendedOperations-ln>>", lambda e: self.funcHandle('ln'))
		self.bind_all("<<ExtendedOperations-deg>>", lambda e: self.funcHandle('todegree'))
		self.bind_all("<<ExtendedOperations-rad>>", lambda e: self.funcHandle('toradian'))
		self.bind_all("<<ExtendedOperations-mod>>", lambda e: self.funcHandle('mod'))
		self.bind_all("<<ExtendedOperations-pow>>", lambda e: self.funcHandle('pow'))
		self.bind_all("<<ExtendedOperations-sqrt>>", lambda e: self.funcHandle('sqrt'))
		self.bind_all("<<ExtendedOperations-exp>>", lambda e: self.funcHandle('exp'))
		self.bind_all("<<ExtendedOperations-factorial>>", lambda e: self.funcHandle('factorial'))


if __name__ == "__main__":
	# a Tk instance
	root = Tk.Tk()
	# make it not resizable
	root.resizable(0,0)

	# create an instance of the calculator app
	app = App(master = root)

	# set the title of the app
	app.master.title("Tim's Awesome Calculator")

	# and its starting position
	root.geometry("+250+100")

	# program doesn't do much without this
	root.mainloop()

