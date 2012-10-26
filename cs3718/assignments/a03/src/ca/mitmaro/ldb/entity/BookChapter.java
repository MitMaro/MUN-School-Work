/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.entity;

import ca.mitmaro.lang.StringUtils;

public class BookChapter extends Paper {
	
	private static class Data {
		private String chapter_title;
		private String address;
		private String publisher;
		private int start_page;
		private int end_page;
		private String editors;
		
		public Data(
			String chp_title, String address, String publisher,
			int page_start, int page_end, String editors
		) {
			this.chapter_title = chp_title;
			this.publisher = publisher;
			this.address = address;
			this.start_page = page_start;
			this.end_page = page_end;
			this.editors = editors;
		}
		
		public void setChapterTitle(String chapter_title) {
			this.chapter_title = chapter_title;
		}
		
		public void setAddress(String address) {
			this.address = address;
		}
		
		public void setPublisher(String publisher) {
			this.publisher = publisher;
		}
		
		public void setStartPage(int start_page) {
			this.start_page = start_page;
		}
		
		public void setEndPage(int end_page) {
			this.end_page = end_page;
		}
		
		public void setEditors(String editors) {
			this.editors = editors;
		}
		
		public Data(Data data) {
			this.chapter_title = data.chapter_title;
			this.publisher = data.publisher;
			this.address = data.address;
			this.start_page = data.start_page;
			this.end_page = data.end_page;
			this.editors = data.editors;
		}

		protected String getAddress() {
			return this.address;
		}
		
		protected String getPublisher() {
			return this.publisher;
		}

		protected int getStartPage() {
			return start_page;
		}
		
		protected int getEndPage() {
			return end_page;
		}
		
		protected String getChapterTitle() {
			return this.chapter_title;
		}
		
		protected String getEditors() {
			return this.editors;
		}
	}
	
	private List<Data> data_sets; 
	
	public BookChapter(String id, String type) {
		super(id, type);
		this.data_sets = new List<Data>();
	}
	
	public void setDataSet (
		String list_name, String title, String author_first, String author_last,
		int year, String address, String editors, String publisher,
		String chp_title, int start_page, int end_page
	) {
		this.data_sets.put(
			list_name,
			new Data(chp_title, address, publisher, start_page, end_page, editors)
		);
		super.setDataSet(list_name, title, author_first, author_last, year);
	}
	
	public void addToList(String new_list, String old_list) {
		super.addToList(new_list, old_list);
		this.data_sets.put(new_list, new Data(this.data_sets.get(old_list)));
	}
	
	public String getAddress(String list_name) {
		if (this.data_sets.has(list_name)) {
			return this.data_sets.get(list_name).getAddress();
		}
		return null;
	}

	public String getPublisher(String list_name) {
		if (this.data_sets.has(list_name)) {
			return this.data_sets.get(list_name).getPublisher();
		}
		return null;
	}

	public String getEditors(String list_name) {
		if (this.data_sets.has(list_name)) {
			return this.data_sets.get(list_name).getEditors();
		}
		return null;
	}
	
	public String getChapterTitle(String list_name) {
		if (this.data_sets.has(list_name)) {
			return this.data_sets.get(list_name).getChapterTitle();
		}
		return null;
	}
	
	public String getPages(String list_name) {
		int start;
		int end;
		if (this.data_sets.has(list_name)) {
			start = this.data_sets.get(list_name).getStartPage();
			end = this.data_sets.get(list_name).getEndPage();
			
			// single page
			if (start == end) {
				return Integer.toString(start);
			}
			
			return start + "-" + end;
		}
		return null;
	}
	
	@Override
	public void updateField(String list_name, String field_name, String value) {
		super.updateField(list_name, field_name, value);
		
		if (field_name.equals("chapter_title")) {
			this.data_sets.get(list_name).setChapterTitle(value);
		} else if (field_name.equals("address")) {
			this.data_sets.get(list_name).setAddress(value);
		} else if (field_name.equals("publisher")) {
			this.data_sets.get(list_name).setPublisher(value);
		} else if (field_name.equals("editors")) {
			this.data_sets.get(list_name).setEditors(value);
		}
		
	}
	
	@Override
	public void updateField(String list_name, String field_name, int value) {
		super.updateField(list_name, field_name, value);
		
		if (field_name.equals("start_page")) {
			this.data_sets.get(list_name).setStartPage(value);
		} else if (field_name.equals("end_page")) {
			this.data_sets.get(list_name).setEndPage(value);
		}
		
	}
	
	@Override
	public String getPrintable(String list_name, String padding) {
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
	
	
}
