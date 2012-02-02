/**
 * Tim Oram's Commons Libray - Command Line Interface
 * By: Tim Oram
 */
package ca.mitmaro.commandline.command;

import ca.mitmaro.commandline.term.Terminal;

public abstract class Command {
	
	protected Terminal terminal;
	
	public Command(Terminal term) {
		this.terminal = term;
	}
	
	public abstract int call(String[] args);
}

