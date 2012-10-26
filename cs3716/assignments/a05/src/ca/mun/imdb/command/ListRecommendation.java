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

public class ListRecommendation extends Command {
	
	private Application app;
	
	public ListRecommendation(Application application) {
		this.app = application;
	}

	public void call(String[] s) {
		for (String name: this.app.getRecommendationListNames()) {
			System.out.println(name);
		}
	}
}