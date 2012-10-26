/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.ldb.Application;
import ca.mitmaro.ldb.CommandLineInterface;

public class LDB {
	
	public static void main (String[] args) throws Throwable {
		
		Application application = new Application();
		Terminal term = new Terminal();
		CommandLineInterface cli = new CommandLineInterface(application, term);
		application.setInterface(cli);
		
		// application entry point
		System.exit(cli.run());
		
	}
	
}
