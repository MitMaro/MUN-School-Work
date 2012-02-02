/**
 * Tim Oram's Commons Libray - Command Line Interface
 * By: Tim Oram
 */
package ca.mitmaro.commandline.userinterface;

import java.io.IOException;
import java.io.File;

import ca.mitmaro.commandline.term.Terminal;

public class FilePrompt extends Prompt<File> {
	
	public FilePrompt(String question, Terminal term) {
		super(question, term);
	}
	
	public File waitForResponse() throws IOException {
		
		return new File(super.promptForNonEmptyInput());
	}
	
}
