#########################################################
##  CS 3710 (Fall 2008), Assignment #4, Question #2    ##
##   Script File Name: procStudent.py                  ##
##       Student Name: Tim Oram                        ##
##         Login Name: mitmaro                         ##
##              MUN #: #########                       ##
#########################################################

import sys

# print a little usage message when the required paramaters are not given
if len(sys.argv) < 2:
    print "procStudent.py <student-data-file>"
    sys.exit(1) # halt script


# a student class because it makes things easier than handling multi dimensional
# dictionaries
class Student:
	def __init__(self, name, number):
		[self.last_name, self.first_name] = name.split(None, 1)
		self.number = number
		self.marks = {}

	def addMark(self, course, mark):
		self.marks[course] = mark

	# create a string representation of the student
	def __str__(self):
		# the student number and name formatted appropriately
		rtn = ["%s : %s, %s\n" % (self.number, self.last_name, self.first_name)]
		# check if mark exists
		if len(self.marks) == 0:
			rtn.append("   ----\n")
		else:
			# sort the course keys and add the marks
			course_keys = self.marks.keys()
			course_keys.sort()
			for course in course_keys:
				rtn.append("%10s%6s\n" % (course, self.marks[course]))
		return "".join(rtn).strip()


students = {}
# read the file and parse the lines
for line in file(sys.argv[1]):
	[student_number, command, argument] = map(str.strip, line.split(None, 2))
	if command == "NAME":
		# an error check, not really needed as the files are formatted correctly
		if student_number not in students:
			students[student_number] = Student(argument, student_number)
	elif command == "MARK":
		if student_number in students:
			[course, mark] = argument.split()
			students[student_number].addMark(course, mark)

# sort student keys/numbers
student_keys = students.keys()
student_keys.sort()
# print each student
for student_number in student_keys:
	print students[student_number]

