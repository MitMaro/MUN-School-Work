/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.exception;

/**
 * Exception for an invalid list name
 * 
 * @author Tim Oram (MitMaro)
 *
 */
public class InvalidListException extends Exception {

	/**
	 * The serialization id
	 */
	private static final long serialVersionUID = -3457797329462685360L;
	
	/**
	 * The name of the list
	 */
	private final String list_name;
	
	/**
	 * Construct a list with a list name
	 * @param list_name
	 */
	public InvalidListException(String list_name) {
		super(String.format("The list with name, %s, does not exist.", list_name));
		this.list_name = list_name;
	}
	
	/**
	 * @return the list name
	 */
	public String getListName() {
		return this.list_name;
	}
}
