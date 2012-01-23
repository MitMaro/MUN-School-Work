/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #1                       //
//  Program File Name: IMDB.java                               //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.imdb;

import java.util.Arrays;

import ca.mitmaro.commandline.CommandLine;
import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.commandline.userinterface.NumberedMenu;
import ca.mitmaro.commandline.command.Help;
import ca.mitmaro.commandline.command.Exit;
import ca.mitmaro.commandline.help.Message;

import ca.mitmaro.imdb.command.LoadRecommendation;
import ca.mitmaro.imdb.command.ListRecommendationLists;
import ca.mitmaro.imdb.command.MergeRecommendationList;
import ca.mitmaro.imdb.command.DisplayRecommendationList;
import ca.mitmaro.imdb.command.SearchRecommendationList;

public class ImdbCommandLine extends CommandLine {
	
	private Application application;
	
	private NumberedMenu ui;
	
	public ImdbCommandLine(Application application) {
		super(new Terminal());
		
		String[] menu_options = {
			"loadrec",
			"listrecs",
			"mergerec",
			"displayrec",
			"searchrec",
			"help",
			"exit"
		};
		
		this.ui = new NumberedMenu(this.terminal);
		this.ui.setOptions(Arrays.asList(menu_options));
		this.ui.setTitle("\n*** Operations ***");
		this.ui.disableAbortPrompt();
		this.ui.setPrompt("$ opeartion: ");
		
		this.application = application;
		
		this
			.addCommand("help", new Help(this.help, this.terminal), new Message("help", "Print a list of all system operations."))
			.addCommand("exit", new Exit(this.terminal), new Message("exit", "Exit the system."))
			.addCommand(
				"loadrec",
				new LoadRecommendation(
					this.application,
					this.terminal
				),
				new Message(
					"loadrec",
					"Load a movie recommendation list stored in a file into the system and store under a name."
				)
			)
			.addCommand(
				"listrecs",
				new ListRecommendationLists(
					this.application,
					this.terminal
				),
				new Message(
					"listrecs",
					"List all movie recommendation lists stored in the system."
				)
			)
			.addCommand(
				"mergerec",
				new MergeRecommendationList(
					this.application,
					this.terminal
				),
				new Message(
					"mergerec",
					"Merge a named movie recommendation list into the master movie recommendation list."
				)
			)
			.addCommand(
				"displayrec",
				new DisplayRecommendationList(
					this.application,
					this.terminal
				),
				new Message(
					"displayrec",
					"Display a movie recommedation list or the master recommendation list; sorted or in natural order."
				)
			)
			.addCommand(
				"searchrec",
				new SearchRecommendationList(
					this.application,
					this.terminal
				),
				new Message(
					"searchrec",
					"Display a movie recommedation based base on a search."
				)
			)
		;
	}
	
	public int mainloop() {
		
		int code;
		String response;
		
		while (true) {
			
			try {
				
				response = this.ui.waitForResponse();
				
				if (response == null) {
					continue;
				}
				
				if ((code = this.runCommand(response)) != 1) {
					return code;
				}
			} catch (IllegalArgumentException e) {
				this.terminal.out.println("Error: " + e.getMessage() + "\n");
				this.runCommand("help");
				continue;
			} catch (Exception e) {
				// because i don't want to start over with testing if i make a typo
				System.err.println("Opps, something went wrong...");
				e.printStackTrace();
				continue;
			}
		}
	}
	
	
}

