/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #1                       //
//  Program File Name: IMDB.java                               //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline.command;

import java.io.IOException;

import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.commandline.userinterface.YesNoQuestion;

public class Exit extends Command {
	
	YesNoQuestion menu; 
	
	public Exit(Terminal terminal) {
		super(terminal);
		
		this.menu = new YesNoQuestion(terminal);
		
		this.menu.setTitle("\nAre you sure you wish to exit?");
		this.menu.setPrompt("$ exit: ");
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
