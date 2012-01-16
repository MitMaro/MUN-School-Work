/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.entity;

public class Publication {
	
	private String id;
	
	private String title;
	
	public Author author;
	
	public Publication (String id) {
		this.id = id;
	}
	
	public Publication (String id, String title, Author author) {
		this.id = id;
		this.title = title;
		this.author = author;
	}
	
	public String getUniqueId() {
		return this.id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void updatePublication(String title, Author author) {
		this.title = title;
		this.author = author;
	}
	
}
