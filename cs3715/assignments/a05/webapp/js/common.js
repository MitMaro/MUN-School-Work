/*
Computer Science 3715
Assignment #5
Image Rotate With Comments

By: Tim Oram
Student Number: #########
*/


/*jslint white: true, browser: true, onevar: true, undef: true, nomen: true,
eqeqeq: true, plusplus: true, bitwise: true, regexp: true, newcap: true,
immed: true */
/*global alert window*/

"use strict";

function Page(param) {
	// define dom elements to use
	this.buttonPrev = document.getElementById("button-prev");
	this.buttonNext = document.getElementById("button-next");
	
	this.rotateImage = document.getElementById("rotate-image");

	this.commentsList = document.getElementById("comments-list");
	
	this.commentsLoader = document.getElementById("comments-loader");
	
	this.commentsForm = document.getElementById("comments-form");
	this.commentsFormLoader = document.getElementById("comments-form-loader");
	this.commentsFormList = document.getElementById("comments-form-list");
	this.commentsImageNameInput = document.getElementById("comment-image_name");
	
	// the list of images
	this.images = ['A.png', 'B.png', 'C.png', 'D.png'];
	this.currentImageIndex = 0;
	
	// because self is equal to this, and well I need this for scoping
	var self = this;
	
	// bind the dom events to their respective functions
	this.bindEvents = function () {
		self.buttonNext.onclick = self.rotateNext;
		self.buttonPrev.onclick = self.rotatePrev;
		self.commentsForm.onsubmit = self.postComment;
	};
	
	// the rotate to next image event handler
	this.rotateNext = function (event) {
		self.currentImageIndex += 1;
		if (self.currentImageIndex >= self.images.length) {
			self.currentImageIndex = 0;
		}
		
		self.update(self.images[self.currentImageIndex]);
	};

	// the rotate to previous image event handler
	this.rotatePrev = function (event) {
		self.currentImageIndex -= 1;
		if (self.currentImageIndex < 0) {
			self.currentImageIndex = self.images.length - 1;
		}
		
		self.update(self.images[self.currentImageIndex]);
	};
	
	// updates many things, usually when an image is changed
	this.update = function (imageName) {
		var
			// I do not, and will not, support IE!!!
			ajaxRequest = new XMLHttpRequest(),
			requestDone = false
		;
		
		// reset the form
		self.commentsForm.reset();
		
		// update the image
		self.rotateImage.src = "images/" + imageName;
		
		// update the hidden image name input
		self.commentsImageNameInput.value = imageName;
		
		// toggle the loader image and comments list
		self.commentsLoader.style.display = "block";
		self.commentsList.style.display = "none";
		
		// this ajax requests handler
		ajaxRequest.onreadystatechange = function () {
			var jsonData = null;
			
			// only do something on complete
			if (!requestDone && ajaxRequest && ajaxRequest.readyState === 4) {
				requestDone = true; // stops double calls, which I think can happen
				
				// toggle the loader image and comments list
				self.commentsLoader.style.display = "none";
				self.commentsList.style.display = "block";
				
				// check the response status to make sure all went well
				if (ajaxRequest.status === 200) {
					// JSON is the wonderful JSON parser by Douglas Crockford, better than eval
					jsonData = JSON.parse(ajaxRequest.responseText);
					
					// handle errors from server
					if (!jsonData.status || jsonData.status.code !== 200) {
						alert("Error: " + jsonData.status.message);
					} else {
						// populate the comments data
						self.populateComments(jsonData.data);
					}
				} else { // there was an error, show an error (sorry for the alert)
					alert("There was an AJAX error. Error: " + ajaxRequest.statusText);
				}
			}
		};
		
		// submit the request
		ajaxRequest.open("GET", "/comments/?image_name=" + imageName, true);
		ajaxRequest.send(null);
	};
	
	// given a json data object populate the comments list
	this.populateComments = function (data) {
		var
			commentsDom = "",
			index = null,
			comment = null
		;
		
		// create the list of comments
		for (index in data)
		{
			if (data.hasOwnProperty(index)) { // make sure index belongs to this data
				comment = data[index];
				commentsDom += "<li><span class='by'>" + comment.author +
					": </span>" + comment.comment + "</li>";
			}
		}
		
		// handle no comments
		if (commentsDom === "") {
			self.commentsList.innerHTML = "<li class='empty'>No Comments Yet</li>";
		} else {
			self.commentsList.innerHTML = commentsDom;
		}
	};
	
	// event handler for the form post
	this.postComment = function (event) {
		var
			// I do not, and will not, support IE!!!
			ajaxRequest = new XMLHttpRequest(),
			requestDone = false,
			qs = "",
			el = null,
			index = 0
		;
		
		// toggle the loader image and comments list
		self.commentsFormLoader.style.display = "block";
		self.commentsFormList.style.display = "none";

		// build the data string
		for (index = 0; index < self.commentsForm.elements.length; index += 1) {
			el = self.commentsForm.elements[index];
			if (el.type === "hidden" || el.type === "text" || el.tagName === "TEXTAREA") {
				qs += encodeURIComponent(el.name) + "=" + encodeURIComponent(el.value) + "&";
			}
		}
		
		// this ajax requests handler
		ajaxRequest.onreadystatechange = function () {
			// when done
			if (!requestDone && ajaxRequest && ajaxRequest.readyState === 4) {
				requestDone = true; // stops double calls, which I think can happen
				
				// toggle the loader image and comments list
				self.commentsFormLoader.style.display = "none";
				self.commentsFormList.style.display = "block";
				
				// check for errors
				if (ajaxRequest.status === 200) {
					
					// JSON is the wonderful JSON parser by Douglas Crockford, better than eval
					var jsonData = JSON.parse(ajaxRequest.responseText);
					
					// handle errors from server
					if (!jsonData.status || jsonData.status.code !== 201) {
						alert("Error: " + jsonData.status.message);
					} else {
						// success, update the comments list
						self.update(self.images[self.currentImageIndex]);
					}
				} else { // error response
					alert("There was an AJAX error. Error: " + ajaxRequest.statusText);
				}
			}
		};
		
		// send POST request
		ajaxRequest.open("POST", "/comments/", true);
		ajaxRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		ajaxRequest.send(qs);

		// this should stop the form from submitting
		return false;
	};
}

// only run js on the windows load event
window.onload = function () {
	var page = new Page();
	page.bindEvents();
	page.update("A.png"); // default image
};
