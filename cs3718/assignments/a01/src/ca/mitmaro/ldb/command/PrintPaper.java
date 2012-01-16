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

public class PrintPaper extends BaseCommand {
	
	public PrintPaper(Application application, ArrayList<BaseCommand.HelpMessage> msgs) {
		super(application, msgs);
	}
	
	public int call(String[] args) {
		
		if (args == null || args.length == 0) {
			this.application.printMasterPaperList();
		} else if (args.length == 1) {
			this.application.printPaperList(args[0]);
		}
		return 1;
	}
	
}
