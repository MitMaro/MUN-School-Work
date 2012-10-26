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
import ca.mun.imdb.entity.List;

public class SearchRecommendation extends Command {
	
	private Application app;
	
	public SearchRecommendation(Application application) {
		this.app = application;
	}
	
	public void call(String[] args) throws IMDBException {
		List list;
		
		// genre search
		if (args.length == 4) {
			String listname = args[0];
			String genre = args[3];
			list = this.app.searchRecommendationList(listname, genre);
		} else if (args.length == 5) { // title/year search
			String listname = args[0];
			String title = args[3];
			
			int year;
			try {
				year = Integer.parseInt(args[4]);
			} catch (NumberFormatException e) {
				throw new InvalidArgumentException("Invalid year provided");
			}
			
			list = this.app.searchRecommendationList(listname, title, year);
		} else {
			throw new InvalidArgumentException("Invalid number of arguments provided");
		}
		
		if (list.getSize() == 0) {
			System.out.println("Search returned no results");
			return;
		}
		
		for (MovieRecommendation rec: list) {
			System.out.println(rec.toString());
		}
	}
}