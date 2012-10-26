/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #5                       //
//  Program File Name: IMDB.java                               //
//         Login Name: oram                                    //
//       Student Name: Oram, Timothy A.                        //
//       Student Name: Alfosool Saheb, Ali Mohammad            //
//       Student Name: Chen, Shike                             //
//       Student Name: Zakzouk, Omar S.                        //
/////////////////////////////////////////////////////////////////

package ca.mun.imdb;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import ca.mun.imdb.entity.DatabaseHistory;
import ca.mun.imdb.entity.MovieRecommendation;
import ca.mun.imdb.entity.PersonalList;
import ca.mun.imdb.entity.Database;
import ca.mun.imdb.entity.List;
import ca.mun.imdb.entity.ListHistory;
import ca.mun.imdb.entity.User;
import ca.mun.imdb.entity.UserList;

public class Application implements Serializable {

	private static final long serialVersionUID = -2190661308649296298L;

	private ArrayList<List> recommendation_lists;

	private ArrayList<Database> recommendation_databases;

	private UserList users_list;

	private transient User user;

	public Application() {
		this.recommendation_databases = new ArrayList<Database>();
		this.recommendation_lists = new ArrayList<List>();
		this.users_list = new UserList();
	}
	
	public boolean hadUsers() {
		return this.users_list.getSize() != 0;
	}
	
	/**
	 * Takes a file object and a list name. Loads the recommendations from the
	 * file and stores them under a list called list_name
	 * 
	 * @param file
	 *            The
	 * @param list_name
	 * @throws IOException
	 */
	public void loadRecommendationList(File file, String list_name)
			throws IOException, InvalidArgumentException {

		BufferedReader in = null;

		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// should never reach here, but it is possible
			System.err.println("Provided file not found: " + file.getPath());
			return;
		}

		String line;
		String[] tmp;
		String title;
		String genre;
		int year;
		double recommendation;

		List list;
		try {
			list = this.getRecommendationListByName(list_name);
		} catch (InvalidListExcpetion e) {
			list = new List(list_name);
			this.recommendation_lists.add(list);
			list.getHistory().addHistory(this.user,
					ListHistory.HistoryEntry.Action.CREATE);
		}

		// read the file
		while ((line = in.readLine()) != null) {

			// spit paper and references
			tmp = line.split("\\#", 4);
			
			if (tmp.length != 4) {
				throw new InvalidArgumentException("Provided Recommendation File is Invalid");
			}

			title = tmp[0].trim();
			genre = tmp[2].trim();
			try {
				year = Integer.parseInt(tmp[1]);
				recommendation = Double.parseDouble(tmp[3]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new InvalidArgumentException("Provided Recommendation File is Invalid.");
			}

			list.addMovie(new MovieRecommendation(title, year, genre,
					this.user, recommendation));

		}
	}

	/**
	 * Merge a recommendation list into a database
	 * 
	 * @param list_name
	 *            The recommendation list
	 * @param db_name
	 *            The database name
	 */
	public void mergeRecommendationList(String list_name, String db_name) throws InvalidListExcpetion {
		
		List list = this.getRecommendationListByName(list_name);
		
		Database database;
		try {
			database = this.getRecommendationDatabaseByName(db_name);
		}
		catch (InvalidDatabaseException e) {
			database = new Database(db_name);
			this.recommendation_databases.add(database);
			database.getHistory().addHistory(this.user, database.getName(),
					DatabaseHistory.HistoryEntry.Action.CREATE);
		}
		database.merge(list);
		database.getHistory().addHistory(this.user, database.getName(),
				DatabaseHistory.HistoryEntry.Action.MERGE);
	}

	/**
	 * Get the recommendation list names
	 * 
	 * @return Recommendation list names
	 */

