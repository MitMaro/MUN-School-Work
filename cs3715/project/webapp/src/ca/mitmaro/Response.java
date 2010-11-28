package ca.mitmaro;
/*
Computer Science 3715
Team Project
Music Wishlist

By: Lauren Stratton #########
    Tim Oram        #########
*/


/**
 * Base response class, contains a status
 */
class Response {
	
	/** The status **/
	protected class Status {
		public int code;
		public String message;
	}
	
	/**
	 * @var The status instance
	 */
	protected Status status;
	
	/**
	 * The default constructor, status code of 200, message of "OK"
	 */
	public Response() {
		this(200, "OK");
	}
	
	/**
	 * The constructor that takes a status code and statuse message
	 *
	 * @param statusCode The status code
	 * @param statusMessage The status message
	 */
	public Response(int stausCode, String statusMessage) {
		this.status = new Status();
		this.status.code = stausCode;
		this.status.message = statusMessage;
	}
	
}
