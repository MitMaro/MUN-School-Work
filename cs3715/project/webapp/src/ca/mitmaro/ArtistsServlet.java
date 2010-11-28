package ca.mitmaro;
/*
Computer Science 3715
Team Project
Music Wishlist

By: Lauren Stratton #########
    Tim Oram        #########
*/

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.NumberFormatException;
import com.google.gson.Gson;

/**
 * The servlet for comments on artists
 */
public class ArtistsServlet extends HttpServlet {
	
	protected void doGet(
		HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			PrintWriter out = response.getWriter();
			
			// this is json data
			response.setContentType("application/json");
			
			Gson gson = new Gson();
			
			int page;
			
			// get sorting info
			String sortby = request.getParameter("sortby");
			String order = request.getParameter("order");
			
			// small pause to show loader in html page because the script returns to fast
			try {Thread.sleep(1500);}catch(Exception e){};
			
			// validate page
			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (NumberFormatException $e) {
				page = 1; // fail safe
			}
			
			// validate sortby
			if (sortby == null || sortby.equals("")) {
				out.print(this.doParameterError("Sort By Parameter Missing"));
				return;
			}
			
			// validate order
			if (order == null || order.equals("")) {
				out.print(this.doParameterError("Order Parameter Missing"));
				return;
			}
			
			// get and return a list of artists
			try {
				Artist[] artists = ArtistsService.get(sortby, order, page);
				int totalResults = ArtistsService.getCount();
				out.print(gson.toJson(new ArtistsResponse(artists, page, totalResults)));
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
			
			Gson gson = new Gson();
			
			// get an id
			int id;
			
			// validate id
			try {
				id = Integer.parseInt(request.getParameter("id"));
			} catch(NumberFormatException e) {
				id = 0; // fail safe
			}
			
			// get the artist name and priority
			String name = request.getParameter("name");
			String priority = request.getParameter("priority");
			
			try {
				// if delete
				if (request.getParameter("delete") != null) {
					Artist artist = new Artist(id);
					ArtistsService.delete(artist);
					out.print(gson.toJson(new Response(200, "Artist Deleted")));
					return;
				} else {
					
					// validate name
					if (name == null || name.equals("")) {
						out.print(this.doParameterError("Artist Name Parameter Missing"));
						return;
					}
					
					// validate priority
					if (priority == null || priority.equals("")) {
						out.print(this.doParameterError("Priority Parameter Missing"));
						return;
					}
					
					if (id > 0) { // id given we are editing
						ArtistsService.update(new Artist(id, name, priority));
						out.print(gson.toJson(new Response(200, "Artist Updated")));
					} else { // else we are adding
						ArtistsService.add(new Artist(name, priority));
						out.print(gson.toJson(new Response(200, "Artist Added")));
					}
				}
			} catch (Exception e) {
				out.print(this.doError(e.getMessage()));
			}
			
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
	
}
