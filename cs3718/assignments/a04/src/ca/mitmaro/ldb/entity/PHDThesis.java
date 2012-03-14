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

public class PHDThesis extends Paper {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8804847179299804274L;

	protected static class Context extends Paper.Context {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8335676577790079990L;
		protected String address;
		
		protected Context() {}

		protected Context(UpdateContext context) {
			super.update(context);
		}
		
		@Override
		protected void update(Paper.Context context) {
			super.update(context);
			Context c = (Context)context;
			this.address = c.address;
		}
		
		@Override
		protected void update(UpdateContext context) {
			super.update(context);
			if (context.address != null) {
				this.address = context.address;
			}
		}
	}
	
	public PHDThesis(String id) {
		super(id);
	}
	
	public String getAddress(String list_name) {
		if (this.data_sets.containsKey(list_name)) {
			return ((Context)this.data_sets.get(list_name)).address;
		}
		return null;
	}
	
	@Override
	public String getPrintable(String list_name, String padding) {
		
		if (!this.data_sets.containsKey(list_name)) {
			throw new InvalidListException();
		}
		
		return String.format(
			"%s%s: %s, %s (%d) %s\n%s\t%s.\n",
			padding,
			this.getId(),
			this.getAuthorLast(list_name),
			this.getAuthorFirst(list_name),
			this.getYear(list_name),
			StringUtils.truncate(this.getTitle(list_name), 35),
			padding,
			this.getAddress(list_name)
		);
	}

	@Override
	public String[] getFields() {
		String[] fields = {"Title", "Author", "Year", "Address"};
		return fields;
	}

	@Override
	public Context getContext() {
		return new Context();
	}
	
	
}
