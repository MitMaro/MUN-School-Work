/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #1                       //
//  Program File Name: IMDB.java                               //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.imdb;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import ca.mitmaro.imdb.entity.Movie;

public class Application {
	
	private LinkedHashMap<String, Movie> movies = new LinkedHashMap<String, Movie>();
	private LinkedHashMap<String, LinkedHashMap<String, Movie>> movie_lists = new LinkedHashMap<String, LinkedHashMap<String, Movie>>();
	
	public void loadRecommendationsFile(File file, String name) throws IOException {
		
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// should never reach here, but it is possible
			System.err.println("Provided file not found: " + file.getPath());
			System.exit(3);
		}
		
		String line;
		String[] tmp;
		String movie_key;
		String title;
		int year;
		double recommendation;
		
		// create a movie list if one does not exist
		LinkedHashMap<String, Movie> movie_list = this.movie_lists.get(name);
		if (movie_list == null) {
			movie_list = new LinkedHashMap<String, Movie>();
			this.movie_lists.put(name, movie_list);
		}
		
		while ((line = in.readLine()) != null) {
			
			// spit paper and references
			tmp = line.split("#", 3);
			
			movie_key = tmp[0].trim() + tmp[1].trim();
			title = tmp[0].trim();
			try {
				year = Integer.parseInt(tmp[1]);
				recommendation = Double.parseDouble(tmp[2]);
			} catch (NumberFormatException e) {
				// pass
				System.err.println("Skipping invalid recommendation");
				continue;
			}
			
			movie_list.put(movie_key, new Movie(title, year, recommendation));
			
		}
	}
	
	public void mergeRecommendationList(String list_name) {
		for (Map.Entry<String, Movie> movie: this.movie_lists.get(list_name).entrySet()) {
			this.movies.put(movie.getKey(), movie.getValue());
		}
		
	}
	
	public ArrayList<String> getRecommendationListNames() {
		return this.getRecommendationListNames(false);
	}
	
	public ArrayList<Movie> getMovies() {
		return new ArrayList<Movie>(this.movies.values());
	}
	
	public ArrayList<Movie> getMovies(String list_name) {
		return new ArrayList<Movie>(this.movie_lists.get(list_name).values());
	}
	
	// by master list
	public ArrayList<Movie> getSortedMovies() {
		return this.getSortedMovies(this.movies);
	}
	// by list name
	public ArrayList<Movie> getSortedMovies(String list_name) {
		return this.getSortedMovies(this.movie_lists.get(list_name));
	}
	
	public ArrayList<Movie> getSortedMovies(LinkedHashMap<String, Movie> list) {
		
		ArrayList<Movie> names = new ArrayList<Movie>(list.values());
		
		Collections.sort(names);
		
		return names;
	}
	
	
	
	// by master list
	public Movie getMovieRecommendationFromList(String title, int year) {
		return this.getMovieRecommendationFromList(this.movies, title, year);
	}
	// by list name
	public Movie getMovieRecommendationFromList(String list_name, String title, int year) {
		return this.getMovieRecommendationFromList(this.movie_lists.get(list_name), title, year);
	}
	
	public Movie getMovieRecommendationFromList(LinkedHashMap<String, Movie> list, String title, int year) {
		return list.get(String.format("%s%d", title, year));
	}
	
	
	public String getFormattedMovieRecommendation(Movie movie) {
		return String.format("%s (%d) - %2.1f", movie.getTitle(), movie.getYear(), movie.getRecommendation());
	}
	
	public ArrayList<String> getRecommendationListNames(boolean include_master) {
		
		ArrayList<String> r = new ArrayList<String>(this.movie_lists.keySet());
		
		if (include_master) {
			r.add(0, "master");
		}
		
		return r;
	}
	
}
