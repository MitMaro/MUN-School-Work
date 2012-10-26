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
import ca.mun.imdb.entity.Movie;
import ca.mun.imdb.entity.PersonalList;

public class DisplayPersonalList extends Command {
	
	private Application app;
	
	public DisplayPersonalList(Application application) {
		this.app = application;
	}
	
	public void call(String[] args) throws IMDBException {
		
		System.out.println("Personal List");
		
		PersonalList list = this.app.getPersonalList();
		
		if (list.getSize() == 0) {
			System.out.println("-- List is Empty --");
			return;
		}
		
		for (Movie movie: list) {
			System.out.println(movie.toString());
		}
	}
}