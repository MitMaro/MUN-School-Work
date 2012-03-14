/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline.command;

import java.io.IOException;
import java.util.ArrayList;

import ca.mitmaro.commandline.help.Message;
import ca.mitmaro.commandline.help.System;
import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.commandline.userinterface.YesNoQuestion;
import ca.mitmaro.lang.StringUtils;

public class DefaultCommands {
	
	private Terminal terminal;
	private System help;
	
	private YesNoQuestion exit_prompt;
	
	private boolean no_exit_prompt = false;
	
	/**
	 * @param help The help system
	 * @param term The terminal interface
	 */
	public DefaultCommands(System help, Terminal term) {
		this.terminal = term;
		this.exit_prompt = new YesNoQuestion(term);
		this.exit_prompt.setTitle("Are you sure you wish to exit?");
		this.help = help;
	}
	
	/**
	 * Displays a list of commands with their usage and help messages
	 * 
	 * @param args The arguments passed to this command
	 * @return Always returns a successful, aka 1, code. 
	 */
	@ca.mitmaro.commandline.command.annotation.Command(name="help")
	@ca.mitmaro.commandline.command.annotation.Help(usage="help", help="Print a list of all system commands.")
	public int helpCommand(String[] args) {
		
		if (args == null || args.length == 0) {
			this.terminal.out().println("Operation List\n");
			for (ArrayList<Message> msgs: this.help.messages.values()) {
				for (Message msg: msgs) {
					this.printHelpMessage(msg, "        ");
				}
			}
		} else if (args.length == 1) {
			// print usage for command
			if (this.help.messages.containsKey(args[0])) {
				int len = 0;
				for (Message msg: this.help.messages.get(args[0])) {
					if (msg.getUsage().length() > len) {
						len = msg.getUsage().length();
					}
					
				}
				for (Message msg: this.help.messages.get(args[0])) {
					this.printHelpMessage(msg, StringUtils.repeat(" ", len));
				}
			} else {
				throw new IllegalArgumentException("Provided command " + args[0] + " Parameter(s).");
			}
		} else {
			throw new IllegalArgumentException("Invalid Parameter(s).");
		}
		
		return 1;
	}
	
	/**
	 * Prints a single formatted command help and usage message.
	 * 
	 * @param help_msg The help message class to print
	 * @param padding Additional padding to add to the front of the output lines.
	 */
	private void printHelpMessage(Message help_msg, String padding) {
		
		int length = this.help.getMaxCommandNameLength() - help_msg.getUsage().length() + 3;
		
		int base_width = padding.length() + this.help.getMaxCommandNameLength();
		
		String command_padding = StringUtils.repeat(" ", this.help.getMaxCommandNameLength());
		
		// print usage
		this.terminal.out().print(padding + help_msg.getUsage());
		
		// command/column alignment
		this.terminal.out().print(StringUtils.repeat(" ", length));
		
		// print help message with word wrap
		length = base_width;
		for (String word: help_msg.getHelp().split("\\s")) {
			
			// word wrap
			length += word.length();
			if (length > this.terminal.getTermWidth()) {
				this.terminal.out().println();
				// additional padding
				this.terminal.out().print(command_padding + padding + "   ");
				length = base_width + word.length();
			}
			this.terminal.out().print(word + " ");
		}
		this.terminal.out().println();
	}
	
	public void disableConfirmExit(boolean value) {
		this.no_exit_prompt = value;
	}
	
	/**
	 * Prompts the user to confirm quit. Returns a 0 if quit was a confirm, else returns
	 * a 1.
	 * 
	 * @param args The arguments passed to this command
	 * @return 1 is the command was successful, a number > 1 for an error and 0 for a non-error quit. 
	 */
	@ca.mitmaro.commandline.command.annotation.Command(name="exit")
	@ca.mitmaro.commandline.command.annotation.Help(usage="exit", help="Exit the application.")
	public int exitCommand(String[] args) {
		
		try {
			if (this.no_exit_prompt || this.exit_prompt.waitForResponse()) {
				return 0; // 0 = good shutdown
			}
		} catch (IOException e) {
			this.terminal.out().println("An error occured. Aborting quit.");
		}
		return 1;
	}
	

}
