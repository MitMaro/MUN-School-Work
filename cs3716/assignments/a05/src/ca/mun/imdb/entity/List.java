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

public class List implements Serializable,
		Iterable<MovieRecommendation> {
	private static final long serialVersionUID = -1616793039748595303L;

	private String listname;

	private LinkedList<MovieRecommendation> master = new LinkedList<MovieRecommendation>();

	private boolean verified = false;

	private ListHistory history = new ListHistory();

	public List(String Listname) {
		this.listname = Listname;
	}

	public List(List list) {
		this.master = new LinkedList<MovieRecommendation>(list.master);
	}

	public void addMovie(MovieRecommendation m) {
		if (!this.master.contains(m)) {
			this.master.add(m);
		}
	}

	public MovieRecommendation getMovieRecommendation(String title, int year) {
		for (MovieRecommendation rec : this.master) {
			if (rec.getTitle().equals(title) && rec.getYear() == year) {
				return rec;
			}
		}
		return null;
	}

	public void delMovie(int pos) {
		this.master.remove(pos);
	}

	public String getListName() {
		return this.listname;
	}

	public void setListName(String l) {
		this.listname = l;
	}

	public MovieRecommendation getItem(int pos) {
		return this.master.get(pos);
	}

	public int getSize() {
		return this.master.size();
	}

	public void setVerified() {
		this.verified = true;
	}

	public boolean isVerified() {
		return this.verified;
	}

	public ListHistory getHistory() {
		return this.history;
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

}
