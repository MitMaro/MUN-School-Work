/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.entity;

import ca.mitmaro.ldb.exception.InvalidListException;

public abstract class Paper {
	
	private static class Data {
		private String title;
		private String author_first;
		private String author_last;
		private int year;
		
		protected Data(String author_first, String author_last, String title, int year) {
			this.author_first = author_first;
			this.author_last = author_last;
			this.title = title;
			this.year = year;
		}
		
		protected Data(Data data) {
			this.author_first = data.author_first;
			this.author_last = data.author_last;
			this.title = data.title;
			this.year = data.year;
		}
		
		protected String getTitle() {
			return this.title;
		}

		protected String getAuthorFirst() {
			return this.author_first;
		}

		protected String getAuthorLast() {
			return this.author_last;
		}
		
		protected void setTitle(String title) {
			this.title = title;
		}
		
		protected void setAuthorFirst(String name) {
			this.author_first = name;
		}
		
		protected void setAuthorLast(String name) {
			this.author_last = name;
		}
		
		protected int getYear() {
			return this.year;
		}
		
		protected void setYear(int year) {
			this.year = year;
		}
	}
	
	private String id;
	private String type;

	private List<Paper> references;
	private List<Paper> citations;
	
	private List<Data> data_sets; 
	
	public Paper (String id, String type) {
		this.id = id;
		this.type = type;
		this.data_sets = new List<Data>();
		this.references = new List<Paper>();
		this.citations = new List<Paper>();
	}
	
	protected void setDataSet (
		String list_name, String title, String author_first, String author_last, int year
	) {
		this.data_sets.put(list_name, new Data(author_first, author_last, title, year));
	}
	
	public void addToList(String new_list, String old_list) {
		if (this.data_sets.has(old_list)) {
			this.data_sets.put(new_list, new Data(this.data_sets.get(old_list)));
		} else {
			throw new InvalidListException();
		}
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getTitle(String list_name) {
		if (this.data_sets.has(list_name)) {
			return this.data_sets.get(list_name).getTitle();
		}
		throw new InvalidListException();
	}

	public String getAuthorFirst(String list_name) {
		if (this.data_sets.has(list_name)) {
			return this.data_sets.get(list_name).getAuthorFirst();
		}
		throw new InvalidListException();
	}

	public String getAuthorLast(String list_name) {
		if (this.data_sets.has(list_name)) {
			return this.data_sets.get(list_name).getAuthorLast();
		}
		throw new InvalidListException();
	}

	public int getYear(String list_name) {
		if (this.data_sets.has(list_name)) {
			return this.data_sets.get(list_name).getYear();
		}
		throw new InvalidListException();
	}

	public void updateField(String list_name, String field_name, String value) {
		
		if (!this.data_sets.has(list_name)) {
			throw new InvalidListException();
		}
		
		if (field_name.equals("author_first")) {
			this.data_sets.get(list_name).setAuthorFirst(value);
		} else if (field_name.equals("author_last")) {
			this.data_sets.get(list_name).setAuthorLast(value);
		} else if (field_name.equals("title")) {
			this.data_sets.get(list_name).setTitle(value);
		}
	}
	
	public void updateField(String list_name, String field_name, int value) {
		
		if (!this.data_sets.has(list_name)) {
			throw new InvalidListException();
		}
		
		if (field_name.equals("year")) {
			this.data_sets.get(list_name).setYear(value);
		}
	}
	
	public void addReference(Paper paper) {
		this.references.put(paper.getId(), paper);
	}
	
	public List<Paper> getReferences() {
		return this.references;
	}

	public void addCitation(Paper paper) {
		this.citations.put(paper.getId(), paper);
	}
	
	public List<Paper> getCitations() {
		return this.citations;
	}
	
	abstract public String getPrintable(String list_name, String padding);
	
}
