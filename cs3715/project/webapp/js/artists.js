/*
Computer Science 3715
Team Project
Music Wishlist

By: Lauren Stratton #########
    Tim Oram        #########
*/


/*jslint white: true, browser: true, onevar: true, undef: true, nomen: true,
eqeqeq: true, plusplus: true, bitwise: true, regexp: true, newcap: true,
immed: true */
/*global alert window Element confirm*/

"use strict";

// I need appendAfter, which doesn't exist, so add it to the Element
Element.prototype.appendAfter = function (node) {
	if (this.nextSibling) {
		this.parentNode.insertBefore(node, this.nextSibling);
	} else {
		this.parentNode.appendChild(node);
	}
};

function Page() {
	
	// define dom elements to use
	this.elements = {
		'addNewShow': document.getElementById("add_control"),
		'addEditForm': document.getElementById("form-add-edit"),
		'addFormRow': document.getElementById("add_inputs"),
		'editFormRow': document.getElementById("edit_inputs"),
		'inputs': {
			'priority': document.getElementById("addEdit-artist_priority"),
			'edit': {
				'name': document.getElementById("edit-artist_name"),
				'id': document.getElementById("edit-artist_id"),
				'hearts': {
					'one': document.getElementById("edit-heart_one"),
					'two': document.getElementById("edit-heart_two"),
					'three': document.getElementById("edit-heart_three"),
					'four': document.getElementById("edit-heart_four"),
					'five': document.getElementById("edit-heart_five")
				}
			},
			'add': {
				'name': document.getElementById("add-artist_name"),
				'hearts': {
					'one': document.getElementById("add-heart_one"),
					'two': document.getElementById("add-heart_two"),
					'three': document.getElementById("add-heart_three"),
					'four': document.getElementById("add-heart_four"),
					'five': document.getElementById("add-heart_five")
				}
			}
		},
		'listLoader': document.getElementById("list-loader"),
		'artistsList': document.getElementById("list"),
		'listControls': document.getElementById("controls"),
		'emptyMessage': document.getElementById("empty"),
		'columns': {
			'name': document.getElementById("header-artist"),
			'priority': document.getElementById("header-priority")
		},
		'paginate': document.getElementById("paginate")
	};
	
	this.constants = {
		'sortImages': {
			'asc_active': 'images/arrow_down_dark_grey_8x8.png',
			'desc_active': 'images/arrow_up_dark_grey_8x8.png',
			'asc_inactive': 'images/arrow_down_light_grey_8x8.png'
		}
	};
	
	this.page = 1;
	this.sortby = 'name';
	this.order = 'ASC';
	
	var self = this; // scope bug fix
	
	
	// bind dom events
	this.bindEvents = function () {
		// the form
		self.elements.addNewShow.onclick = self.showAdd;
		self.elements.inputs.add.name.onkeyup = self.hideAdd;
		self.elements.inputs.edit.name.onkeyup = self.hideEdit;
		self.elements.addEditForm.onsubmit = self.addEditHandler;
		
		// setup hearts to set value of priority input 
		self.elements.inputs.add.hearts.one.onclick = function () {
			self.setPriority('LOWEST');
		};
		self.elements.inputs.add.hearts.two.onclick = function () {
			self.setPriority('LOW');
		};
		self.elements.inputs.add.hearts.three.onclick = function () {
			self.setPriority('MEDIUM');
		};
		self.elements.inputs.add.hearts.four.onclick = function () {
			self.setPriority('HIGH');
		};
		self.elements.inputs.add.hearts.five.onclick = function () {
			self.setPriority('HIGHEST');
		};
		self.elements.inputs.edit.hearts.one.onclick = function () {
			self.setPriority('LOWEST');
		};
		self.elements.inputs.edit.hearts.two.onclick = function () {
			self.setPriority('LOW');
		};
		self.elements.inputs.edit.hearts.three.onclick = function () {
			self.setPriority('MEDIUM');
		};
		self.elements.inputs.edit.hearts.four.onclick = function () {
			self.setPriority('HIGH');
		};
		self.elements.inputs.edit.hearts.five.onclick = function () {
			self.setPriority('HIGHEST');
		};
		
		// setup coumn headers
		self.elements.columns.name.data = {'sort': 'name', 'order': 'DESC'};
		self.elements.columns.name.onclick = self.refreshHandler;
		self.elements.columns.priority.data = {'sort': 'priority', 'order': 'ASC'};
		self.elements.columns.priority.onclick = self.refreshHandler;
	};
	
	// shows the add artists form
	this.showAdd = function (event) {
		self.hideEdit(); // hide edit on show of add
		self.elements.addNewShow.style.display = 'none';
		self.elements.addFormRow.style.display = 'table-row';
		self.elements.inputs.add.name.focus();
	};
	
	// hides the add artists form and clears some values
	this.hideAdd = function (event) {
		// break out on keypress events that are not the escape key
		if (event && event.which && event.which !== 27) {
			return;
		}
		self.elements.inputs.add.name.value = '';
		self.elements.addNewShow.style.display = 'table-row';
		self.elements.addFormRow.style.display = 'none';
	};
	
	// shows the edit form, placing it in place of the rom being edited
	this.showEdit = function (event) {
		
		var
			artist = event.target.data.artist,
			row = event.target.parentNode.parentNode
		;
		
		self.hideAdd(); // hide add on show of edit
		self.hideEdit(); // hide edit on show of edit
		
		self.elements.editFormRow.style.display = 'table-row';
		// set elements
		self.elements.inputs.edit.id.value = artist.id;
		self.elements.inputs.edit.name.value = artist.name;
		self.setPriority(artist.priority);
		self.elements.inputs.edit.name.focus();
		
		// place the form after the row being edited
		row.appendAfter(self.elements.editFormRow);
		
		row.style.display = 'none';
		
		self.elements.inputs.edit.name.focus();
		
	};
	
	// hide the edit form, also clear some values
	this.hideEdit = function (event) {
		
		// break out on keypress events that are not the escape key
		if (event && event.which && event.which !== 27) {
			return;
		}
		
		self.elements.inputs.edit.id.value = '';
		self.elements.inputs.edit.name.value = '';
		self.setPriority('MEDIUM');
		self.elements.editFormRow.style.display = 'none';
		
		// show the previous hidden row
		if (self.elements.editFormRow.previousSibling && self.elements.editFormRow.previousSibling.style) {
			self.elements.editFormRow.previousSibling.style.display = 'table-row';
		}
		
	};
	
	// reset the src of the heart elements to all blank
	this.resetHearts = function () {
		var
			key,
			heart
		;
		for (key in self.elements.inputs.add.hearts) {
			if (self.elements.inputs.add.hearts.hasOwnProperty(key)) { // make sure index belongs to this data
				heart = self.elements.inputs.add.hearts[key];
				heart.src = 'images/heart_stroke_dark_grey_12x11.png';
			}
		}
		for (key in self.elements.inputs.edit.hearts) {
			if (self.elements.inputs.edit.hearts.hasOwnProperty(key)) { // make sure index belongs to this data
				heart = self.elements.inputs.edit.hearts[key];
				heart.src = 'images/heart_stroke_dark_grey_12x11.png';
			}
		}
		self.elements.inputs.priority.value = 'LOW';
	};
	
	// handles a refresh click (ie. change sort order)
	this.refreshHandler = function (event) {
		
		var
			target = event.target,
			sortby,
			order
		;
		
		// get the right target (walk up tree looking for the td)
		while (!target.data || !target.data.sort) {
			target = target.parentNode;
		}
		
		sortby = target.data.sort;
		order = target.data.order;
		
		// reset columns
		self.elements.columns.name.data.order = 'ASC';
		self.elements.columns.name.getElementsByTagName('img')[0].src = self.constants.sortImages.asc_inactive;
		self.elements.columns.priority.data.order = 'ASC';
		self.elements.columns.priority.getElementsByTagName('img')[0].src = self.constants.sortImages.asc_inactive;
		
		// figure out sorting images
		if (order === "ASC") {
			target.data.order = 'DESC';
			target.getElementsByTagName('img')[0].src = self.constants.sortImages.asc_active;
		} else {
			target.data.order = 'ASC';
			target.getElementsByTagName('img')[0].src = self.constants.sortImages.desc_active;
		}
		
		// save for later use
		self.sortby = sortby;
		self.order = order;
		self.page = 1;
		
		self.refreshList(sortby, order);
	};
	
	// refreshes the list using an ajax call
	this.refreshList = function (sortby, order) {
		var
			ajaxRequest = new XMLHttpRequest(),
			requestDone = false
		;
		
		// toggle the loader image and comments list
		self.elements.artistsList.style.display = "none";
		self.elements.listControls.style.display = "none";
		self.elements.emptyMessage.style.display = "none";
		self.elements.listLoader.style.display = "table-row-group";
		
		// clear artists list, this may leak memory
		while (self.elements.artistsList.hasChildNodes()) {
			self.elements.artistsList.removeChild(self.elements.artistsList.firstChild);
		} 
		
		// this ajax requests handler
		ajaxRequest.onreadystatechange = function () {
			var
				artists,
				artist,
				jsonData,
				index
			;
			
			// when done
			if (!requestDone && ajaxRequest && ajaxRequest.readyState === 4) {
				requestDone = true; // stops double calls, which I think can happen
				
				// check for errors
				if (ajaxRequest.status === 200) {
					
					// JSON is the wonderful JSON parser by Douglas Crockford, better than eval
					jsonData = JSON.parse(ajaxRequest.responseText);
					
					// handle errors from server
					if (!jsonData.status || jsonData.status.code !== 200) {
						alert("Error: " + jsonData.status.message);
					} else {
						artists = jsonData.data;
						
						// success, update the artists list 
						if (artists.length > 0) {
							for (index in artists) {
								if (artists.hasOwnProperty(index)) { // make sure index belongs to this data
									self.addToList(artists[index]);
								}
							}
							self.elements.artistsList.style.display = "table-row-group";
						} else { // no artists
							self.elements.emptyMessage.style.display = "table-row-group";
						}
						
						// generate the pages links 
						self.generatePaginate(jsonData.page, jsonData.totalResults);
						
						self.elements.listControls.style.display = "table-row-group";
						self.elements.listLoader.style.display = "none";
						
					}
				} else { // error response
					alert("There was an AJAX error. Error: " + ajaxRequest.statusText);
				}
			}
		};
		
		// submit the request
		ajaxRequest.open(
			"GET", "artists/?sortby=" + sortby + "&order=" + order + "&page=" + self.page, true
		);
		ajaxRequest.send(null);
		
	};
	
	// creates the dom elements (table row) for the provided artist and adds it to the table list
	this.addToList = function (artist) {
		var
			row,
			col,
			heart1, heart2, heart3, heart4, heart5,
			edit,
			trash
		;
		
		row = document.createElement('tr');
		
		// artist name
		col = document.createElement('td');
		col.setAttribute('class', 'name');
		col.innerHTML = artist.name;
		row.appendChild(col);
		
		// artist priority
		col = document.createElement('td');
		col.setAttribute('class', 'priority');
		
		heart1 = document.createElement('img');
		heart1.setAttribute('alt', "Lowest Priority");
		heart1.setAttribute('title', "Lowest Priority");
		heart1.src = "images/heart_stroke_dark_grey_12x11.png";
		heart2 = document.createElement('img');
		heart2.setAttribute('alt', "Low Priority");
		heart2.setAttribute('title', "Low Priority");
		heart2.src = "images/heart_stroke_dark_grey_12x11.png";
		heart3 = document.createElement('img');
		heart3.setAttribute('alt', "Medium Priority");
		heart3.setAttribute('title', "Medium Priority");
		heart3.src = "images/heart_stroke_dark_grey_12x11.png";
		heart4 = document.createElement('img');
		heart4.setAttribute('alt', "High Priority");
		heart4.setAttribute('title', "High Priority");
		heart4.src = "images/heart_stroke_dark_grey_12x11.png";
		heart5 = document.createElement('img');
		heart5.setAttribute('alt', "Highest Priority");
		heart5.setAttribute('title', "Highest Priority");
		heart5.src = "images/heart_stroke_dark_grey_12x11.png";
		
		// fill the hearts when needed
		switch (artist.priority) {
			case "HIGHEST":
				heart5.src = "images/heart_fill_orange_12x11.png";
			case "HIGH":
				heart4.src = "images/heart_fill_orange_12x11.png";
			case "MEDIUM":
				heart3.src = "images/heart_fill_orange_12x11.png";
			case "LOW":
				heart2.src = "images/heart_fill_orange_12x11.png";
			case "LOWEST":
				heart1.src = "images/heart_fill_orange_12x11.png";
		}
		
		col.appendChild(heart1);
		col.appendChild(heart2);
		col.appendChild(heart3);
		col.appendChild(heart4);
		col.appendChild(heart5);
		
		row.appendChild(col);
		
		// controls
		col = document.createElement('td');
		col.setAttribute('class', 'controls');
		
		edit = document.createElement('img');
		edit.setAttribute('src', 'images/pen_alt_fill_dark_grey_12x12.png');
		edit.setAttribute('alt', 'Edit Artist');
		edit.setAttribute('title', 'Edit Artist');
		edit.onclick = self.showEdit;
		// add artist information to the edit element for easy retrieval later
		edit.data = {'artist': artist};
		
		trash = document.createElement('img');
		trash.setAttribute('src', 'images/trash_stroke_dark_grey_12x12.png');
		trash.setAttribute('alt', 'Edit Artist');
		trash.setAttribute('title', 'Edit Artist');
		trash.onclick = self.deleteArtist;
		// add artist information to the delete element for easy retrieval later
		trash.data = {'artist': artist};
		
		col.appendChild(edit);
		col.appendChild(trash);
		
		row.appendChild(col);
		
		self.elements.artistsList.appendChild(row);
		
	};
	
	// goto a particular page
	this.gotoPage = function (page) {
		self.page = page;
		self.refreshList(self.sortby, self.order, page);
		return false;
	};
	
	// generates the dom elements to show page links
	this.generatePaginate = function (page, totalResults) {
		
		var
			node,
			totalPages = Math.ceil(totalResults / 10),
			index
		;
		
		// generate a link
		function generatePaginateNode (p) {
			var node;
			// make the current page a span
			if (p === page) {
				node = document.createElement("span");
				node.innerHTML = p;
			} else {
				node = document.createElement("a");
				node.setAttribute("href", "");
				node.innerHTML = p;
				node.onclick = function () {
					return self.gotoPage(p);
				};
			}
			return node;
		};
		
		// clear paginate list, this may leak memory
		while (self.elements.paginate.hasChildNodes()) {
			self.elements.paginate.removeChild(self.elements.paginate.firstChild);
		}
		
		// only create the left arrow if not on the first page
		if (page > 1) {
			node = document.createElement('img');
			node.setAttribute('src', "images/arrow_left_dark_grey_12x12.png");
			node.setAttribute('alt', "Previous Page");
			node.setAttribute('title', "Previous Page");
			// funny wrapping of functions to handle scope
			(function (p) {
				node.onclick = function () {
					return self.gotoPage(p - 1);
				};
			})(page);
			
			self.elements.paginate.appendChild(node);
		}
		
		// create the numbers
		for (index = 1; index <= totalPages; index += 1) {
			node = generatePaginateNode(index);
			self.elements.paginate.appendChild(node);
		}
		
		
		// only show right arrow if not on the last page
		if (page < totalPages) {
			node = document.createElement('img');
			node.setAttribute('src', "images/arrow_right_dark_grey_12x12.png");
			node.setAttribute('alt', "Next Page");
			node.setAttribute('title', "Next Page");
			// funny wrapping of functions to handle scope
			(function (p) {
				node.onclick = function () {
					return self.gotoPage(p + 1);
				};
			})(page);
			
			self.elements.paginate.appendChild(node);
		}
		
	};
	
	// takes care of setting the hidden priority input and making the hearts solid
	this.setPriority = function (value) {
		self.resetHearts();
		// set the source attribute on the heart images to make them solid
		switch (value) {
			case "HIGHEST":
				self.elements.inputs.add.hearts.five.src = "images/heart_fill_orange_12x11.png";
				self.elements.inputs.edit.hearts.five.src = "images/heart_fill_orange_12x11.png";
			case "HIGH":
				self.elements.inputs.add.hearts.four.src = "images/heart_fill_orange_12x11.png";
				self.elements.inputs.edit.hearts.four.src = "images/heart_fill_orange_12x11.png";
			case "MEDIUM":
				self.elements.inputs.add.hearts.three.src = "images/heart_fill_orange_12x11.png";
				self.elements.inputs.edit.hearts.three.src = "images/heart_fill_orange_12x11.png";
			case "LOW":
				self.elements.inputs.add.hearts.two.src = "images/heart_fill_orange_12x11.png";
				self.elements.inputs.edit.hearts.two.src = "images/heart_fill_orange_12x11.png";
			case "LOWEST":
				self.elements.inputs.add.hearts.one.src = "images/heart_fill_orange_12x11.png";
				self.elements.inputs.edit.hearts.one.src = "images/heart_fill_orange_12x11.png";
		}
		self.elements.inputs.priority.value = value;
	};
	
	// handle an add or edit event making an ajax to save the data
	this.addEditHandler = function (event) {
		var
			ajaxRequest = new XMLHttpRequest(),
			requestDone = false,
			qs
		;
		
		// if an edit
		if (self.elements.inputs.edit.id.value > 0) {
			qs = "name=" + self.elements.inputs.edit.name.value +
				"&priority=" + self.elements.inputs.priority.value +
				"&id=" + self.elements.inputs.edit.id.value;
		} else { // else an add
			qs = "name=" + self.elements.inputs.add.name.value +
				"&priority=" + self.elements.inputs.priority.value;
		}
		
		// this ajax requests handler
		ajaxRequest.onreadystatechange = function () {
			// when done
			if (!requestDone && ajaxRequest && ajaxRequest.readyState === 4) {
				requestDone = true; // stops double calls, which I think can happen
				
				// check for errors
				if (ajaxRequest.status === 200) {
					
					// JSON is the wonderful JSON parser by Douglas Crockford, better than eval
					var jsonData = JSON.parse(ajaxRequest.responseText);
					
					// handle errors from server
					if (!jsonData.status || jsonData.status.code !== 200) {
						alert("Error: " + jsonData.status.message);
					} else {
						self.refreshList(self.sortby, self.order, self.page);
					}
				} else { // error response
					alert("There was an AJAX error. Error: " + ajaxRequest.statusText);
				}
			}
		};
		
		self.hideAdd();
		self.hideEdit();
		
		// send POST request
		ajaxRequest.open("POST", "/artists/", true);
		ajaxRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		ajaxRequest.send(qs);
		
		return false;
	};
	
	// handle deleting of artists, using ajax
	this.deleteArtist = function (event) {
		var
			ajaxRequest = new XMLHttpRequest(),
			requestDone = false,
			qs,
			artist = event.target.data.artist
		;
		
		// are we sure?
		if (!confirm("Do you wish to delete this artist?")) {
			return;
		}
		
		qs = "id=" + artist.id + "&delete=true";
		
		// this ajax requests handler
		ajaxRequest.onreadystatechange = function () {
			// when done
			if (!requestDone && ajaxRequest && ajaxRequest.readyState === 4) {
				requestDone = true; // stops double calls, which I think can happen
				
				// check for errors
				if (ajaxRequest.status === 200) {
					
					// JSON is the wonderful JSON parser by Douglas Crockford, better than eval
					var jsonData = JSON.parse(ajaxRequest.responseText);
					
					// handle errors from server
					if (!jsonData.status || jsonData.status.code !== 200) {
						alert("Error: " + jsonData.status.message);
					} else {
						self.refreshList(self.sortby, self.order, self.page);
					}
				} else { // error response
					alert("There was an AJAX error. Error: " + ajaxRequest.statusText);
				}
			}
		};
		
		// just in case one is open
		self.hideAdd();
		self.hideEdit();
		
		// send POST request
		ajaxRequest.open("POST", "/artists/", true);
		ajaxRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		ajaxRequest.send(qs);
		
		
	};
}


// only run js on the windows load event
window.onload = function () {
	var page = new Page();
	page.bindEvents();
	page.refreshList('name', 'ASC', 1);
};
