/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////

import ca.mitmaro.ldb.EntityManager;
import ca.mitmaro.ldb.Application;
import ca.mitmaro.ldb.CommandLineInterface;

public class LDB {
	
	public static void main (String[] args) {
		
		EntityManager em = new EntityManager();
		Application application = new Application(em);
		CommandLineInterface cli = new CommandLineInterface(application);
		application.setInterface(cli);
		
		// application entry point
		System.exit(cli.run());
		
	}
	
}
