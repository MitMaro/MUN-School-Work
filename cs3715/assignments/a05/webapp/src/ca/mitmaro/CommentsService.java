/*
Computer Science 3715
Assignment #5
Image Rotate With Comments

By: Tim Oram
Student Number: #########
*/
package ca.mitmaro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.google.gson.Gson;


/**
 * Service class for accessing Comments
 */
class CommentsService {
	
	/**
	 * Create the comments table in the sqlite database if it doesn' exist
	 */
	public static void createTable() throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
		Statement stat = conn.createStatement();
		stat.execute(
			"CREATE TABLE IF NOT EXISTS comments (" +
				"id INTEGER NOT NULL," +
				"author TEXT," +
				"comment TEXT," +
				"image_name TEXT," +
				"PRIMARY KEY (id))"
		);
		
	}
	
	/**
	 * Add a new comment to the database
	 *
	 * @param An instance of a comment
	 */
	public static void addComment(Comment cmt) throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
		
		// create our query
		PreparedStatement prep = conn.prepareStatement(
			"INSERT INTO comments (author, comment, image_name) VALUES (?, ?, ?)"
		);
		prep.setString(1, cmt.getAuthor());
		prep.setString(2, cmt.getComment());
		prep.setString(3, cmt.getImageName());
		
		// execute
		prep.executeUpdate();
		
		// clean up
		prep.close();
		conn.close();
	}
	
	/**
	 * Gets an array of comments for a given image name
	 *
	 * @param imageName The name of the image
	 *
	 * @return An array of comments
	 */
	public static Comment[] getCommentsForImageName(String imageName) throws Exception {
		
		String author;
		String comment;
		ArrayList<Comment> comments = new ArrayList<Comment>();
		
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
		
		// create our query
		PreparedStatement prep = conn.prepareStatement(
			"SELECT author, comment FROM comments WHERE image_name = ?"
		);
		prep.setString(1, imageName);
		
		// get results
		ResultSet results = prep.executeQuery();
		
		// create array of Comment object from result set
		while (results.next()) {
			author = results.getString("author");
			comment = results.getString("comment");
			comments.add(new Comment(author, comment, imageName));
		}
		
		// clean up
		prep.close();
		conn.close();
		
		return comments.toArray(new Comment[0]);
	}
}