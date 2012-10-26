/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.exception;

/**
 * An exception when an invalid/missing paper is provided
 *
 */
public class MissingPaperException extends Exception {

	/**
	 * The serialization id
	 */
	private static final long serialVersionUID = 5645769112193804890L;
	
	/**
	 * The id of the paper
	 */
	private final String pid;
	
	/**
	 * Construct a exception with a paper id
	 * @param pid the paper id
	 */
	public MissingPaperException(String pid) {
		super(String.format("The paper with pid, %s, does not exist.", pid));
		this.pid = pid;
	}
	
	/**
	 * @return the paper id
	 */
	public String getPaperId() {
		return this.pid;
	}

}
