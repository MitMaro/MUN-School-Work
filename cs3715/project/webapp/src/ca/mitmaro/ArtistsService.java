package ca.mitmaro;
/*
Computer Science 3715
Team Project
Music Wishlist

By: Lauren Stratton #########
    Tim Oram        #########
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.google.gson.Gson;


class ArtistsService {
	
	/**
	 * Create the artists table in the sqlite database if it doesn't exist
	 */
	public static void createTable() throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
		Statement stat = conn.createStatement();
		stat.execute(
			"CREATE TABLE IF NOT EXISTS artists (" +
				"id INTEGER NOT NULL," +
				"name TEXT," +
				"priority TEXT," +
				"PRIMARY KEY (id))"
		);
	}
	
	/**
	 * Add a new artist to the database
	 *
	 * @param An instance of a artist
	 */
	public static void add(Artist artist) throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
		
		// create our query
		PreparedStatement prep = conn.prepareStatement(
			"INSERT INTO artists (name, priority) VALUES (?, ?)"
		);
		prep.setString(1, artist.getName());
		prep.setString(2, artist.getPriorityAsString());
		
		// execute
		prep.executeUpdate();
		
		// clean up
		prep.close();
		conn.close();
	}
	
	/**
	 * Update an existsing artist
	 *
	 * @param An instance of a artist
	 */
	public static void update(Artist artist) throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
		
		// create our query
		PreparedStatement prep = conn.prepareStatement(
			"UPDATE artists SET name = ?, priority = ? WHERE id = ?"
		);
		prep.setString(1, artist.getName());
		prep.setString(2, artist.getPriorityAsString());
		prep.setInt(3, artist.getId());
		
		// execute
		prep.executeUpdate();
		
		// clean up
		prep.close();
		conn.close();
	}
	
	/**
	 * Delete an artist
	 *
	 * @param An instance of a artist
	 */
	public static void delete(Artist artist) throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
		
		// create our query
		PreparedStatement prep = conn.prepareStatement(
			"DELETE FROM artists WHERE id = ?"
		);
		prep.setInt(1, artist.getId());
		
		// execute
		prep.executeUpdate();
		
		// clean up
		prep.close();
		conn.close();
	}
	
	/**
	 * Gets an array of artists
	 *
	 * @param sortby The field to sort on
	 * @param order The order to sort (ASC, DESC)
	 * @param page The page used to cacluate offset
	 *
	 * @return An array of artists
	 */
	public static Artist[] get(String sortby, String order, int page) throws Exception {
		
		int id;
		String name;
		String priority;
		ArrayList<Artist> artists = new ArrayList<Artist>();
		
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
		
		if (!order.equals("DESC")) {
			order = "ASC";
		}
		
		// check for invalid data
		if (!sortby.equals("name") && !sortby.equals("priority")) {
			sortby = "name";
		} else if (sortby.equals("priority")) { // priorty requires a special orderby
			sortby = " CASE artists.priority " +
				"WHEN \"LOWEST\" THEN 0 " +
				"WHEN \"LOW\" THEN 1 " +
				"WHEN \"MEDIUM\" THEN 2 " +
				"WHEN \"HIGH\" THEN 3 " +
				"WHEN \"HIGHEST\" THEN 4 " +
			"END ";
		} else {
			sortby += " COLLATE NOCASE";
		}
		
		
		// check for invalid page
		if (page < 1) {
			page = 1;
		}
		
		String query =
			"SELECT id, name, priority " +
			"FROM artists " +
			"ORDER BY " + sortby + " " + order +
			" LIMIT 10 OFFSET ?";
			
		// create our query
		PreparedStatement prep = conn.prepareStatement(query);
		
		prep.setInt(1, (page  - 1) * 10);
		
		// get results
		ResultSet results = prep.executeQuery();
		
		// create array of artists object from result set
		while (results.next()) {
			id = results.getInt("id");
			name = results.getString("name");
			priority = results.getString("priority");
			artists.add(new Artist(id, name, priority));
		}
		
		// clean up
		prep.close();
		conn.close();
		
		return artists.toArray(new Artist[0]);
	}
	/**
	 * @return Number of entries in the artists table
	 */
	public static int getCount() throws Exception {
		
		int cnt = 0;
		
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
		Statement stat = conn.createStatement();
		ResultSet result = stat.executeQuery(
			"SELECT COUNT(id) as c FROM artists"
		);
		
		if (result.next()) {
			cnt = result.getInt("c");
		}
		
		// clean up
		stat.close();
		conn.close();
		return cnt;
	}
	
}
