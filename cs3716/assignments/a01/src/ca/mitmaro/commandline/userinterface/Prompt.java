/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #1                       //
//  Program File Name: IMDB.java                               //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline.userinterface;

import java.io.IOException;

import ca.mitmaro.commandline.term.Terminal;

public abstract class Prompt<T> extends TextBased<T> {
	
	private String question;
	
	public Prompt(String question, Terminal term) {
		super(term);
		this.question = question;
	}
	
	protected String promptForNonEmptyInput() throws IOException {
		String line = null;
		do {
			this.terminal.out.println(this.question);
			this.printPrompt();
			
			line = this.terminal.in.readLine();
			
			if (!line.trim().equals("")) {
				return line;
			}
			
			this.terminal.out.println("Didn't understand the response");
			this.terminal.out.flush();
			
		} while (true);
	}
	
}
