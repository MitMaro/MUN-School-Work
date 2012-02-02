/**
 * Tim Oram's Commons Libray - Command Line Interface
 * By: Tim Oram
 */
package ca.mitmaro.commandline.command;

import java.io.IOException;

import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.commandline.userinterface.YesNoQuestion;

public class Exit extends Command {
	
	private YesNoQuestion menu; 
	
	public Exit(Terminal terminal) {
		super(terminal);
		
		this.menu = new YesNoQuestion(terminal);
		
		this.menu.setTitle("\nAre you sure you wish to exit?");
		this.setPrompt("$ exit: ");
	}
	
	public void setPrompt(String prompt) {
		this.menu.setPrompt(prompt);
	}
	
	public int call(String[] args) {
		
		try {
			if (this.menu.waitForResponse().equals("yes")) {
				return 0; // 0 = good shutdown
			}
		} catch (IOException e) {
			this.terminal.out.println("An error occured. Aborting quit.");
		}
		return 1;
	}
	
}
