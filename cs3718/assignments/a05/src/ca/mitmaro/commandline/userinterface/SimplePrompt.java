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
 * A very simple, wait for some non-empty response prompt
 * 
 * @author Tim Oram (MitMaro)
 *
 */
public class SimplePrompt extends Prompt<String> {
	
	/**
	 * @param question The question to ask
	 * @param term A terminal interface
	 */
	public SimplePrompt(String question, Terminal term) {
		super(question, term);
	}
	
	
	/**
	 * Prompts with a question and waits for the user to respond.
	 * This method will block until there is a response.
	 * 
	 * @return True if yes was selected, false if no was selected
	 * @throws IOException
	 */
	@Override
	public String waitForResponse() throws IOException {
		
		return this.promptForNonEmptyInput();
		
	}
}
