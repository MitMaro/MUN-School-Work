/*
Computer Science 3715
Assignment #5
Image Rotate With Comments

By: Tim Oram
Student Number: #########
*/

package ca.mitmaro;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.Gson;

/**
 * The servlet for comments on images
 */
public class CommentsServlet extends HttpServlet {
	
	protected void doGet(
		HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			PrintWriter out = response.getWriter();
			
			// this is json data
			response.setContentType("application/json");
			
			// get the image name to get comments
			String imageName = request.getParameter("image_name");
			if (imageName == null) {
				out.print(this.doParameterError("Image Name Parameter Missing"));
				return;
			}
			
			try {
				// grab the comments for this image from the database
				Comment[] comments = CommentsService.getCommentsForImageName(imageName);
				// print our response object as a json string
				Gson gson = new Gson();
				out.print(gson.toJson(new ImageCommentsResponse(comments)));
			} catch (Exception e) {
				out.print(this.doError(e.getMessage()));
				return;
			}
	}
	
	protected void doPost(
		HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			
			// get the author
			String author = request.getParameter("author");
			if (author == null || author.equals("")) {
				out.print(this.doParameterError("Author Parameter Missing"));
				return;
			}
			
			// get the comment
			String comment = request.getParameter("comment");
			if (comment == null || comment.equals("")) {
				out.print(this.doParameterError("Comment Parameter Missing"));
				return;
			}
			
			// get the author
			String imageName = request.getParameter("image_name");
			if (imageName == null || imageName.equals("")) {
				out.print(this.doParameterError("Image Name Parameter Missing"));
				return;
			}
			
			try {
				// add the comment to the database
				CommentsService.addComment(new Comment(author, comment, imageName));
			} catch (Exception e) {
				out.print(this.doError(e.getMessage()));
				return;
			}
			// print our response object as a json string
			Gson gson = new Gson();
			out.print(gson.toJson(new Response(201, "Comment Created")));
			
	}
	
	/**
	 * Create an json string for an error state
	 *
	 * @param message An error message
	 *
	 * @return A json encoded string
	 */
	protected String doError(String message) {
		// return an error response
		Gson gson = new Gson();
		return gson.toJson(new Response(500, message));
	}
	/**
	 * Create a json string for a parameter error
	 *
	 * @param message An error message
	 *
	 * @return A json encoded string
	 */
	protected String doParameterError(String message) {
		// return an error response
		Gson gson = new Gson();
		return gson.toJson(new Response(400, message));
	}
	
	/**
	 * Make sure the database is created with the right tables
	 */
	public static void checkDatabase() throws Exception {
		CommentsService.createTable();
	}
	
}