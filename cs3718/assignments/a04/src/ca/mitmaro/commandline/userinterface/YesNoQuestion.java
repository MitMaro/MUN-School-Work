/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline.userinterface;

import java.io.IOException;

import ca.mitmaro.commandline.term.Terminal;

public class YesNoQuestion extends TextBased<Boolean> {
	
	/**
	 * @param term A terminal interface
	 */
	public YesNoQuestion(Terminal term) {
		super(term);
	}
	
	/**
	 * Prompts with a yes no question and waits for the user to respond.
	 * This method will block until there is a response.
	 * 
	 * @return True if yes was selected, false if no was selected
	 * @throws IOException
	 */
	public Boolean waitForResponse() throws IOException {
		
		String line = null;
		do {
			this.printTitle();
			this.terminal.out().println("  1: Yes\t\t2: No");
			this.printPrompt();
			
			line = this.terminal.in().readLine();
			
			line = line.trim().toLowerCase();
			
			if (line.equals("yes") || line.equals("y") || line.equals("1")) {
				return true;
			} else if (line.equals("no") || line.equals("n") || line.equals("2")) {
				return false;
			}
			
			this.terminal.out().println("Didn't understand the response");
			this.terminal.out().flush();
			
		} while (true);
	}
	
}
