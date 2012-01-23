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
import ca.mitmaro.commandline.userinterface.SimplePrompt;
import ca.mitmaro.commandline.userinterface.ExistentFilePrompt;
import ca.mitmaro.imdb.Application;


public class LoadRecommendation extends Command {
	
	private ExistentFilePrompt file_prompt;
	private SimplePrompt name_prompt;
	private Application application;
	
	public LoadRecommendation(Application application, Terminal term) {
		super(term);
		
		this.application = application;
		
		this.file_prompt = new ExistentFilePrompt("What is the recommendation list file path you wish to load?", term);
		this.name_prompt = new SimplePrompt("What is the recommendation list name?", term);
		
		this.file_prompt.setPrompt("$ loadrec: ");
		this.name_prompt.setPrompt("$ loadrec: ");
		
	}
	
	public int call(String[] args) {
		
		File file;
		String name;
		
		try {
			file = this.file_prompt.waitForResponse();
			name = this.name_prompt.waitForResponse();
			this.application.loadRecommendationsFile(file, name);
		} catch (IOException e) {
			this.terminal.out.println("An error occured. Aborting quit.");
			return 3;
		}
		
		
		
		
		
		return 1;
		
	}
}

