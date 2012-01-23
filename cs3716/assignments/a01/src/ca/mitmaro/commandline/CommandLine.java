/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #1                       //
//  Program File Name: IMDB.java                               //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline;

import java.util.HashMap;
import java.util.Set;

import ca.mitmaro.commandline.command.Command;
import ca.mitmaro.commandline.command.Help;
import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.commandline.userinterface.TextBased;
import ca.mitmaro.commandline.help.System;
import ca.mitmaro.commandline.help.Message;

public abstract class CommandLine {
	
	protected final System help = new System();
	
	private final HashMap<String, Command> commands = new HashMap<String, Command>();
	
	protected final Terminal terminal;
	
	public CommandLine(Terminal terminal) {
		this.terminal = terminal;
	}
	
	public CommandLine addCommand(String name, Command command) {
		this.commands.put(name, command);
		return this;
	}
	
	public CommandLine addCommand(String name, Command command, Message help_msg) {
		return this
			.addCommand(name, command)
			.addHelpMessage(name, help_msg)
		;
 	}
	
	public CommandLine addHelpMessage(String name, Message help_msg) {
		this.help.addMessage(name, help_msg);
		return this;
	}
	
	public Set<String> getCommandNames() {
		return this.commands.keySet();
	}
	
	public Command getCommand(String name) {
		return this.commands.get(name);
	}
	
	public int runCommand(String command) {
		return this.commands.get(command).call(null);
	}
	
	public abstract int mainloop();
	
}

