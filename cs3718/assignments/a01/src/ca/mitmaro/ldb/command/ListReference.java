/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.command;

import ca.mitmaro.ldb.Application;

public class ListReference extends BaseCommand {
	
	public ListReference(Application application, String usage, String help) {
		super(application, usage, help);
	}
	
	public int call(String[] args) {
		
		if (args != null && args.length != 0) {
			throw new IllegalArgumentException("Invalid parameter(s).");
		}
		
		this.application.printPaperReferenceLists();
		
		return 1;
	}
	
}
