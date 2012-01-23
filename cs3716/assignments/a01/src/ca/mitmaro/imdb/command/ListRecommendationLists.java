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
import java.util.ArrayList;

import ca.mitmaro.commandline.command.Command;
import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.commandline.userinterface.SimplePrompt;
import ca.mitmaro.commandline.userinterface.ExistentFilePrompt;
import ca.mitmaro.imdb.Application;


public class ListRecommendationLists extends Command {
	
	private Application application;
	
	public ListRecommendationLists(Application application, Terminal term) {
		super(term);
		this.application = application;
	}
	
	public int call(String[] args) {
		
				
		int index = 1;
		
		ArrayList<String> rec_lists = this.application.getRecommendationListNames();
		
		if (rec_lists.size() == 0) {
			this.terminal.out.println(" ** No Recommedation Lists Loaded **");
		}
		
		for (String list_name: rec_lists) {
			this.terminal.out.format("%4d: %s\n", index, list_name);
			index++;
		}
		return 1;
		
	}
}

