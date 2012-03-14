/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.entity;

import ca.mitmaro.lang.StringUtils;
import ca.mitmaro.ldb.exception.InvalidListException;

public class BookChapter extends Paper {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8596221110373492026L;

	protected static class Context extends Paper.Context {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -2769685830653690325L;
		protected String chapter_title;
		protected String address;
		protected String publisher;
		protected int start_page;
		protected int end_page;
		protected String editors;
		
		protected Context() {}
		
		protected Context(UpdateContext context) {
			this.update(context);
		}

		@Override
		protected void update(Paper.Context context) {
			super.update(context);
			Context c = (Context)context;
			this.chapter_title = c.chapter_title;
			this.address = c.address;
			this.publisher = c.publisher;
			this.start_page = c.start_page;
			this.end_page = c.end_page;
			this.editors = c.editors;
		}
		
		@Override
		protected void update(UpdateContext context) {
			super.update(context);
			
			if (context.book_title != null) {
				this.title = context.book_title;
			}
			
			if (context.chapter_title != null) {
				this.chapter_title = context.chapter_title;
			}
			
			if (context.publisher != null) {
				this.publisher = context.publisher;
			}
			
			if (context.address != null) {
				this.address = context.address;
			}
			
			if (context.start_page != -1) {
				this.start_page = context.start_page;
			}
			
			if (context.end_page != -1) {
				this.end_page = context.end_page;
			}
			
			if (context.editors != null) {
				this.editors = context.editors;
			}
		}
		
	}
	
	public BookChapter(String id) {
		super(id);
	}
	
	public String getAddress(String list_name) {
		if (this.data_sets.containsKey(list_name)) {
			return ((Context)this.data_sets.get(list_name)).address;
		}
		throw new InvalidListException();
	}

	public String getPublisher(String list_name) {
		if (this.data_sets.containsKey(list_name)) {
			return ((Context)this.data_sets.get(list_name)).publisher;
		}
		throw new InvalidListException();
	}

	public String getEditors(String list_name) {
		if (this.data_sets.containsKey(list_name)) {
			return ((Context)this.data_sets.get(list_name)).editors;
		}
		throw new InvalidListException();
	}
	
	public String getChapterTitle(String list_name) {
		if (this.data_sets.containsKey(list_name)) {
			return ((Context)this.data_sets.get(list_name)).chapter_title;
		}
		throw new InvalidListException();
	}
	
	public String getPages(String list_name) {
		int start;
		int end;
		if (this.data_sets.containsKey(list_name)) {
			start = ((Context)this.data_sets.get(list_name)).start_page;
			end = ((Context)this.data_sets.get(list_name)).end_page;
			
			// single page
			if (start == end) {
				return Integer.toString(start);
			}
			
			return start + "-" + end;
		}
		throw new InvalidListException();
	}
	
	@Override
	public String getPrintable(String list_name, String padding) {
		
		if (!this.data_sets.containsKey(list_name)) {
			throw new InvalidListException();
		}
		
		return String.format(
			"%s%s: %s, %s (%d) %s\n%s\tIn %s (eds.) %s\n%s\t%s; %s. %s.\n",
			padding,
			this.getId(),
			this.getAuthorLast(list_name),
			this.getAuthorFirst(list_name),
			this.getYear(list_name),
			StringUtils.truncate(this.getChapterTitle(list_name), 35),
			padding,
			this.getEditors(list_name),
			StringUtils.truncate(this.getTitle(list_name), 35),
			padding,
			this.getPublisher(list_name),
			this.getAddress(list_name),
			this.getPages(list_name)
		);
	}

	@Override
	public String[] getFields() {
		String[] fields = {"Book Title", "Author", "Year", "Chapter Title", "Pages", "Address", "Publisher", "Editors"};
		return fields;
	}

	@Override
	public Context getContext() {
		return new Context();
	}
	
	
}
