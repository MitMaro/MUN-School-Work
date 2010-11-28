/*------------------------------------------------------------------------------
    File: www/js/common.js
 Project: cs2760 - Assignment #8
 Version: 0.1.0
      By: Lauren Stratton, Simon McIlhargey, Tim Oram
------------------------------------------------------------------------------*/

var Page = {
	setup: function(){
		Page.dom = {}
		
		Page.dom.delete_links = $$("a.delete");

		Page.dom.form = $pick($('add_issue'), $('edit_issue'));
		Page.dom.inputs = {
			'name': $('name'),
			'due': $('due')
		};
		
		Page.dom.delete_links.addEvent('click', function(e){
			if(!confirm("Are you sure you wish to delete this issue?")) {
				e.stop();
			}
		});
		
		if(Page.dom.form) {
			Page.dom.inputs.name.addClass("validate['required']");
			new FormCheck(Page.dom.form, {
				submitByAjax: false,
				flashTips: true
			});
			new CalendarEightysix(Page.dom.inputs.due, {
				disallowUserInput: true,
				format: "%Y-%m-%d"
			});
		}
	}
}

window.addEvent('domready', function(){	
	Page.setup();
});
