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
import ca.mitmaro.commandline.userinterface.YesNoQuestion;
import ca.mitmaro.imdb.Application;
import ca.mitmaro.imdb.entity.Movie;

public class DisplayRecommendationList extends Command {
	
	private NumberedMenu list_prompt;
	private YesNoQuestion sort_prompt;
	
	private Application application;
	
	public DisplayRecommendationList(Application application, Terminal term) {
		super(term);
		
		this.application = application;
		
		this.list_prompt = new NumberedMenu(term);
		this.list_prompt.setDefaultOption("master");
		this.list_prompt.setPrompt("$ displayrec: ");
		
		this.sort_prompt = new YesNoQuestion(term);
		this.sort_prompt.setTitle("Sort List?");
		this.sort_prompt.setPrompt("$ displayrec: ");
		
	}
	
	public int call(String[] args) {
		
		String list_name;
		boolean sorted;

		int index;
		
		ArrayList<Movie> movies;
		
		this.list_prompt.setOptions(this.application.getRecommendationListNames(true));
		
		try {
			list_name = this.list_prompt.waitForResponse();
			
			// abort sent
			if (list_name == null) {
				return 1;
			}
			
			sorted = this.sort_prompt.waitForResponse() == "yes";
			
			if (sorted) {
				if (list_name.equals("master")) {
					movies = this.application.getSortedMovies();
				} else {
					movies = this.application.getSortedMovies(list_name);
				}
			} else {
				if (list_name.equals("master")) {
					movies = this.application.getMovies();
				} else {
					movies = this.application.getMovies(list_name);
				}
			}
			
			if (list_name.equals("master")) {
				this.terminal.out.println("\nMaster List");
			} else {
				this.terminal.out.println("\n" + list_name);
			}
			
			index = 1;
			for (Movie movie: movies) {
				this.terminal.out.format("%3d: %s\n", index, this.application.getFormattedMovieRecommendation(movie));
				index++;
			}
			
		} catch (IOException e) {
			this.terminal.out.println("An error occured. Aborting quit.");
			return 3;
		}
		
		return 1;
		
	}
}

