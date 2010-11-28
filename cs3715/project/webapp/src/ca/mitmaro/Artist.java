package ca.mitmaro;
/*
Computer Science 3715
Team Project
Music Wishlist

By: Lauren Stratton #########
    Tim Oram        #########
*/

/**
 * An artist model
 */
class Artist {
	
	/**
	 * Priority status
	 */
	protected enum Priority {
		LOWEST, LOW, MEDIUM, HIGH, HIGHEST 
	}
	/**
	 * @var The artists unique id
	 */
	protected int id;
	
	/**
	 * @var The artists name
	 */
	protected String name;
	
	/**
	 * @var The priority
	 */
	protected Priority priority;
	
	/**
	 * Empty Constructor
	 */
	public Artist () {
		this (0, "", "MEDIUM");
	}
	
	/**
	 * Constuctor that takes an id. Default name of "" and default priority of "MEDIUM"
	 *
	 * @param id The artist id
	 */
	public Artist (int id) {
		this (id, "", "MEDIUM");
	}
	
	/**
	 * Constuctor that takes a name and a priority. Default id of 0
	 *
	 * @param name The artist name
	 * @param priority The priority
	 */
	public Artist (String name, String priority) {
		this(0, name, priority);
	}
	
	/**
	 * Constuctor that takes an id, a name and a priority.
	 * 
	 * @param id The artist id
	 * @param name The artist name
	 * @param priority The priority
	 */
	public Artist (int id, String name, String priority) {
		this.id = id;
		this.name = name;
		this.priority = Priority.valueOf(priority);
	}
	
	/**
	 * @return The id of the artists
	 */
	public int getId () {
		return this.id;
	}
	
	/**
	 * @return The name of the artist
	 */
	public String getName () {
		return this.name;
	}
	
	/**
	 * @return The priority of the artist as a string
	 */
	public String getPriorityAsString() {
		return this.priority.toString();
	}
	
}
