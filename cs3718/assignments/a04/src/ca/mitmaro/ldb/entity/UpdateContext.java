/**
 * 
 */
package ca.mitmaro.ldb.entity;

// technically a struct
public class UpdateContext {
	public String address;
	public String author_first;
	public String author_last;
	public String book_title;
	public String journal_title;
	public String chapter_title;
	public String editors;
	public int end_page = -1;
	public String paper_title;
	public String publisher;
	public int start_page = -1;
	public String title;
	public int volume = -1;
	public int year = -1;
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
