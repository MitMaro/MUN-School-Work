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

public abstract class TextBased<R> {
	
	public final Terminal terminal;
	
	private String prompt = null;
	
	private String title;
	
	/**
	 * @param term A terminal interface
	 */
	public TextBased(Terminal term) {
		this.terminal = term;
	}
	
	/**
	 * @param title Set the title for the prompt
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Print the prompt title
	 */
	public void printTitle() {
		if (this.title != null) {
			this.terminal.out().println(this.title);
		}
	}
	
	/**
	 * Sets the prompt display string
	 * 
	 * @param prompt The prompt string
	 */
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	
	/**
	 * Prompts the prompt, defaulting to the terminal default if no custom
	 * prompt was previously provided.
	 */
	public void printPrompt() {
		if (this.prompt == null) {
			this.terminal.prompt();
		} else {
			this.terminal.out().print(this.prompt);
			this.terminal.out().flush();
		}
	}
	
	public abstract R waitForResponse() throws IOException;
	
}

