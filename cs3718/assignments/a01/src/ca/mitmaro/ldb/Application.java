/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import ca.mitmaro.ldb.entity.Author;
import ca.mitmaro.ldb.entity.Publication;
import ca.mitmaro.ldb.entity.List;
import ca.mitmaro.ldb.entity.SoftReferenceList;
import ca.mitmaro.ldb.entity.Paper;
import ca.mitmaro.ldb.entity.Journal;
import ca.mitmaro.ldb.entity.Volume;
import ca.mitmaro.ldb.lang.StringUtils;

public class Application {
	
	private CommandLineInterface intrface; // can be substituted later for other interfaces
	
	private EntityManager entity_manager;
	
	// global lists
	private List<Publication> publications = new List<Publication>();
	private List<Publication> references = new List<Publication>();
	private List<Author> authors = new List<Author>();
	private List<Journal> journals = new List<Journal>();
	private List<Volume> volumes = new List<Volume>();
	
	private Publication current_working_publication;
	
	// the "master" list of publications
	private SoftReferenceList<Publication> master_publications_list = new SoftReferenceList<Publication>();
	// the "master" list of references
	private SoftReferenceList<Publication> master_references_list = new SoftReferenceList<Publication>();
	
	// the named lists of publications
	private LinkedHashMap<String, SoftReferenceList<Publication>> file_publications_lists = new LinkedHashMap<String, SoftReferenceList<Publication>>();
	// the named lists of references
	private LinkedHashMap<String, SoftReferenceList<Publication>> file_references_lists = new LinkedHashMap<String, SoftReferenceList<Publication>>();
	
	public Application(EntityManager em) {
		this.entity_manager = em;
	}
	
	public void setInterface (CommandLineInterface intf) {
		this.intrface = intf;
	}
	
	public CommandLineInterface getInterface() {
		return this.intrface;
	}
	
