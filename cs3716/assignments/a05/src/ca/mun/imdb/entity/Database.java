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

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.io.Serializable;

public class Database implements Serializable,
		Iterable<MovieRecommendation> {

	private static final long serialVersionUID = -452816362609537636L;

	private LinkedList<MovieRecommendation> master;

	private String name;

	private DatabaseHistory history = new DatabaseHistory();

	public Database(String name) {
		this.name = name;
		this.master = new LinkedList<MovieRecommendation>();
	}
	
	public Database(Database db) {
		this.name = db.name;
		this.master = new LinkedList<MovieRecommendation>(db.master);
	}

	public void merge(List recommendation_list) {
		for (MovieRecommendation rec : recommendation_list) {
			if (this.master.contains(rec)) {
				// update movie recommendation
				this.master.get(this.master.indexOf(rec)).setRecommendation(
						rec.getRecommendation());
			} else {
				// not found so add the recommendation
				this.master.add(rec);
			}
		}
	}

	public int getSize() {
		return this.master.size();
	}

	public String getName() {
		return this.name;
	}


	public MovieRecommendation getMovie(int pos) {
		return this.master.get(pos);
	}

	public void sort() {
		Collections.sort(this.master);
	}
	
	@Override
	public Iterator<MovieRecommendation> iterator() {
		return this.master.iterator();
	}

	public void addMovieRecommendation(MovieRecommendation recommendation) {
		this.master.add(recommendation);
	}

	public DatabaseHistory getHistory() {
		return this.history;
	}

}
