/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #1                       //
//  Program File Name: IMDB.java                               //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.imdb.entity;

public class Movie implements Comparable<Movie> {
	
	private String title;
	
	private int year;
	
	private double recommendation;
	
	public Movie(String title, int year, double recommendation) {
		this.title = title;
		this.year = year;
		this.recommendation = recommendation;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public double getRecommendation() {
		return this.recommendation;
	}
	
	public int compareTo(Movie movie) {
		
		int title_compare = this.title.compareToIgnoreCase(movie.getTitle());
		
		// title based comparison was enough
		if (title_compare != 0) {
			return title_compare;
		}
		
		// if titles are equal than compare by year
		return this.year - movie.getYear();
	}
}
