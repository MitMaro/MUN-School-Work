/**
 * Tim Oram's Commons Libray - Command Line Interface
 * By: Tim Oram
 */
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
