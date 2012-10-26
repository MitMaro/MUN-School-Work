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

import java.util.InputMismatchException;
import java.util.Scanner;

import ca.mun.imdb.Application;
import ca.mun.imdb.IMDBException;
import ca.mun.imdb.InvalidArgumentException;
import ca.mun.imdb.entity.MovieRecommendation;
import ca.mun.imdb.entity.List;

public class EditRecommendation extends Command {
	
	private Application app;
	
	public EditRecommendation(Application application) {
		this.app = application;
	}

	public void call(String[] args) throws IMDBException, InputMismatchException {
		
		if (args.length != 1) {
			throw new InvalidArgumentException("Invalid number of arguments provided");
		}
		
		String listname = args[0];
		
		Scanner in = new Scanner(System.in);
		
		System.out.print("Enter movie title to edit: ");
		
		String title = in.nextLine();
		int year;
		
		List movies = this.app.searchRecommendationListByTitle(listname, title);
		
		MovieRecommendation rec;
		
		if (movies.getSize() == 1) {
			rec = movies.getItem(0);
		} else if (movies.getSize() == 0) {
			System.out.println("No movies match your search");
			return;
		} else {
			System.out.print("Enter movie year: ");
			year = in.nextInt();
			
			movies = this.app.searchRecommendationList(listname, title, year);
			
			if (movies.getSize() == 0) {
				System.out.println("No movies match your search");
				return;
			}
			rec = movies.getItem(0);
		}
		in.nextLine();
		System.out.print("Enter new movie title: ");
		title = in.nextLine();
		
		System.out.print("Enter new movie year: ");
		year = in.nextInt();
		
		this.app.editMovieRecommendation(listname, rec.getTitle(), rec.getYear(), title, year);
	}

}