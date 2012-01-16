/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.command;

import ca.mitmaro.ldb.Application;

public class AddPaper extends BaseCommand {
	
	public AddPaper (Application application, String command_name, String help) {
		super(application, command_name, help);
	}
	
	public int call(String[] args) {
		
		if (args.length != 1) {
			throw new IllegalArgumentException("Missing parameter.");
		}
		
		String name = args[0];
		
		this.application.addPapersToMasterList(name);
		
		return 1;
	}
	
}
