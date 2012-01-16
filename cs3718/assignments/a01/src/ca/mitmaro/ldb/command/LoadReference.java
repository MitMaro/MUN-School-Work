/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.command;

import java.util.Arrays;
import java.io.File;
import java.io.IOException;

import ca.mitmaro.ldb.lang.StringUtils;
import ca.mitmaro.ldb.Application;

public class LoadReference extends BaseCommand {
	
	public LoadReference(Application application, String usage, String help) {
		super(application, usage, help);
	}
	
	public int call(String[] args) {
		if (args == null || args.length < 3) {
			throw new IllegalArgumentException("Missing parameters.");
		}
		
		if (args.length > 3) {
			args[2] = StringUtils.join(Arrays.copyOfRange(args, 2, args.length));
		}
		
		if (!args[1].equals("as")) {
			throw new IllegalArgumentException("Invalid parameter: " + args[1]);
		}
		
		String filepath = args[0];
		
		File f = new File(filepath);
		
		if (!f.isFile() || !f.canRead()) {
			throw new IllegalArgumentException("Invalid filepath provided: " + filepath);
		}
		
		String name = args[2];
		
		try {
			this.application.loadPaperReferenceListFile(filepath, name);
		} catch (IOException e) {
			System.out.println("IOError Error " + e.getMessage());
			return 4;
		}
		
		return 1;
	}
	
}
