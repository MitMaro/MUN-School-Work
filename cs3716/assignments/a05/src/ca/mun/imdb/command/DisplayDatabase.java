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

import ca.mun.imdb.Application;
import ca.mun.imdb.IMDBException;
import ca.mun.imdb.InvalidArgumentException;
import ca.mun.imdb.entity.MovieRecommendation;
import ca.mun.imdb.entity.Database;

public class DisplayDatabase extends Command {
	
	private Application app;
	
	public DisplayDatabase(Application application) {
		this.app = application;
	}
	
	public void call(String[] args) throws IMDBException {
		
		if (args.length != 1 && args.length != 2) {
			throw new InvalidArgumentException("Invalid number of arguments provided");
		}
		
		Database db;
		String dbname;
		if (args[0].equals("sorted")) {
			dbname = args[1];
			db = this.app.getDatabase(dbname, true);
		} else {
			dbname = args[0];
			db = this.app.getDatabase(dbname, false);
		}
		
		
		System.out.println(String.format("Database: %s", dbname));
		
		if (db.getSize() == 0) {
			System.out.println("-- Database is Empty --");
			return;
		}
		
		for (MovieRecommendation rec: db) {
			System.out.println(rec.toString());
		}
	}
}