/*
Computer Science 3715
Assignment #5
Image Rotate With Comments

By: Tim Oram
Student Number: #########
*/
package ca.mitmaro;

/**
 * Describes a comment
 */
class Comment {
	/**
	 * @var The comment text
	 */
	protected String comment;
	/**
	 * @var The comment author's name
	 */
	protected String author;
	/**
	 * @var The images name
	 */
	protected String imageName;
	
	/**
	 * Empty constructor
	 */
	public Comment() {
	}
	
	/**
	 * Construct a Comment providing all data
	 *
	 * @param comment The comment text
	 * @param author The comment's author
	 * @param imageName The name of the image associtated with this comment
	 */
	public Comment(String comment, String author, String imageName) {
		this.comment = comment;
		this.author = author;
		this.imageName = imageName;
	}
	
	/**
	 * Get the comment text
	 *
	 * @return The comment text
	 */
	public String getComment() {
		return this.comment;
	}
	
	/**
	 * Get the author's name
	 *
	 * @return The author's name
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * Get the image name
	 *
	 * @return The image name
	 */
	public String getImageName() {
		return this.imageName;
	}
	
}