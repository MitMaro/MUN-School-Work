#########################################################
##  CS 2500 (Fall 2008), Assignment #8, Question #1    ##
##   Script File Name: CalcFunctions.py                ##
##       Student Name: Tim Oram                        ##
##         Login Name: oram                            ##
##              MUN #: #########                       ##
#########################################################
#    File: CalcFunctions.py
# Project: CS 2500 (Assignment 8, Question 1)
#      By: Tim Oram [t.oram@mitmaro.ca]
# Created: November 24, 2008; Updated December 03, 2008
# Purpose: Contains various math functions
"""
This file defines some functions used by the calculator.
"""
import operator, math
from decimal import Decimal

# operators
def add(a, b): return a + b
def subtract(a, b): return a - b
def multiply(a, b): return a * b
def divide(a, b):
	if(b.quantize(b) == Decimal("0").quantize(b)):
		return Decimal("NAN")
	return a / b
def pow(a, b): return a ** b
def fmod(a, b): return a % b

# factorial
def factorial(a):
	# don't do factorials over 10000 (cause they can take forever)
	if a >= 10000:
		return Decimal("NAN")
	return reduce(operator.mul, xrange(2, a+1))

def log(a, b):
	if(a.quantize(a) == Decimal("1").quantize(a)):
		return Decimal("NAN")
	return math.log(b, a)
	
	