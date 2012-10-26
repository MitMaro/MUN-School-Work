/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.io.FileUtil;
import ca.mitmaro.ldb.Application;
import ca.mitmaro.ldb.CommandLineInterface;

/**
 * Main entry class
 *
 * @author Tim Oram (MitMaro)
 */
public class LDB {
	
	public static void main (String[] args) throws Throwable {
		
		boolean gui = false;
		
		FileUtil file_util = new FileUtil();
		Terminal term = new Terminal(
			new BufferedReader(new InputStreamReader(System.in)),
			new PrintWriter(new OutputStreamWriter(System.out)),
			new PrintWriter(new OutputStreamWriter(System.err))
		);
		Application application = new Application(file_util, term);
		CommandLineInterface cli = new CommandLineInterface(application, term, file_util);
		application.setInterface(cli);
		
		// parse args from command line
		for (String arg: args) {
			if (arg.equals("--gui") || arg.equals("-gui") || arg.equals("gui")) {
				gui = true;
			}
		}
		
		if (gui) {
			// gui mode
			cli.runCommand("guionly");
		} else {
			// cli mode
			System.exit(cli.run());
		}
	}
}
