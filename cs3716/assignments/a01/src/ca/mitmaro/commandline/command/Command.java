/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #1                       //
//  Program File Name: IMDB.java                               //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline.command;

import ca.mitmaro.commandline.term.Terminal;

public abstract class Command {
	
	protected Terminal terminal;
	
	public Command(Terminal term) {
		this.terminal = term;
	}
	
	public abstract int call(String[] args);
}

