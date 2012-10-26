/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.entity;

import ca.mitmaro.lang.StringUtils;
import ca.mitmaro.ldb.exception.InvalidListException;

/**
 * A conference paper
 * 
 * @author Tim Oram (MitMaro)
 *
 */
public class ConferencePaper extends Paper {
	
	/**
	 * The serialization id
	 */
	private static final long serialVersionUID = -7270600896289215526L;

	/**
	 * The data context for a conference paper
	 *
	 */
	protected static class Context extends Paper.Context {
		
		/**
		 * The serialization id
		 */
		private static final long serialVersionUID = 1356517968499971044L;
		/**
		 * The chapter title
		 */
		protected String chapter_title;
		/**
		 * The address
		 */
		protected String address;
		/**
		 * The publisher
		 */
		protected String publisher;
		/**
		 * The start page range
		 */
		protected int start_page;
		/**
		 * The end page range
		 */
		protected int end_page;
		/**
		 * The editors
		 */
		protected String editors;
		
		
		/**
		 * Construct an empty context
		 */
		protected Context() {}
		
		/**
		 * @param context
		 */
		protected Context(UpdateContext context) {
			this.update(context);
		}

		@Override
		protected void update(Paper.Context context) {
			super.update(context);
			Context c = (Context)context;
			this.address = c.address;
			this.publisher = c.publisher;
			this.chapter_title = c.chapter_title;
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
	
	/**
	 * Construct a conference paper given an id
	 */
	public ConferencePaper(String id) {
		super(id);
	}
	
	/**
	 * Get an address with respect to a book
	 * 
	 * @param list_name The name of the list
	 * @return The address
	 * @throws InvalidListException If this book is not attached to a list
	 */
	public String getAddress(String list_name) throws InvalidListException {
		if (this.data_sets.containsKey(list_name)) {
			return ((Context)this.data_sets.get(list_name)).address;
		}
		throw new InvalidListException(list_name);
	}

	/**
	 * Get the publisher with respect to a list
	 * 
	 * @param list_name The name of the list
	 * @return The publisher
	 * @throws InvalidListException If this book is not attached to a list
	 */
	public String getPublisher(String list_name) throws InvalidListException {
		if (this.data_sets.containsKey(list_name)) {
			return ((Context)this.data_sets.get(list_name)).publisher;
		}
		throw new InvalidListException(list_name);
	}

	/**
	 * Gets the editor with respect to a list
	 * 
	 * @param list_name The name of the list
	 * @return The editor
	 * @throws InvalidListException If this book is not attached to a list
	 */
	public String getEditors(String list_name) throws InvalidListException {
		if (this.data_sets.containsKey(list_name)) {
			return ((Context)this.data_sets.get(list_name)).editors;
		}
		throw new InvalidListException(list_name);
	}
	
	/**
	 * Gets a chapter title with respect to a list
	 * 
	 * @param list_name The name of the list
	 * @return The chapter title
	 * @throws InvalidListException If this book is not attached to a list
	 */
	public String getChapterTitle(String list_name) throws InvalidListException {
		if (this.data_sets.containsKey(list_name)) {
			return ((Context)this.data_sets.get(list_name)).chapter_title;
		}
		throw new InvalidListException(list_name);
	}

	public String getTitle(String list_name) throws InvalidListException {
		return this.getChapterTitle(list_name);
	}
	
	public String getBookTitle(String list_name) throws InvalidListException {
		return super.getTitle(list_name);
	}
	
	/**
	 * Gets the pages as a range (a-b or just a if a==b)
	 * 
	 * @param list_name The name of the list
	 * @return The page range
	 * @throws InvalidListException If this book is not attached to a list
	 */
	public String getPages(String list_name) throws InvalidListException {
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
		throw new InvalidListException(list_name);
	}
	
	@Override
	public String getPrintable(String list_name, String padding) throws InvalidListException {
		
		if (!this.data_sets.containsKey(list_name)) {
			throw new InvalidListException(list_name);
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
			StringUtils.truncate(this.getBookTitle(list_name), 35),
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
	protected Context getContext() {
		return new Context();
	}
}
