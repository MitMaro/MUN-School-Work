/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb;


import ca.mitmaro.ldb.entity.Author;
import ca.mitmaro.ldb.entity.Paper;
import ca.mitmaro.ldb.entity.Journal;
import ca.mitmaro.ldb.entity.Volume;

public class EntityManager {
	
	public Author createAuthor(String firstname, String lastname) {
		return new Author(firstname, lastname);
	}
	
	public Paper createPaper(String pid, String title, int start_page, int end_page, Volume volume, Author author) {
		Paper paper = new Paper(pid, title, author, volume, start_page, end_page);
		
		if (author != null) {
			author.addPublication(paper);
		}
		if (volume != null) {
			volume.addPaper(paper);
		}
		return paper;
	}
	
	public Journal createJournal(String title) {
		return new Journal(title);
	}
	
	public Volume createVolume(Journal journal, int number, int year) {
		Volume volume = new Volume(journal, number, year);
		journal.addVolume(volume);
		return volume;
	}
	
	public void updatePaper(Paper paper, String title, Author author, Volume volume, int page_start, int page_end) {
		paper.updatePaper(title, author, volume, page_start, page_end);
		author.addPublication(paper);
		volume.addPaper(paper);
	}
	
}
