/*
Computer Science 3715
Assignment #5
Image Rotate With Comments

By: Tim Oram
Student Number: #########
*/

package ca.mitmaro;

/**
 * A response class for the comment data
 */
class ImageCommentsResponse extends Response {
	
	protected Comment[] data;
	
	/**
	 * Constructor to set the data for the class
	 */
	public ImageCommentsResponse(Comment[] comments) {
		super();
		this.data = comments;
	}
	
}
