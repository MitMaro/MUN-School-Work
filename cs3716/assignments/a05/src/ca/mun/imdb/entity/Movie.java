/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #5                       //
//  Program File Name: IMDB.java                               //
//         Login Name: oram                                    //
//       Student Name: Oram, Timothy A.                        //
//       Student Name: Alfosool Saheb, Ali Mohammad            //
//       Student Name: Chen, Shike                             //
//       Student Name: Zakzouk, Omar S.                        //
/////////////////////////////////////////////////////////////////
package ca.mun.imdb.entity;
import java.io.Serializable;

public class Movie implements Serializable, Comparable<Movie> {

	private static final long serialVersionUID = 5329973938472686565L;

	private String title;
	private int year;
	private String genre;

	// Constructor
	public Movie(String Title, int Year, String Genre) {
		this.title = Title;
		this.year = Year;
		this.genre = Genre;
	}

	public String getTitle() {
		return this.title;
	}

	public int getYear() {
		return this.year;
	}

	public String getGenre() {
		return this.genre;
	}

	public void setTitle(String newTitle) {
		this.title = newTitle;
	}

	public void setYear(int newYear) {
		this.year = newYear;
	}

	public void setGenre(String newGenre) {
		this.genre = newGenre;
	}

	@Override
	public int compareTo(Movie o) {
		// compare titles
		int c = this.getTitle().compareTo(o.getTitle());
		// titles are same compare year
		if (c == 0) {
			c = this.getYear() - o.getYear();
		}
		return c;
	}
	
	@Override
	public String toString() {
		return String.format(
			"%s [%d] (%s)",
			this.getTitle(),
			this.getYear(),
			this.getGenre()
		);
	}
	
	@Override
	public boolean equals(Object e) {
		if (e instanceof Movie) {
			Movie movie = (Movie)e;
			return movie.title.equals(movie.title) && movie.year == this.year;
		}
		return false;
	}
}
