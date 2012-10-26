/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.entity;

import ca.mitmaro.lang.StringUtils;

public class JournalPaper extends Paper {
	
	private static class Data {
		private String paper_title;
		private int volume;
		private int start_page;
		private int end_page;
		
		protected Data(String paper_title, int volume, int start_page, int end_page) {
			this.paper_title = paper_title;
			this.volume = volume;
			this.start_page = start_page;
			this.end_page = end_page;
		}
		
		public void setVolume(int volume) {
			this.volume = volume;
		}
		
		public void setStartPage(int start_page) {
			this.start_page = start_page;
		}
		
		public void setEndPage(int end_page) {
			this.end_page = end_page;
		}
		
		protected void setPaperTitle(String title) {
			this.paper_title = title;
		}
		
		protected Data(Data data) {
			this.paper_title = data.paper_title;
			this.volume = data.volume;
			this.start_page = data.start_page;
			this.end_page = data.end_page;
		}
		
		protected String getPaperTitle() {
			return paper_title;
		}
		
		protected int getVolume() {
			return volume;
		}
		
		protected int getStartPage() {
			return start_page;
		}
		
		protected int getEndPage() {
			return end_page;
		}
		
	}
	
	private List<Data> data_sets; 
	
	public JournalPaper(String id, String type) {
		super(id, type);
		this.data_sets = new List<Data>();
	}
	
	public void setDataSet (
		String list_name, String journal_title, String paper_title,
		String author_first, String author_last, int year, int volume,
		int start_page, int end_page
	) {
		this.data_sets.put(list_name, new Data(paper_title, volume, start_page, end_page));
		super.setDataSet(list_name, journal_title, author_first, author_last, year);
	}
	
	public void addToList(String new_list, String old_list) {
		super.addToList(new_list, old_list);
		this.data_sets.put(new_list, new Data(this.data_sets.get(old_list)));
	}
	
	public String getPaperTitle(String list_name) {
		if (this.data_sets.has(list_name)) {
			return this.data_sets.get(list_name).getPaperTitle();
		}
		return null;
	}
	
	public int getVolume(String list_name) {
		if (this.data_sets.has(list_name)) {
			return this.data_sets.get(list_name).getVolume();
		}
		return 0;
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
		
		if (field_name.equals("paper_title")) {
			this.data_sets.get(list_name).setPaperTitle(value);
		}
		
	}
	
	@Override
	public void updateField(String list_name, String field_name, int value) {
		super.updateField(list_name, field_name, value);
		
		if (field_name.equals("volume")) {
			this.data_sets.get(list_name).setVolume(value);
		} else if (field_name.equals("start_page")) {
			this.data_sets.get(list_name).setStartPage(value);
		} else if (field_name.equals("end_page")) {
			this.data_sets.get(list_name).setEndPage(value);
		}
		
	}
	
	@Override
	public String getPrintable(String list_name, String padding) {
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
	
	
}
