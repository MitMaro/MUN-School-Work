/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;

import ca.mitmaro.ldb.exception.InvalidListException;

public abstract class Paper implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4345814402579311723L;

	protected static class Context implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 32075534600184931L;
		
		protected String title;
		protected String author_first;
		protected String author_last;
		protected int year;
		
		protected Context() {}
		
		protected Context(UpdateContext context) {
			this.update(context);
		}
		
		protected void update(Context context) {
			this.title = context.title;
			this.author_first = context.author_first;
			this.author_last = context.author_last;
			this.year = context.year;
		}
		
		protected void update(UpdateContext context) {
			
			if (context.author_first != null) {
				this.author_first = context.author_first;
			}
			
			if (context.author_last != null) {
				this.author_last = context.author_last;
			}
			
			if (context.title != null) {
				this.title = context.title;
			}
			
			if (context.year != -1) { 
				this.year = context.year;
			}
		}
	}
	
	private String id;

	private LinkedHashSet<Paper> references;
	private LinkedHashSet<Paper> citations;
	
	protected LinkedHashMap<String, Context> data_sets; 
	
	public Paper (String id) {
		this.id = id;
		this.data_sets = new LinkedHashMap<String, Context>();
		this.references = new LinkedHashSet<Paper>();
		this.citations = new LinkedHashSet<Paper>();
	}
	
	protected void setDataSet (String list_name, UpdateContext context) {
		Context c = this.getContext();
		c.update(context);
		this.data_sets.put(list_name, c);
	}
	
	public void addToList(String new_list, String old_list) {
		if (this.data_sets.containsKey(old_list)) {
			Context context = this.getContext();
			context.update(this.data_sets.get(old_list));
			this.data_sets.put(new_list, context);
		} else {
			throw new InvalidListException();
		}
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getTitle(String list_name) {
		if (this.data_sets.containsKey(list_name)) {
			return this.data_sets.get(list_name).title;
		}
		throw new InvalidListException();
	}

	public String getAuthorFirst(String list_name) {
		if (this.data_sets.containsKey(list_name)) {
			return this.data_sets.get(list_name).author_first;
		}
		throw new InvalidListException();
	}

	public String getAuthorLast(String list_name) {
		if (this.data_sets.containsKey(list_name)) {
			return this.data_sets.get(list_name).author_last;
		}
		throw new InvalidListException();
	}

	public int getYear(String list_name) {
		if (this.data_sets.containsKey(list_name)) {
			return this.data_sets.get(list_name).year;
		}
		throw new InvalidListException();
	}

	public void update(String list_name, UpdateContext context) {
		if (!this.data_sets.containsKey(list_name)) {
			this.data_sets.put(list_name, this.getContext());
		}
		
		this.data_sets.get(list_name).update(context);
	}
	
	public void addReference(Paper paper) {
		this.references.add(paper);
	}
	
	public LinkedHashSet<Paper> getReferences() {
		return this.references;
	}

	public void addCitation(Paper paper) {
		this.citations.add(paper);
	}
	
	public LinkedHashSet<Paper> getCitations() {
		return this.citations;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Paper && ((Paper) obj).getId().equals(this.getId());
	}
	
	abstract public String getPrintable(String list_name, String padding);
	
	abstract public String[] getFields();
	
	abstract protected Context getContext();
	
}
