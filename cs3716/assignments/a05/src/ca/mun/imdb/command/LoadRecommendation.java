/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #5                       //
//  Program File Name: IMDB.java                               //
//         Login Name: oram                                    //
//       Student Name: Oram, Timothy A.                        //
//       Student Name: Alfosool Saheb, Ali Mohammad            //
//       Student Name: Chen, Shike                             //
//       Student Name: Zakzouk, Omar S.                        //
/////////////////////////////////////////////////////////////////

package ca.mun.imdb.command;

import java.io.File;
import java.io.IOException;
import ca.mun.imdb.Application;
import ca.mun.imdb.IMDBException;
import ca.mun.imdb.InvalidArgumentException;

public class LoadRecommendation extends Command{
	
	private Application app;
	
	public LoadRecommendation(Application application) {
		this.app = application;
	}
	
	public void call(String[] args) throws IMDBException {
		
		if (args.length != 3) {
			throw new InvalidArgumentException("Invalid number of arguments provided");
		}
		
		String filename = args[0];
		String listname = args[2];
		try {
			File f = new File(filename);
			
			if (f.canRead()) {
				this.app.loadRecommendationList(f, listname);
			} else {
				throw new InvalidArgumentException(String.format("The file, %s, could not be read.", filename));
			}
		} catch (IOException e) {
			System.err.println("Unknwon IO Error occured");
		}
	}
}