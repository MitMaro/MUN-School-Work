/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.entity;

import ca.mitmaro.ldb.entity.SoftReferenceList;

public class Paper extends Publication {
	
	private int page_start;
	
	private int page_end;
	
	private SoftReferenceList<Paper> references = new SoftReferenceList<Paper>();
	
	public Volume volume;
	
	public Paper(String pid) {
		super(pid);
	}
	
	public Paper(String pid, String title, Author author, Volume volume, int start_page, int end_page) {
		super(pid, title, author);
		this.page_start = start_page;
		this.page_end = end_page;
		this.volume = volume;
	}
	
	public void addReference(Paper paper) {
		this.references.put(paper.getUniqueId(), paper);
	}
	
	public SoftReferenceList<Paper> getReferences() {
		return this.references;
	}
	
	public int getPublicationYear() {
		return this.volume.getPublicationYear();
	}
	
	public String getPages() {
		
		// single page
		if (this.page_start == this.page_end) {
			return Integer.toString(this.page_start);
		}
		
		return this.page_end + " - " + this.page_start;
	}
	
	public void updatePaper(String title, Author author, Volume volume, int page_start, int page_end) {
		super.updatePublication(title, author);
		this.volume = volume;
		this.page_start = page_start;
		this.page_end = page_end;
	}
	
}
