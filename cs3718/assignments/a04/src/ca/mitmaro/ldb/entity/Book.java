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

public class Book extends Paper {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6615794449271578935L;

	protected static class Context extends Paper.Context {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -1533019143927049778L;
		protected String address;
		protected String publisher;
		
		protected Context() {}
		
		protected Context(UpdateContext context) {
			this.update(context);
		}

		@Override
		protected void update(Paper.Context context) {
			super.update(context);
			Context c = (Context)context;
			this.address = c.address;
			this.publisher = c.publisher;
		}
		
		@Override
		protected void update(UpdateContext context) {
			super.update(context);

			if (context.book_title != null) {
				this.title = context.book_title;
			}
			
			if (context.publisher != null) {
				this.publisher = context.publisher;
			}
			if (context.address != null) {
				this.address = context.address;
			}
		}
	}
	
	public Book(String id) {
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
	
	@Override
	public String getPrintable(String list_name, String padding) {
		
		if (!this.data_sets.containsKey(list_name)) {
			throw new InvalidListException();
		}
		
		return String.format(
			"%s%s: %s, %s (%d) %s\n%s\t%s; %s.\n",
			padding,
			this.getId(),
			this.getAuthorLast(list_name),
			this.getAuthorFirst(list_name),
			this.getYear(list_name),
			StringUtils.truncate(this.getTitle(list_name), 35),
			padding,
			this.getPublisher(list_name),
			this.getAddress(list_name)
		);
	}
	
	@Override
	public Context getContext() {
		return new Context();
	}

	@Override
	public String[] getFields() {
		String[] fields = {"Author", "Year", "Book Title", "Address", "Publisher"};
		return fields;
	}

}
