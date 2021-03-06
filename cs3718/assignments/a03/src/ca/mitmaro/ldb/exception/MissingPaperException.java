/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.exception;

public class MissingPaperException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5645769112193804890L;

	/**
	 * @param message
	 */
	public MissingPaperException(String message) {
		super(message);
	}

}
