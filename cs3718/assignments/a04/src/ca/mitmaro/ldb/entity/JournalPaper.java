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

public class JournalPaper extends Paper {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7708267179369016444L;

	protected static class Context extends Paper.Context {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 6023474742003662921L;
		protected String paper_title;
		protected int volume;
		protected int start_page;
		protected int end_page;
		
		protected Context() {}
		
		protected Context(UpdateContext context) {
			super.update(context);
		}

		@Override
		protected void update(Paper.Context context) {
			super.update(context);
			Context c = (Context)context;
			this.paper_title = c.paper_title;
			this.start_page = c.start_page;
			this.end_page = c.end_page;
			this.volume = c.volume;
		}

		@Override
		protected void update(UpdateContext context) {
			super.update(context);

			if (context.journal_title != null) {
				this.title = context.journal_title;
			}
			
			if (context.paper_title != null) {
				this.paper_title = context.paper_title;
			}
			
			if (context.volume != -1) {
				this.volume = context.volume;
			}
			
			if (context.start_page != -1) {
				this.start_page = context.start_page;
			}
			
			if (context.end_page != -1) {
				this.end_page = context.end_page;
			}
		}
		
	}
	
	public JournalPaper(String id) {
		super(id);
	}
	
	public String getPaperTitle(String list_name) {
		if (this.data_sets.containsKey(list_name)) {
			return ((Context)this.data_sets.get(list_name)).paper_title;
		}
		throw new InvalidListException();
	}
	
	public int getVolume(String list_name) {
		if (this.data_sets.containsKey(list_name)) {
			return ((Context)this.data_sets.get(list_name)).volume;
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
			"%s%s: %s, %s (%d) %s\n%s\t%s, %d, %s.\n",
			padding,
			this.getId(),
			this.getAuthorLast(list_name),
			this.getAuthorFirst(list_name),
			this.getYear(list_name),
			StringUtils.truncate(this.getPaperTitle(list_name), 35),
			padding,
			this.getTitle(list_name),
			this.getVolume(list_name),
			this.getPages(list_name)
		);
	}

	@Override
	public String[] getFields() {
		String[] fields = {"Paper Title", "Journal Title", "Author", "Year", "Volume", "Pages"};
		return fields;
	}

	@Override
	public Context getContext() {
		return new Context();
	}
	
	
}
