/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline.userinterface;

import java.io.IOException;
import java.io.File;

import ca.mitmaro.commandline.term.Terminal;

public class FilePrompt extends Prompt<File> {
	
	/**
	 * @param question The question to prompt
	 * @param term The terminal interface
	 */
	public FilePrompt(String question, Terminal term) {
		super(question, term);
	}

	/**
	 * Prompt for a file path
	 * 
	 * @return A file object pointing to a file.
	 * @throws IOException
	 */
	public File waitForResponse() throws IOException {
		
		return new File(super.promptForNonEmptyInput());
	}
	
}
