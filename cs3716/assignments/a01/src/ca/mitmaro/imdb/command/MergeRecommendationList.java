/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #1                       //
//  Program File Name: IMDB.java                               //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.imdb.command;

import java.io.IOException;
import java.io.File;

import ca.mitmaro.commandline.command.Command;
import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.commandline.userinterface.NumberedMenu;
import ca.mitmaro.imdb.Application;

public class MergeRecommendationList extends Command {
	
	private NumberedMenu list_prompt;
	private Application application;
	
	public MergeRecommendationList(Application application, Terminal term) {
		super(term);
		
		this.application = application;
		
		this.list_prompt = new NumberedMenu(term);
		
		this.list_prompt.setTitle("Select recommendation list to merge");
		
		this.list_prompt.setPrompt("$ mergerec: ");
	}
	
	public int call(String[] args) {
		
		String list_name;
		
		this.list_prompt.setOptions(this.application.getRecommendationListNames());
		
		try {
			list_name = this.list_prompt.waitForResponse();
			
			// catch empty list
			if (list_name == null) {
				this.terminal.out.println(" ** No Recommedation Lists Loaded **");
				return 1;
			}
			
			this.application.mergeRecommendationList(list_name);
		} catch (IOException e) {
			this.terminal.out.println("An error occured. Aborting quit.");
			return 3;
		}
		
		return 1;
		
	}
}

