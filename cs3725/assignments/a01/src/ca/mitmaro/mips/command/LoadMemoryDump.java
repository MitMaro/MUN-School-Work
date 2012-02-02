/**
 * CS 3725 - Assignment #1
 * By: Tim Oram (200529220)
 * Simple MIPS Processor
 *
 * Control Unit - One controller to rule them all
 */
package ca.mitmaro.mips.command;

import java.io.File;
import java.io.IOException;

import ca.mitmaro.commandline.command.Command;
import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.commandline.userinterface.ExistentFilePrompt;
import ca.mitmaro.mips.MIPS;

public class LoadMemoryDump extends Command {
	
	MIPS proc;
	
	ExistentFilePrompt file_prompt;
	
	public LoadMemoryDump(MIPS proc, Terminal term) {
		super(term);
		this.proc = proc;
		
		this.file_prompt = new ExistentFilePrompt("MIPS memory dump file path?", term);
		this.file_prompt.setPrompt("mem> ");
	}
	
	public int call(String[] args) {
		
		File file;
		
		try {
			file = this.file_prompt.waitForResponse();
			this.proc.loadMemoryDump(file);
		} catch (IOException e) {
			this.terminal.out.println("An error occured. Aborting quit.");
			return 3;
		}
		return 1;
	}
}
