/**
 * CS 3725 - Assignment #1
 * By: Tim Oram (200529220)
 * Simple MIPS Processor
 *
 * Control Unit - One controller to rule them all
 */
package ca.mitmaro.mips.command;


import ca.mitmaro.commandline.command.Command;
import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.mips.MIPS;

public class Run extends Command {
	
	MIPS proc;
	
	public Run(MIPS proc, Terminal term) {
		super(term);
		this.proc = proc;
	}
	
	public int call(String[] args) {
		this.proc.run();
		return 1;
	}
}
