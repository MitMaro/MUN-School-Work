/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.command;

import ca.mitmaro.ldb.Application;

public class Help extends BaseCommand {
	
	public Help (Application application, String usage, String help) {
		super(application, usage, help);
	}
	
	public int call(String[] args) {
		
		if (args != null && args.length != 0) {
			throw new IllegalArgumentException("Invalid Parameter(s).");
		}
		
		System.out.println("Operation List\n");
		
		for (String command_name: this.application.getInterface().getCommandNames()) {
			this.application.getInterface().printCommandHelp(command_name, "\t");
		}
		return 1;
	}
	
}
