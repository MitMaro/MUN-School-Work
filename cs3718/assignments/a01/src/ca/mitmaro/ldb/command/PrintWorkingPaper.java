/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.command;

import ca.mitmaro.ldb.Application;

public class PrintWorkingPaper extends BaseCommand {
	
	public PrintWorkingPaper(Application application, String usage, String help) {
		super(application, usage, help);
	}
	
	public int call(String[] args) {
		if (args != null && args.length != 0) {
			throw new IllegalArgumentException("Invalid Parameter(s).");
		}
		
		this.application.printCurrentWorkingPaper();
		
		return 1;
	}
	
}
