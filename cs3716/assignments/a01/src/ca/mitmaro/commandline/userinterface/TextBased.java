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

public abstract class TextBased<R> {
	
	public final Terminal terminal;
	
	private String prompt = null;
	
	private String title;
	
	public TextBased(Terminal term) {
		this.terminal = term;
	}
	
	public void setTitle(String title) {
		this.title = title;	
	}
	
	public void printTitle() {
		if (this.title != null) {
			this.terminal.out.println(this.title);
		}
	}
	
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	
	public void printPrompt() {
		if (this.prompt == null) {
			this.terminal.prompt();
		} else {
			this.terminal.out.print(this.prompt);
			this.terminal.out.flush();
		}
	}
	
	public abstract R waitForResponse() throws IOException;
	
}

