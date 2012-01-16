/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.entity;

import ca.mitmaro.ldb.entity.SoftReferenceList;

public class Volume {
	
	private int year;
	
	private int number;
	
	public final Journal journal;
	
	private SoftReferenceList<Paper> papers = new SoftReferenceList<Paper>();
	
	public Volume(Journal journal, int number, int year) {
		this.journal = journal;
		this.number = number;
		this.year = year;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public void addPaper(Paper paper) {
		this.papers.put(paper.getUniqueId(), paper);
	}
	
	public SoftReferenceList<Paper> getPapers() {
		return this.papers;
	}
	
	public int getPublicationYear() {
		return this.year;
	}
	
}
