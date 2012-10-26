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
import ca.mun.imdb.entity.ListHistory;

public class ViewHistoryRecommendation extends Command {
	
	private Application app;
	
	public ViewHistoryRecommendation(Application application) {
		this.app = application;
	}
	
	public void call(String[] args) throws IMDBException {
		
		if (args.length != 1) {
			throw new InvalidArgumentException("Invalid number of arguments provided");
		}
		
		for (ListHistory.HistoryEntry entry: this.app.getRecommendationListHistory(args[0])) {
			System.out.println(entry.toString());
		}
	}
}