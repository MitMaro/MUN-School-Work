/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline.userinterface;

import java.io.IOException;

import ca.mitmaro.commandline.term.Terminal;

/**
 * A basic prompt that requires the user to provide a response
 *
 * @param <T> The return type of this response
 */
public abstract class Prompt<T> extends TextBased<T> {
	
	/**
	 * The question the prompt asks
	 */
	private String question;
	
	/**
	 * Constructs this prompt with supplied question
	 * 
	 * @param question The question
	 * @param term A terminal instance
	 */
	public Prompt(String question, Terminal term) {
		super(term);
		this.question = question;
	}
	
	/**
	 * Waits the the user to input a non-empty input
	 * 
	 * @return The input when not empty
	 * @throws IOException
	 */
	protected String promptForNonEmptyInput() throws IOException {
		String line = null;
		do {
			this.terminal.out().println(this.question);
			this.printPrompt();
			
			line = this.terminal.in().readLine();
			
			if (!line.trim().equals("")) {
				return line;
			}
			
			this.terminal.out().println("Didn't understand the response");
			this.terminal.out().flush();
			
		} while (true);
	}
}
