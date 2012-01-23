/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Set;

import ca.mitmaro.ldb.command.BaseCommand;
import ca.mitmaro.ldb.command.BaseCommand.HelpMessage;
import ca.mitmaro.ldb.command.Help;
import ca.mitmaro.ldb.command.LoadPaper;
import ca.mitmaro.ldb.command.LoadReference;
import ca.mitmaro.ldb.command.ListPaper;
import ca.mitmaro.ldb.command.ListReference;
import ca.mitmaro.ldb.command.AddPaper;
import ca.mitmaro.ldb.command.AddReference;
import ca.mitmaro.ldb.command.PrintPaper;
import ca.mitmaro.ldb.command.PrintReference;
import ca.mitmaro.ldb.command.PrintWorkingPaper;
import ca.mitmaro.ldb.command.SetWorkingPaper;
import ca.mitmaro.ldb.command.Exit;

public class CommandLineInterface {
	
	private int term_width = 70;
	
	private int max_command_name_length = 0;
	
	private LinkedHashMap<String, BaseCommand> commands;
	
	private BufferedReader stdin;
	
	protected final Application application;
	
	public CommandLineInterface(Application application) {
		
		this.application = application;
		
		this.commands = new LinkedHashMap<String, BaseCommand>();
		this.stdin = new BufferedReader(new InputStreamReader(System.in));
		
		ArrayList<HelpMessage> printlist_help = new ArrayList<HelpMessage>();
		printlist_help.add(new HelpMessage("printpap", "Print the master paper description list."));
		printlist_help.add(new HelpMessage("printpap NAME", "Print the paper description list stored by name NAME."));
		
		ArrayList<HelpMessage> printref_help = new ArrayList<HelpMessage>();
		printlist_help.add(new HelpMessage("printref", "Print the master paper reference list."));
		printlist_help.add(new HelpMessage("printref NAME", "Print the paper reference list stored by name NAME."));
		
		ArrayList<HelpMessage> setcwp_help = new ArrayList<HelpMessage>();
		setcwp_help.add(
			new HelpMessage(
				"setcwp PID",
				"Set current working paper to paper in master paper description list with paper ID PID."
			)
		);
		setcwp_help.add(
			new HelpMessage("setcwp RX", "Set current working paper to reference X (1-indexed) of the current working paper.")
		);
		
		this
			.addCommand("help", new Help(this.application, "help", "Print a list of all system operations."))
			.addCommand("loadpap", new LoadPaper(this.application, "loadpap FILE as NAME", "Load a paper description " +
			            "list stored in file FILE into the system and store under name NAME.")
			)
			.addCommand("loadref", new LoadReference(this.application, "loadref FILE as NAME", "Load a paper reference " +
			            "list stored in file FILE into the system and store under name NAME.")
			)
			.addCommand("listpap", new ListPaper(this.application, "listpap", "List all paper description lists " +
			            "stored in the system.")
			)
			.addCommand("listref", new ListReference(this.application, "listref", "List all paper reference lists " +
			            "stored in the system.")
			)
			.addCommand("addpap", new AddPaper(this.application, "addpap NAME", "Add the paper description list " +
			            "stored by name NAME to the master paper description list.")
			)
			.addCommand("addref", new AddReference(this.application, "addref NAME", "Add the paper reference list " +
			            "stored by name NAME to the master paper reference list.")
			)
			.addCommand("printpap", new PrintPaper(this.application, printlist_help)
			)
			.addCommand("printref", new PrintReference(this.application, printref_help))
			.addCommand("setcwp", new SetWorkingPaper(this.application, setcwp_help))
			.addCommand("printcwp", new PrintWorkingPaper(this.application, "printcwp", "Print current working paper " +
			            "and its references."))
			.addCommand("exit", new Exit(this.application, "exit", "Exit the system."))
		;
		
	}
	
	public CommandLineInterface addCommand(String name, BaseCommand command) {
		
		// record command name length for later use in printing help messages
		for (BaseCommand.HelpMessage help_msg: command.getHelpMessages()) {
			if (this.max_command_name_length < help_msg.getUsage().length()) {
				this.max_command_name_length = help_msg.getUsage().length();
			}
		}
		
		this.commands.put(name, command);
		return this;
	}
	
	public Set<String> getCommandNames() {
		return this.commands.keySet();
	}
	
	public BaseCommand getCommand(String name) {
		return this.commands.get(name);
	}
	
	public int run() {
		
		String input;
		String[] tmp;
		BaseCommand cmd;
		int code;
	
		while (true) {
			System.out.print("$ ");
			
			try {
				input = this.stdin.readLine();
				
				if (input != null) {
					input = input.trim();
				} else {
					continue;
				}
				
			}
			catch (IOException e) {
				System.out.println(e.getMessage());
				return 2; // IO error
			}
			
			// parse out command and argument string
			tmp = input.split("\\s+", 2);
			
			if (tmp[0].equals("")) {
				continue;
			}
			
			if (this.commands.containsKey(tmp[0])) {
				cmd = this.commands.get(tmp[0]);
			} else {
				System.out.println("Error: Invalid Operation Provided");
				continue;
			}
			
			// make call to command
			try { 
				if (tmp.length > 1) {
					if ((code = cmd.call(tmp[1].split("\\s+"))) != 1) {
						return code;
					}
				} else {
					// break out if a command returns somthing other than 1, other numbers can be treated as error codes
					// Special Codes: 1 - no error, 0 - proper shutdown
					if ((code = cmd.call(null)) != 1) {
						return code;
					}
				}
			} catch (IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage() + "\n");
				this.printCommandHelp(cmd);
				continue;
			} catch (Exception e) {
				// because i don't want to start over with testing if i make a typo
				System.out.println("Opps, something went wrong...");
				e.printStackTrace();
				continue;
			}
		}
		
	}
	
	public void printCommandHelp(String command_name) {
		this.printCommandHelp(command_name, "");
	}
	
	public void printCommandHelp(String command_name, String extra_padding) {
		if (this.commands.containsKey(command_name)) {
			this.printCommandHelp(this.getCommand(command_name), extra_padding);
		}
	}
	
	public void printCommandHelp(BaseCommand command) {
		this.printCommandHelp(command, "");
	}
	
	public void printCommandHelp(BaseCommand command, String extra_padding) {
		
		int length;
		
		// padding... i miss python and str.repeat. might be other ways to do this but this works
		String padding = String.format(String.format("%%0%dd", this.max_command_name_length), 0).replace('0',' ');
		
		for (BaseCommand.HelpMessage help_msg: command.getHelpMessages()) {
			
			length = this.max_command_name_length - help_msg.getUsage().length() + 3;
			
			// print usage
			System.out.print(extra_padding + help_msg.getUsage());
			
			// command/column alignment
			System.out.print(String.format(String.format("%%0%dd", length), 0).replace('0',' '));
			
			// print help message with word wrap
			length = 0;
			for (String word: help_msg.getHelp().split("\\s")) {
				
				// word wrap
				length += word.length();
				if (length > this.term_width) {
					System.out.println();
					// additional padding
					System.out.print(extra_padding);
					System.out.print(padding + "   ");
					length = word.length();
				}
				System.out.print(word + " ");
			}
			System.out.println();
		}
	}
	

}
