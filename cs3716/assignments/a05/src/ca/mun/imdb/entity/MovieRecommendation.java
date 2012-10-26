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

public class MovieRecommendation implements Serializable,
		Comparable<MovieRecommendation> {

	private static final long serialVersionUID = 7537500579098569881L;
	private double recommendation;
	private User recommender;
	private Movie movie;

	public MovieRecommendation(String title, int year, String genre,
			User recommender, double recommendation) {
		this.movie = new Movie(title, year, genre);
		this.recommendation = recommendation;
		this.recommender = recommender;
	}

	public User getRecommender() {
		return this.recommender;
	}

	public void setRecommender(User recommender) {
		this.recommender = recommender;
	}

	public Double getRecommendation() {
		return this.recommendation;
	}

	public void setRecommendation(double recommendation) {
		this.recommendation = recommendation;
	}

	public Movie getMovie() {
		return this.movie;
	}

	public String getTitle() {
		return this.movie.getTitle();
	}

	public int getYear() {
		return this.movie.getYear();
	}

	public String getGenre() {
		return this.movie.getGenre();
	}

	@Override
	public int compareTo(MovieRecommendation o) {

		int c = this.movie.compareTo(o.movie);

		// same movie, compare by recommendation user
		if (c == 0) {
			c = this.recommender.getUserName().compareTo(
					o.recommender.getUserName());
		}
		return c;
	}
	
	@Override
	public String toString() {
		return String.format(
			"%s [%d] (%s) - %.2f (%s)",
			this.movie.getTitle(),
			this.movie.getYear(),
			this.movie.getGenre(),
			this.recommendation,
			this.recommender.getUserName()
		);
	}
	
	@Override
	public boolean equals(Object e) {
		if (e instanceof MovieRecommendation) {
			MovieRecommendation rec = (MovieRecommendation)e;
			return rec.getMovie().equals(this.getMovie()) && this.recommender.equals(rec.recommender);
		}
		return false;
	}
	
}