	public void loadPaperDescriptionListFile(String filepath, String name) throws IOException {
		
		BufferedReader in = new BufferedReader(new FileReader(filepath));;
		
		String line = "";
		String[] tmp;
		
		Paper paper = null;
		String pid = "";
		int year = 0;
		Author author = null;
		String title = "";
		Journal journal = null;
		Volume volume = null;
		int volume_number = 0;
		int page_start = 0;
		int page_end = 0;
		
		// load the file publications list, if one exists. if not create it
		SoftReferenceList<Publication> file_publications = this.file_publications_lists.get(name);
		if (file_publications == null) {
			file_publications = new SoftReferenceList<Publication>();
			this.file_publications_lists.put(name, file_publications);
		}
		
		while ((line = in.readLine()) != null) {
			
			tmp = line.split("\\s+", 2);
			
			if (tmp[0].equals("PID")) {
				pid = tmp[1];
			}
			else if (tmp[0].equals("AUT")) {
				tmp = tmp[1].split(",", 2);
				author = this.getOnMissingAddAuthor(tmp[0].trim(), tmp[1].trim());
			}
			else if (tmp[0].equals("PYR")) {
				try {
					year = Integer.parseInt(tmp[1]);
				} catch (NumberFormatException e) {
					// pass - we are allowed to assume correctly formatted files
				}
			}
			else if (tmp[0].equals("TLE")) {
				title = tmp[1];
			}
			else if (tmp[0].equals("JNT")) {
				journal = this.getOnMissingAddJournal(tmp[1].trim());
			}
			else if (tmp[0].equals("JNV")) {
				try {
					volume_number = Integer.parseInt(tmp[1]);
				} catch (NumberFormatException e) {
					// pass - we are allowed to assume correctly formatted files
				}
			}
			else if (tmp[0].equals("JNP")) {
				if (tmp[1].contains("-")) {
					try {
						tmp = tmp[1].split("-", 2);
						page_start = Integer.parseInt(tmp[0]);
						page_end = Integer.parseInt(tmp[1]);
					} catch (NumberFormatException e) {
						// pass - we are allowed to assume correctly formatted files
					}
				} else {
					try {
						page_start = page_end = Integer.parseInt(tmp[0]);
					} catch (NumberFormatException e) {
						// pass - we are allowed to assume correctly formatted files
					}
				}
			}
			else if (tmp[0].equals("END")) {
				
				volume = this.getOnMissingAddVolume(journal, volume_number, year);
				
				
				if ((paper = (Paper)this.publications.get(pid)) != null) {
					this.entity_manager.updatePaper(paper, title, author, volume, page_start, page_end);
				} else {
					// update paper if it already exists, could be a bare paper
					paper = this.getOnMissingAddPaper(pid, title, page_start, page_end, volume, author);
				}
				
				file_publications.put(pid, paper);
				
				// reset values to be safe
				paper = null;
				pid = "";
				author = null;
				year = 0;
				title = "";
				journal = null;
				volume = null;
				volume_number = 0;
				page_start = 0;
				page_end = 0;
			}
			
		}
	}
	
	
	public void loadPaperReferenceListFile(String filepath, String name) throws IOException {
		
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new FileReader(filepath));
		} catch (FileNotFoundException e) {
			// should never reach here, but it is possible
			System.out.println("Provided file not found: " + filepath);
			System.exit(3);
		}
		String line;
		String[] tmp;
		Paper paper;
		Paper reference;
		
		// load the file reference list, if one exists. if not create it
		SoftReferenceList<Publication> file_references = this.file_references_lists.get(name);
		if (file_references == null) {
			file_references = new SoftReferenceList<Publication>();
			this.file_references_lists.put(name, file_references);
		}
		
		while ((line = in.readLine()) != null) {
			
			// spit paper and references
			tmp = line.split(":", 2);
			paper = (Paper)this.getOnMissingAddPaper(tmp[0].trim());
			
			// split references
			tmp = tmp[1].split("\\s+");
			
			for (String ref: tmp) {
				reference = this.getOnMissingAddPaper(ref);
				file_references.put(name, reference);
				paper.addReference(reference);
			}
			
		}
		
	}
	
	public void setCurrentWorkingPaper(String id) {
		
		int index;
		int i = 1;
		
		if (id.startsWith("P")) {
			this.current_working_publication = this.publications.get(id);
		} else if (id.startsWith("R")) {
			
			try {
				index = Integer.parseInt(id.substring(1));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Provided id, " + id + ", is invalid.");
			}
			
			// get reference by index, kinda sucks doing it this way but c'est la vie
			for (Paper paper: ((Paper)this.current_working_publication).getReferences()) {
				
				if (index == i) {
					this.current_working_publication = paper;
					break;
				}
				i++;
			}
		}
		System.out.println("Current working paper set to: " + this.current_working_publication.getTitle());
	}
	
	public void addPapersToMasterList(String name) {
		for (String k: this.file_publications_lists.get(name).keySet()) {
			this.master_publications_list.put(k, this.publications.get(k));
		}
	}
	
	public void addReferencesToMasterList(String name) {
		for (String k: this.file_references_lists.get(name).keySet()) {
			this.master_references_list.put(k, this.references.get(k));
		}
	}
	
	public void printPaperDescriptionLists() {
		int i = 1;
		for (String list_name: this.file_publications_lists.keySet()) {
			System.out.print("\t");
			System.out.print(i);
			System.out.println(") " + list_name);
			i++;
		}
	}
	
	public void printPaperReferenceLists() {
		int i = 1;
		for (String list_name: this.file_references_lists.keySet()) {
			System.out.print("\t");
			System.out.print(i);
			System.out.println(") " + list_name);
			i++;
		}
	}
	
	public void printMasterPaperList() {
		this.printMasterPaperList(false);
	}
	
	public void printMasterPaperList(boolean references) {
		for (Publication pub: this.master_publications_list) {
			this.printPaper((Paper)pub, references);
			System.out.println();
		}
	}
	
	public void printPaperList(String name) {
		this.printPaperList(name, false);
	}
	
	public void printPaperList(String name, boolean references) {
		for (Publication pub: this.file_publications_lists.get(name)) {
			this.printPaper((Paper)pub, references);
			System.out.println();
		}
	}
	
	public void printCurrentWorkingPaper() {
		this.printPaper((Paper)this.current_working_publication, true);
	}
	
	private void printPaper(Paper paper) {
		this.printPaper(paper, false);
	}
	
	private void printPaper(Paper paper, boolean references) {
		
		int index = 0;
		
		System.out.printf(
			"%s: %s, %s (%d) %s\n\t %s, %d, $s.\n",
			paper.getUniqueId(),
			paper.author.getLastname(),
			paper.author.getFirstname(),
			paper.volume.getPublicationYear(),
			StringUtils.truncate(paper.getTitle(), 35),
			paper.volume.journal.getTitle(),
			paper.volume.getNumber(),
			paper.getPages()
		);
		
		if (references) {
			
			if (paper.getReferences().length() == 0) {
				System.out.println("\n\tNO REFERENCES\n");
				
			} else {
				System.out.println("\n\tREFERENCES");
			
				for (Paper reference: paper.getReferences()) {
					System.out.printf(
						"\n\tR%d: %s: %s, %s (%d) %s\n\t\t%s, %d, $s.\n",
						index,
						paper.getUniqueId(),
						paper.author.getLastname(),
						paper.author.getFirstname(),
						paper.volume.getPublicationYear(),
						StringUtils.truncate(paper.getTitle(), 35),
						paper.volume.journal.getTitle(),
						paper.volume.getNumber(),
						paper.getPages()
					);
					index++;
				}
			}
		}
	}
	
	private Paper getOnMissingAddPaper(String pid) {
		return this.getOnMissingAddPaper(pid, null, 0, 0, null, null);
	}
	
	private Paper getOnMissingAddPaper(String pid, String title, int page_start, int page_end, Volume volume, Author author) {
		Paper paper = (Paper)this.publications.get(pid);
		if (paper == null) {
			paper = this.entity_manager.createPaper(pid, title, page_start, page_end, volume, author);
			this.publications.put(pid, paper);
		}
		return paper;
	}
	
	private Author getOnMissingAddAuthor(String firstname, String lastname) {
		Author author = this.authors.get(firstname + lastname);
		if (author == null) {
			author = this.entity_manager.createAuthor(firstname, lastname);
			this.authors.put(firstname + lastname, author);
		}
		return author;
	}
	
	private Journal getOnMissingAddJournal(String title) {
		Journal journal = this.journals.get(title);
		if (journal == null) {
			journal = this.entity_manager.createJournal(title);
			this.journals.put(title, journal);
		}
		return journal;
	}
	
	private Volume getOnMissingAddVolume(Journal journal, int number, int year) {
		Volume volume = this.volumes.get(journal.getTitle() + number);
		if (volume == null) {
			volume = this.entity_manager.createVolume(journal, number, year);
			this.volumes.put(journal.getTitle() + number, volume);
		}
		return volume;
	}
}
