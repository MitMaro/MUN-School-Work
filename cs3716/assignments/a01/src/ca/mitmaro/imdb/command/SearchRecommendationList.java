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
import java.util.LinkedHashMap;

import ca.mitmaro.commandline.command.Command;
import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.commandline.userinterface.NumberedMenu;
import ca.mitmaro.commandline.userinterface.SimplePrompt;
import ca.mitmaro.imdb.Application;
import ca.mitmaro.imdb.entity.Movie;

public class SearchRecommendationList extends Command {
	
	private NumberedMenu list_prompt;
	private SimplePrompt query_prompt;
	
	private Application application;
	
	public SearchRecommendationList(Application application, Terminal term) {
		super(term);
		
		this.application = application;
		
		this.list_prompt = new NumberedMenu(term);
		this.list_prompt.setDefaultOption("master");
		this.list_prompt.setPrompt("$ searchrec: ");
		
		this.query_prompt = new SimplePrompt("Search phrase? (TITLE YEAR)", term);
		this.query_prompt.setPrompt("$ searchrec: ");
		
	}
	
	public int call(String[] args) {
		
		String search;
		String title;
		String list_name;
		int year;
		String tmp[];
		int tmp_index;
		
		Movie movie =null;
		
		this.list_prompt.setOptions(this.application.getRecommendationListNames(true));
		
		try {
			list_name = this.list_prompt.waitForResponse();
			
			// abort sent
			if (list_name == null) {
				return 1;
			}
			
			search = this.query_prompt.waitForResponse();
			
			try {
				year = Integer.parseInt(search.substring(search.lastIndexOf(" ")+1, search.length()));
			} catch (NumberFormatException e) {
				// pass - allowed to assume correct files
				return 1;
			}
			
			title = search.substring(0, search.lastIndexOf(" "));
			
			if (list_name.equals("master")) {
				movie = this.application.getMovieRecommendationFromList(title, year);
			} else {
				movie = this.application.getMovieRecommendationFromList(list_name, title, year);
			}
			
			if (movie == null) {
				this.terminal.out.println("Movie recommendation not found.");
				return 1;
			}
			
			this.terminal.out.println(this.application.getFormattedMovieRecommendation(movie));
			
		} catch (IOException e) {
			this.terminal.out.println("An error occured. Aborting quit.");
			return 3;
		}
		
		return 1;
		
	}
}

