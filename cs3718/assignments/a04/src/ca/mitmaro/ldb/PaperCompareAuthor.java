package ca.mitmaro.ldb;

import java.util.Comparator;

import ca.mitmaro.ldb.entity.Paper;
import ca.mitmaro.ldb.exception.InvalidListException;

/**
 * The paper author comparator
 * 
 * @author Tim Oram (MitMaro)
 *
 */
public class PaperCompareAuthor implements Comparator<Paper> {
	/**
	 * The list name
	 */
	private String list_name;
	
	
	/**
	 * Construct an instance of this comparator with provided list name
	 * @param list_name
	 */
	public PaperCompareAuthor(String list_name) {
		this.list_name = list_name;
	}
	
	@Override
	public int compare(Paper p1, Paper p2) {
		String author_full_1;
		String author_full_2;
		try {
			author_full_1 = p1.getAuthorLast(this.list_name) + p1.getAuthorFirst(this.list_name);
			author_full_2 = p2.getAuthorLast(this.list_name) + p2.getAuthorFirst(this.list_name);
		} catch (InvalidListException e) {
			// return equals on fail (not sure how to handle this case)
			return 0;
		}
		return author_full_1.compareTo(author_full_2);
	}
}
