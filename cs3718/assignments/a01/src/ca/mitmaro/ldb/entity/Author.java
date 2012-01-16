/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.entity;

public class Author {
	
	protected String firstname;
	
	protected String lastname;
	
	protected SoftReferenceList<Publication> publications = new SoftReferenceList<Publication>();
	
	public Author(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public String getFirstname() {
		return this.firstname;
	}
	
	public String getLastname() {
		return this.lastname;
	}
	
	public void addPublication(Paper paper) {
		this.publications.put(paper.getUniqueId(), paper);
	}
	
	public SoftReferenceList<Publication> getPublications() {
		return this.publications;
	}
	
}
