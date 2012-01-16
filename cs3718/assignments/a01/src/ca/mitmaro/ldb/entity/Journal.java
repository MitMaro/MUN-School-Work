/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.entity;

import ca.mitmaro.ldb.entity.SoftReferenceList;

public class Journal {

	private String title;
	
	private SoftReferenceList<Volume> volumes = new SoftReferenceList<Volume>();
	
	public Journal(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void addVolume(Volume volume) {
		this.volumes.put(Integer.toString(volume.getPublicationYear()), volume);
	}
	
	public SoftReferenceList<Volume> getVolumes() {
		return this.volumes;
	}
	
}
