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

public class DisplayRecommendation extends Command {
	
	private Application app;
	
	public DisplayRecommendation(Application application) {
		this.app = application;
	}
	
	public void call(String[] args) throws IMDBException {
		
		if (args.length != 1 && args.length != 2) {
			throw new InvalidArgumentException("Invalid number of arguments provided");
		}
		
		List list;
		String listname;
		if (args[0].equals("sorted")) {
			listname = args[1];
			list = this.app.getMovieRecommendations(listname, true);
		} else {
			listname = args[0];
			list = this.app.getMovieRecommendations(listname, false);
		}
		
		
		System.out.println(String.format("List: %s", listname));
		
		if (list.getSize() == 0) {
			System.out.println("-- List is Empty --");
			return;
		}
		
		for (MovieRecommendation rec: list) {
			System.out.println(rec.toString());
		}
	}
}