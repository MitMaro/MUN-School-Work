/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.command;

import ca.mitmaro.ldb.Application;

public class Exit extends BaseCommand {
	
	public Exit(Application application, String usage, String help) {
		super(application, usage, help);
	}
	
	public int call(String[] args) {
		return 0;
	}
	
}
