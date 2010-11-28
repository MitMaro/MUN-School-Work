/*
Computer Science 3715
Assignment #4
Loan Calculator

By: Tim Oram
Student Number: #########
*/

/*jslint white: true, browser: true, onevar: true, undef: true, nomen: true,
eqeqeq: true, plusplus: true, bitwise: true, regexp: true, newcap: true,
immed: true */

"use strict";

function Page(param) {
	
	// define dom elements to use
	this.calculateInput = document.getElementById("calculate");
	
	this.principalInput = document.getElementById("principal");
	this.principalLabel = document.getElementById("principal_label");
	this.interestInput = document.getElementById("interest");
	this.interestLabel = document.getElementById("interest_label");
	this.monthsInput = document.getElementById("months");
	this.monthsLabel = document.getElementById("months_label");
	
	this.monthlyPayment = document.getElementById("monthly_payment");
	this.totalPayment = document.getElementById("total_payment");
	this.interestPayment = document.getElementById("interest_payment");
	
	var self = this;
	
	// some functions
	this.bindEvents = function () {
		// bind some events
		this.calculateInput.onclick = function () {
			var valid = true;
			
			// do some resets
			self.reset();
			self.principalLabel.setAttribute("class", "");
			self.principalInput.setAttribute("class", "");
			self.interestLabel.setAttribute("class", "");
			self.interestInput.setAttribute("class", "");
			self.monthsLabel.setAttribute("class", "");
			self.monthsInput.setAttribute("class", "");
			
			// check if everything is valid
			if (!self.validatePrincipal()) {
				valid = false;
			}
			if (!self.validateMonths()) {
				valid = false;
			}
			
			if (!self.validateInterest()) {
				valid = false;
			}
			
			// was valid so perform the calculation
			if (valid) {
				self.calculateLoan();
			}
			
		};
		
		// bind some events on the inputs
		this.principalInput.onchange = function (evt) {
			self.validatePrincipal(true);
		};
		
		this.interestInput.onchange = function (evt) {
			self.validateInterest(true);
		};
		
		this.monthsInput.onchange = function (evt) {
			self.validateMonths(true);
		};
	
	};
	
	this.validatePrincipal = function (allowEmpty) {
		var value = self.principalInput.value;
		
		// remove a possible error class
		self.principalLabel.setAttribute("class", "");
		self.principalInput.setAttribute("class", "");
		
		// if the value is empty and an
		if (value === "" && allowEmpty === true) {
			// empty is invalid but not an "error" state
			return false;
		}
		
		// validate
		if (!value.match(/^\d+([.]\d{2})?$/) || value < 0 || value > 10000000) {
			// invalid
			self.principalLabel.setAttribute("class", "error");
			self.principalInput.setAttribute("class", "error");
			self.reset();
			return false;
		}
		
		// valid
		self.principalLabel.setAttribute("class", "valid");
		self.principalInput.setAttribute("class", "valid");
		return true;
	};
	
	this.validateInterest = function (allowEmpty) {
		var value = self.interestInput.value;
		
		// remove a possible error class
		self.interestLabel.setAttribute("class", "");
		self.interestInput.setAttribute("class", "");
		
		// if the value is empty and an
		if (value === "" && allowEmpty === true) {
			// invalid, non-error state
			return false;
		}
		
		// validate
		if (!value.match(/^\d+([.]\d{1,2})?$/) || value < 1 || value > 100) {
			// invalid
			self.interestLabel.setAttribute("class", "error");
			self.interestInput.setAttribute("class", "error");
			self.reset();
			return false;
		}
		
		// valid
		self.interestLabel.setAttribute("class", "valid");
		self.interestInput.setAttribute("class", "valid");
		return true;
	};
	
	this.validateMonths = function (allowEmpty) {
		var value = self.monthsInput.value;
		
		// remove a possible error class
		self.monthsLabel.setAttribute("class", "");
		self.monthsInput.setAttribute("class", "");
		
		// if the value is empty and an
		if (value === "" && allowEmpty === true) {
			// invalid, non-error state
			return false;
		}
		
		// validate
		if (!value.match(/^\d+$/) || value < 0 || value > 720) {
			// invalid
			self.monthsLabel.setAttribute("class", "error");
			self.monthsInput.setAttribute("class", "error");
			self.reset();
			return false;
		}
		
		// valid
		self.monthsLabel.setAttribute("class", "valid");
		self.monthsInput.setAttribute("class", "valid");
		return true;
	};
	
	this.calculateLoan = function () {
		var
			principal = self.principalInput.value,
			// given interest divided by 100 and divided by 12
			interest = self.interestInput.value / 100 / 12,
			months = self.monthsInput.value,
			
			monthlyPayment = principal *
				(interest / (1 - (1 / Math.pow(1 + interest, months)))),
			totalPayment = months * monthlyPayment,
			interestPayment = totalPayment - principal
		;
		
		self.monthlyPayment.value = monthlyPayment.toFixed(2);
		self.totalPayment.value = totalPayment.toFixed(2);
		self.interestPayment.value = interestPayment.toFixed(2);
	};
	
	// reset some values
	this.reset = function () {
		// reset value
		self.monthlyPayment.value = "";
		self.totalPayment.value = "";
		self.interestPayment.value = "";
	};
	
}

// only run js on the windows load event
window.onload = function () {
	var page = new Page();
	page.bindEvents();
};
