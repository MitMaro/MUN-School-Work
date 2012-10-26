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

import java.util.Scanner;

import ca.mun.imdb.Application;
import ca.mun.imdb.IMDBException;
import ca.mun.imdb.InvalidArgumentException;
import ca.mun.imdb.entity.MovieRecommendation;
import ca.mun.imdb.entity.Database;

public class SearchDatabase extends Command {
	
	private Application app;
	
	// show add to personal prompt or not
	private boolean personal_prompt;
	
	public SearchDatabase(Application application, boolean personal_prompt) {
		this.app = application;
		this.personal_prompt = personal_prompt;
	}
	
	public void call(String[] args) throws IMDBException {
		
		Database db;
		
		// genre search
		if (args.length == 4) {
			String dbname = args[0];
			String genre = args[3];
			db = this.app.searchDatabase(dbname, genre);
		} else if (args.length == 5) { // title/year search
			String dbname = args[0];
			String title = args[3];
			
			int year;
			try {
				year = Integer.parseInt(args[4]);
			} catch (NumberFormatException e) {
				throw new InvalidArgumentException("Invalid year provided");
			}
			
			db = this.app.searchDatabase(dbname, title, year);
		} else {
			throw new InvalidArgumentException("Invalid number of arguments provided");
		}
		
		if (db.getSize() == 0) {
			System.out.println("Search returned no results");
			return;
		}
		
		for (MovieRecommendation rec: db) {
			System.out.println(rec.toString());
		}
		
		// if personal prompt is enabled (for browsers only)
		if (this.personal_prompt) {
			Scanner in = new Scanner(System.in);
			
			System.out.print("Add search results to personal list? (Yes|No)");
			
			if (in.nextLine().toLowerCase().equals("yes")) {
				this.app.addToPersonal(db);
			}
		}
	}
}