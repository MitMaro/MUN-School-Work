/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.command;

import java.util.ArrayList;

import ca.mitmaro.ldb.Application;

public class SetWorkingPaper extends BaseCommand {
	
	public SetWorkingPaper(Application application, ArrayList<BaseCommand.HelpMessage> msgs) {
		super(application, msgs);
	}
	
	public int call(String[] args) {
		
		if (args.length != 1) {
			throw new IllegalArgumentException("Missing parameter.");
		}
		
		String id = args[0];
		
		this.application.setCurrentWorkingPaper(id);
		
		return 1;
	}
	
}
