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

public class SimplePrompt extends Prompt<String> {
	
	public SimplePrompt(String question, Terminal term) {
		super(question, term);
	}
	
	public String waitForResponse() throws IOException {
		
		return this.promptForNonEmptyInput();
		
	}
}
