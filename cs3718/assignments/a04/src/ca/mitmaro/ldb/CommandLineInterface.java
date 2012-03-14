/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb;

import java.io.IOException;

import ca.mitmaro.commandline.CommandLine;
import ca.mitmaro.commandline.command.DefaultCommands;
import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.io.FileUtil;

public class CommandLineInterface extends CommandLine {
	
	public class StatusCode extends CommandLine.StatusCode {
		public static final int MISSING_PAPER = 1 | NON_FATAL | USER;
	}
	
	protected final Application application;
	
	public CommandLineInterface(Application application, Terminal terminal, FileUtil file_util) {
		super(terminal);
		this.application = application;
		
		// add the default command line commands
		DefaultCommands dfc = new DefaultCommands(this.help, this.terminal);
		dfc.disableConfirmExit(true); // disable confirm on exit
		this.addCommandClass(dfc);
		this.addCommandClass(new Commands(this.application, this.terminal, file_util));
	}
	
	public int mainloop() throws Throwable {
		String input;
		
		System.out.print("$ ");
		input = this.terminal.in().readLine();
		
		if (input != null) {
			input = input.trim();
		} else {
			return StatusCode.OK;
		}
		
		return this.runCommand(input);
	}
	
	public int runCommand(String input) throws Throwable {

		String[] tmp;
		
		// parse out command and argument string
		tmp = input.split("\\s+", 2);
		
		if (tmp[0].equals("")) {
			return StatusCode.OK;
		}
		
		if (!this.hasCommand(tmp[0])) {
			System.err.println("Error: Invalid Operation Provided");
			return StatusCode.INVALID_OPERATION;
		}
		try {
			if (tmp.length > 1) {
				return super.runCommand(tmp[0], tmp[1].split("\\s+"));
			} else {
				return super.runCommand(tmp[0], null);
			}
		} catch (IOException e) {
			this.runCommand("help", null);
			return StatusCode.IO_ERROR; // IO error
		}
	}
	
}