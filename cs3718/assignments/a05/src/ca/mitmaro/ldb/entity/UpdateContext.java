/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.entity;

/**
 * An update context, used in updating papers
 *
 *@author Tim Oram (MitMaro)
 */
public class UpdateContext { // technically a struct
	/**
	 * The address
	 */
	public String address;
	/**
	 * The authors first name 
	 */
	public String author_first;
	/**
	 * The authors last name
	 */
	public String author_last;
	/**
	 * The book title
	 */
	public String book_title;
	/**
	 * The journal title
	 */
	public String journal_title;
	/**
	 * The chapter title
	 */
	public String chapter_title;
	/**
	 * The editors
	 */
	public String editors;
	/**
	 * The page end range
	 */
	public int end_page = -1;
	/**
	 * The paper title
	 */
	public String paper_title;
	/**
	 * The publisher
	 */
	public String publisher;
	/**
	 * The page start range
	 */
	public int start_page = -1;
	/**
	 * The title
	 */
	public String title;
	/**
	 * The volume
	 */
	public int volume = -1;
	/**
	 * The year
	 */
	public int year = -1;
	/**
	 * Reset values back to their default states (null for Strings, -1 for integers)
	 */
	public void reset() {
		this.address =
		this.author_first =
		this.author_last =
		this.book_title =
		this.journal_title =
		this.chapter_title =
		this.editors =
		this.paper_title =
		this.publisher =
		this.title =
			null;
		this.end_page =
		this.start_page =
		this.volume =
		this.year =
			-1;
	}
}
