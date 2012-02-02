/**
 * CS 3725 - Assignment #1
 * By: Tim Oram (200529220)
 * Simple MIPS Processor
 *
 * InstructionMemory - Code memoy (this is the .text section)
 */
package ca.mitmaro.mips;

import java.util.Arrays;

import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.commandline.help.Message;
import ca.mitmaro.commandline.command.Exit;
import ca.mitmaro.commandline.command.Help;
import ca.mitmaro.commandline.CommandLine;
import ca.mitmaro.commandline.userinterface.NumberedMenu;
import ca.mitmaro.mips.command.LoadProgram;
import ca.mitmaro.mips.command.LoadMemoryDump;
import ca.mitmaro.mips.command.PrintRegisters;
import ca.mitmaro.mips.command.Run;
import ca.mitmaro.mips.MIPS;

public class MIPSCommandLine extends CommandLine {
	
	private MIPS application;
	
	private NumberedMenu ui;
	
	public MIPSCommandLine(MIPS mips) {
		super(new Terminal());
		
		String[] menu_options = {
			"load",
			"mem",
			"run",
			"regs",
			"help",
			"exit"
		};
		
		this.ui = new NumberedMenu(this.terminal);
		this.ui.setOptions(Arrays.asList(menu_options));
		this.ui.setTitle("\n*** Commands ***");
		this.ui.disableAbortPrompt();
		this.ui.setPrompt("What now> ");
		
		this.application = mips;
		
		Exit exit = new Exit(this.terminal);
		exit.setPrompt("exit> ");
		
		this
			.addCommand("help", new Help(this.help, this.terminal), new Message("help", "Print a list of all system commands."))
			.addCommand("exit", exit, new Message("exit", "Exit the MIPS Simulator."))
			.addCommand(
				"load",
				new LoadProgram(
					this.application,
					this.terminal
				),
				new Message(
					"load",
					"Load a MIPS binary file into the simulator memory."
				)
			)
			.addCommand(
				"mem",
				new LoadMemoryDump(
					this.application,
					this.terminal
				),
				new Message(
					"mem",
					"Load a memory dump file into the simulator memory."
				)
			)
			.addCommand(
				"run",
				new Run(
					this.application,
					this.terminal
				),
				new Message(
					"run",
					"Run the simulation."
				)
			)
			.addCommand(
				"regs",
				new PrintRegisters(
					this.application,
					this.terminal
				),
				new Message(
					"regs",
					"Print the registers to the screen."
				)
			)
		;
	}
	
	
	
	public int mainloop() {
		
		int code;
		String response;
		
		while (true) {
			
			try {
				
				response = this.ui.waitForResponse();
				
				if (response == null) {
					continue;
				}
				
				if ((code = this.runCommand(response)) != 1) {
					return code;
				}
			} catch (IllegalArgumentException e) {
				this.terminal.out.println("Error: " + e.getMessage() + "\n");
				this.runCommand("help");
				continue;
			} catch (Exception e) {
				// because i don't want to start over with testing if i make a typo
				System.err.println("Opps, something went wrong...");
				e.printStackTrace();
				continue;
			}
		}
	}
	
}
