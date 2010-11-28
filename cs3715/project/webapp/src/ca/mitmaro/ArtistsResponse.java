package ca.mitmaro;
/*
Computer Science 3715
Team Project
Music Wishlist

By: Lauren Stratton #########
    Tim Oram        #########
*/


/**
 * A response class for the artists data
 */
class ArtistsResponse extends Response {
	
	/**
	 * @var The array or artist objects
	 */
	protected Artist[] data;
	
	/**
	 * @var The current page
	 */
	protected int page;
	
	/**
	 * @var The total artists in the database
	 */
	protected int totalResults;
	
	/**
	 * Constructor to set the data for the class
	 */
	public ArtistsResponse(Artist[] artists, int page, int totalResults) {
		super();
		this.data = artists;
		this.page = page;
		this.totalResults = totalResults;
	}
	
}
