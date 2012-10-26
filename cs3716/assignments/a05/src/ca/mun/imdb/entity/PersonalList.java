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

public class PersonalList implements Serializable, Iterable<Movie> {
	private static final long serialVersionUID = -8910874784151100324L;

	private LinkedList<Movie> master = new LinkedList<Movie>();

	public void add(Movie movie) {
		if (!this.master.contains(movie)) {
			master.add(movie);
		}
	}

	public int getSize() {
		return this.master.size();
	}

	@Override
	public Iterator<Movie> iterator() {
		return this.master.iterator();
	}

	public void sort() {
		Collections.sort(this.master);
	}
}
