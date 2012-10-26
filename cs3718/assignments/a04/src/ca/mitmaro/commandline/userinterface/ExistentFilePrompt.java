/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline.userinterface;

import java.io.File;
import java.io.IOException;

import ca.mitmaro.commandline.term.Terminal;

/**
 * A text based prompt the validates on existing readable files
 * 
 * @author Tim Oram (MitMaro)
 *
 */
public class ExistentFilePrompt extends FilePrompt {
	
	/**
	 * @param question The question to ask
	 * @param term The terminal interface
	 */
	public ExistentFilePrompt(String question, Terminal term) {
		super(question, term);
	}

	/**
	 * Prompt for a file path and check it exists.
	 * 
	 * @return A file object pointing to an existing file.
	 * @throws IOException
	 */
	@Override
	public File waitForResponse() throws IOException {
		
		File f;
		
		do {
			f = super.waitForResponse();
			
			if (f.isFile() && f.canRead()) {
				return f;
			}
			
			this.terminal.out().println("File does not exist or is not readable");
			this.terminal.out().flush();
		} while (true);
	}
	
}