	public ArrayList<String> getRecommendationListNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (List list : this.recommendation_lists) {
			names.add(list.getListName());
		}
		return names;
	}

	/**
	 * A list of database names
	 * 
	 * @return Database name list
	 */
	public ArrayList<String> getDatabaseNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (Database db : this.recommendation_databases) {
			names.add(db.getName());
		}
		return names;
	}

	/**
	 * Get a recommendation database
	 * 
	 * @param db_name
	 *            The database name
	 * @return A recommendation data
	 */
	public Database getDatabase(String name, boolean sorted) throws InvalidDatabaseException {
		Database db = this.getRecommendationDatabaseByName(name);
		
		if (sorted) {
			db = new Database(db);
			db.sort();
		}
		
		return db;
	}

	/**
	 * Edit a movie recommendation (name, genre and year) from a list name and a
	 * id
	 * 
	 * @param list_name
	 *            The list the movie belongs to
	 * @param old_title
	 *            The old title of the movie
	 * @param old_year
	 *            The old year of the movie
	 * @param new_title
	 *            The new title of the movie
	 * @param new_year
	 *            The new year of the movie
	 */
	public void editMovieRecommendation(String list_name, String old_title,
			int old_year, String new_title, int new_year) throws InvalidListExcpetion {
		List list = this.getRecommendationListByName(list_name);
		MovieRecommendation movie_rec = list.getMovieRecommendation(old_title,
				old_year);
		movie_rec.getMovie().setTitle(new_title);
		movie_rec.getMovie().setYear(new_year);
		list.getHistory().addHistory(this.user,
				ListHistory.HistoryEntry.Action.EDIT);
	}

	/**
	 * Set a recommendation list as being verified
	 * 
	 * @param list_name
	 *            The list name
	 */
	public void verifyReccomendationList(String list_name) throws InvalidListExcpetion {
		List list = this.getRecommendationListByName(list_name);
		list.setVerified();
		list.getHistory().addHistory(this.user,
				ListHistory.HistoryEntry.Action.VERIFY);
	}

	/**
	 * Get the action history of a database
	 * 
	 * @param db_name
	 *            The name of the database
	 * @return The database history
	 */
	public DatabaseHistory getDatabaseHistory(String db_name) throws InvalidDatabaseException {
		return this.getRecommendationDatabaseByName(db_name).getHistory();
	}

	/**
	 * Get the action history of a recommendation list
	 * 
	 * @param list_name
	 *            The name of the list
	 * @return The list history
	 */
	public ListHistory getRecommendationListHistory(String list_name) throws InvalidListExcpetion {
		return this.getRecommendationListByName(list_name).getHistory();
	}

	/**
	 * Get the personal list for the currently logged in user
	 * 
	 * @return The personal list
	 */
	public PersonalList getPersonalList() {
		PersonalList plist = this.user.getPersonalList();
		plist.sort();
		return plist;
	}

	public void addToPersonal(Database db) {
		PersonalList list = this.user.getPersonalList();
		for (MovieRecommendation rec: db) {
			list.add(rec.getMovie());
		}
	}
	
	/**
	 * Gets a list of movie recommendations from a list name
	 * 
	 * @param list_name
	 *            The name of the list
	 * @param sorted
	 *            True for a sorted list, otherwise false
	 * @return A recommendation list
	 */
	public List getMovieRecommendations(String list_name,
			boolean sorted) throws InvalidListExcpetion {
		List list = this.getRecommendationListByName(list_name);

		if (sorted) {
			list = new List(list);
			list.sort();
		}

		return list;

	}

	/**
	 * Search a database by genre
	 * 
	 * @param db_name
	 *            The database name
	 * @param genre
	 *            The genre
	 * @return A subset of the database filtered by genre based search
	 */
	public Database searchDatabase(String db_name, String genre) throws InvalidDatabaseException {
		Database db = new Database(db_name);

		for (MovieRecommendation movie_rec : this
				.getRecommendationDatabaseByName(db_name)) {
			if (movie_rec.getGenre().toLowerCase()
					.contains(genre.toLowerCase())) {
				db.addMovieRecommendation(movie_rec);
			}
		}
		return db;
	}

	/**
	 * Search a database by title and year
	 * 
	 * @param db_name
	 *            The database name
	 * @param title
	 *            The movie title
	 * @param year
	 *            The movie year
	 * @return A subset of the database filtered by title/year based search
	 */
	public Database searchDatabase(String db_name, String title,
			int year) throws InvalidDatabaseException {
		Database db = new Database(db_name);

		for (MovieRecommendation movie_rec : this
				.getRecommendationDatabaseByName(db_name)) {
			if (movie_rec.getTitle().toLowerCase().equals(title.toLowerCase())
					&& movie_rec.getYear() == year) {
				db.addMovieRecommendation(movie_rec);
			}
		}

		return db;
	}

	/**
	 * Search a recommendation list
	 * 
	 * @param list_name
	 *            The list name
	 * @param genre
	 *            The genre
	 * @return A subset of the list filtered by a genre based search
	 */
	public List searchRecommendationList(String list_name,
			String genre) throws InvalidListExcpetion {
		List list = new List(list_name);

		for (MovieRecommendation movie_rec : this
				.getRecommendationListByName(list_name)) {
			if (movie_rec.getGenre().toLowerCase()
					.contains(genre.toLowerCase())) {
				list.addMovieRecommendation(movie_rec);
			}
		}

		return list;
	}

	/**
	 * Search a recommendation list
	 * 
	 * @param list_name
	 *            The recommendation list name
	 * @param title
	 *            The title of the movie
	 * @param year
	 *            The year
	 * @return A subset of the list filtered by a title/year based search
	 */
	public List searchRecommendationListByTitle(String list_name, String title) throws InvalidListExcpetion {
		List list = new List(list_name);

		for (MovieRecommendation movie_rec : this
				.getRecommendationListByName(list_name)) {
			if (movie_rec.getTitle().toLowerCase().equals(title.toLowerCase())) {
				list.addMovieRecommendation(movie_rec);
			}
		}

		return list;
	}

	/**
	 * Search a recommendation list
	 * 
	 * @param list_name
	 *            The recommendation list name
	 * @param title
	 *            The title of the movie
	 * @param year
	 *            The year
	 * @return A subset of the list filtered by a title/year based search
	 */
	public List searchRecommendationList(String list_name,
			String title, int year) throws InvalidListExcpetion {
		List list = new List(list_name);

		for (MovieRecommendation movie_rec : this
				.getRecommendationListByName(list_name)) {
			if (movie_rec.getTitle().toLowerCase().equals(title.toLowerCase())
					&& movie_rec.getYear() == year) {
				list.addMovieRecommendation(movie_rec);
			}
		}

		return list;
	}

	public void addUser(User.UserType user_type, String username, String password) {
		User user = new User(username, password, user_type);
		this.users_list.addUser(user);
	}

	/**
	 * Delete a user from the user database
	 * 
	 * @param username
	 *            The username
	 */
	public boolean deleteUser(String username) {
		for (User u : this.users_list) {
			if (u.getUserName().equals(username)) {
				this.users_list.removeUser(u);
				return true;
			}
		}
		return false;
	}

	/**
	 * Validate if the provided username and password match
	 * 
	 * @param username
	 *            The username
	 * @param password
	 *            The password
	 * @return True is the username and password match
	 */
	public User validateUserPassword(String username, String password) {
		for (User user : this.users_list) {
			if (user.validate(username, password)) {
				this.user = user;
				return user;
			}
		}
		return null;
	}

	private List getRecommendationListByName(String name) throws InvalidListExcpetion {
		// find recommendation list
		for (List rec : this.recommendation_lists) {
			if (rec.getListName().equals(name)) {
				return rec;
			}
		}
		throw new InvalidListExcpetion();
	}

	private Database getRecommendationDatabaseByName(String name) throws InvalidDatabaseException {
		// find recommendation list
		for (Database db : this.recommendation_databases) {
			if (db.getName().equals(name)) {
				return db;
			}
		}
		throw new InvalidDatabaseException();
	}
	
	public void saveState() throws IOException {
		ObjectOutputStream writer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("save.dat"))));
		writer.writeObject(this);
		writer.close();
	}
	
	public static Application loadState() throws IOException, ClassNotFoundException {
		File f = new File("save.dat");
		
		if (f.canRead()) {
			ObjectInputStream reader = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
			
			Application app = (Application)reader.readObject();
			return app;
		}
		
		return null;
	}
	
}
