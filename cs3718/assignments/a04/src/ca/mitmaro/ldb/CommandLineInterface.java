/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
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

/**
 * The command line interface application
 *
 * @author Tim Oram (MitMaro)
 */
public class CommandLineInterface extends CommandLine {
	
	/**
	 * Appliation status codes
	 */
	public class StatusCode extends CommandLine.StatusCode {
		/**
		 * A missing paper status code
		 */
		public static final int MISSING_PAPER = 32 | NON_FATAL | USER;
		/**
		 * A missing list status code
		 */
		public static final int MISSING_LIST = 64 | NON_FATAL | USER;
	}
	
	/**
	 * An application instance
	 */
	protected final Application application;
	
	/**
	 * Construct an instance of the command line interface
	 * 
	 * @param application The application instance
	 * @param terminal A terminal interface
	 * @param file_util The file util interface
	 */
	public CommandLineInterface(Application application, Terminal terminal, FileUtil file_util) {
		super(terminal);
		this.application = application;
		
		// add the default command line commands
		DefaultCommands dfc = new DefaultCommands(this.help, this.terminal);
		this.addCommandClass(dfc);
		this.addCommandClass(new Commands(this.application, this.terminal, file_util));
	}
	
	@Override
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
	
	/**
	 * Get and run a command from the interactive command line
	 * 
	 * @param input The input from the command line
	 * @return A status code
	 * @throws Throwable Any occuring exception or error
	 */
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