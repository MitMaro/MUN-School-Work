/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

import ca.mitmaro.ldb.entity.Book;
import ca.mitmaro.ldb.entity.BookChapter;
import ca.mitmaro.ldb.entity.ConferencePaper;
import ca.mitmaro.ldb.entity.JournalPaper;
import ca.mitmaro.ldb.entity.PHDThesis;
import ca.mitmaro.ldb.entity.Paper;
import ca.mitmaro.ldb.entity.List;
import ca.mitmaro.ldb.entity.SoftReferenceList;
import ca.mitmaro.ldb.exception.InvalidListException;
import ca.mitmaro.ldb.exception.MissingPaperException;

public class Application {
	
	private static enum InputTag {PID, ADD, AUT, BED, BNP, BTL, JNP, JNT, JNV, PTY, PUB, PYR, TLE, END}
	
	private CommandLineInterface intrface; // can be substituted later for other interfaces
	
	
	// global lists
	private List<Paper> papers = new List<Paper>();
	private List<Paper> references = new List<Paper>();
	
	private Paper current_working_paper;

	// the key of the master list
	private final String master_list_name = "_ _ _M A S T E R_ _ _";
	
	// the "master" list of paper
	private SoftReferenceList<Paper> master_paper_list = new SoftReferenceList<Paper>();
	// the "master" list of references
	private SoftReferenceList<Paper> master_references_list = new SoftReferenceList<Paper>();
	
	// the named lists of paper
	private LinkedHashMap<String, SoftReferenceList<Paper>> file_paper_lists = new LinkedHashMap<String, SoftReferenceList<Paper>>();
	// the named lists of references
	private LinkedHashMap<String, SoftReferenceList<Paper>> file_references_lists = new LinkedHashMap<String, SoftReferenceList<Paper>>();
	
	public void setInterface (CommandLineInterface intf) {
		this.intrface = intf;
	}
	
	public CommandLineInterface getInterface() {
		return this.intrface;
	}
	
	public Paper getPaper(String pid) {
		return this.papers.get(pid);
	}
	
	public void loadPaperDescriptionListFile(String filepath, String name) throws IOException {
		
		BufferedReader in = new BufferedReader(new FileReader(filepath));
		
		String line = "";
		String[] tmp;
		InputTag input_tag;
		
		// load the file paper list, if one exists. if not create it
		SoftReferenceList<Paper> file_paper = this.file_paper_lists.get(name);
		if (file_paper == null) {
			file_paper = new SoftReferenceList<Paper>();
			this.file_paper_lists.put(name, file_paper);
		}
		

		// possible fields
		String pid = "";
		int year = 0;
		String author_first = "";
		String author_last = "";
		String title = "";
		String book_title = "";
		String publisher = "";
		String address = "";
		String editor = "";
		String type = "";
		int volume_number = 0;
		int page_start = 0;
		int page_end = 0;
		
		Paper paper = null;
		
		while ((line = in.readLine()) != null) {
			
			// skip empty lines
			if (line.trim().equals("")) {
				continue;
			}
			
			tmp = line.split("\\s+", 2);
			
			// convert tag to enum for switch
			try {
				input_tag = InputTag.valueOf(tmp[0].trim().toUpperCase());
			} catch (IllegalArgumentException e) {
				System.err.format("Invalid input tag: %s\n", tmp[0]);
				return;
			}
			
			switch (input_tag) {
				case PID:
					pid = tmp[1].trim();
					break;
				case ADD:
					address = tmp[1].trim();
					break;
				case AUT:
					tmp = tmp[1].split(",", 2);
					author_first = tmp[1].trim();
					author_last = tmp[0].trim();
					break;
				case BED:
					editor = tmp[1].trim();
					break;
				case BNP:
				case JNP:
					if (tmp[1].contains("-")) {
						try {
							tmp = tmp[1].split("-", 2);
							page_start = Integer.parseInt(tmp[0]);
							page_end = Integer.parseInt(tmp[1]);
						} catch (NumberFormatException e) {
							// pass - we are allowed to assume correctly formatted files
							throw new AssertionError("This should not have happened. Input: "  +  tmp[0] + "-" + tmp[1]);
						}
					} else {
						try {
							page_start = page_end = Integer.parseInt(tmp[1]);
						} catch (NumberFormatException e) {
							// pass - we are allowed to assume correctly formatted files
							throw new AssertionError("This should not have happened. Input: "  +  tmp[0]);
						}
					}
					break;
				case BTL:
				case JNT:
					book_title = tmp[1].trim();
					break;
				case JNV:
					try {
						volume_number = Integer.parseInt(tmp[1]);
					} catch (NumberFormatException e) {
						// pass - we are allowed to assume correctly formatted files
						throw new AssertionError("This should not have happened. Input: "  +  tmp[1]);
					}
					break;
				case PTY:
					type = tmp[1].trim().toLowerCase();
					break;
				case PUB:
					publisher = tmp[1].trim();
					break;
				case PYR:
					try {
						year = Integer.parseInt(tmp[1].trim());
					} catch (NumberFormatException e) {
						// pass - we are allowed to assume correctly formatted files
						throw new AssertionError("This should not have happened. Input: "  +  tmp[1]);
					}
					break;
				case TLE:
					title = tmp[1].trim();
					break;
				case END:
					
					// see if paper has been loaded previously
					paper = this.papers.get(pid);
					// does not yet exist
					if (paper == null) {
						paper = this.createPaper(pid, type);
						this.papers.put(pid, paper);
					}
					
					// update paper for this list
					file_paper.put(pid, paper);
					this.updatePaper(
						paper, name, year, author_first, author_last, title, book_title,
						publisher, address, editor, type, volume_number, page_start,
						page_end
					);
					
					// reset values to be safe
					pid = "";
					year = 0;
					author_first = "";
					author_last = "";
					title = "";
					book_title = "";
					publisher = "";
					address = "";
					editor = "";
					type = "";
					volume_number = 0;
					page_start = 0;
					page_end = 0;
					break;
			}
			
		}
	}
	
	private Paper createPaper(String pid, String type) {
		if (type.equals("book")) {
			return new Book(pid, "book");
		} else if (type.equals("phd thesis")) {
			return new PHDThesis(pid, "phd thesis");
		} else if (type.equals("book chapter")) {
			return new BookChapter(pid, "book chapter");
		} else if (type.equals("conference paper")) {
			return new ConferencePaper(pid, "conference paper");
		} else if (type.equals("journal paper")) {
			return new JournalPaper(pid, "journal paper");
		}
		// we are allowed to assume correctly formatted files
		throw new AssertionError("This should not have happened.");
	}
	
	public void updatePaper(
		Paper paper, String list_name, int year, String author_first, String author_last,
		String title, String book_title, String publisher, String address, String editors,
		String type, int volume, int page_start, int page_end
	) {
		if (type.equals("book")) {
			((Book)paper).setDataSet(
				list_name, book_title, author_first, author_last,
				year, address, publisher
			);
		} else if (type.equals("phd thesis")) {
			((PHDThesis)paper).setDataSet(
				list_name, book_title, author_first, author_last,
				year, address
			);
		} else if (type.equals("book chapter")) {
			((BookChapter)paper).setDataSet(
				list_name, book_title, author_first, author_last, year,
				address, editors, publisher, title, page_start, page_end
			);
		} else if (type.equals("conference paper")) {
			((ConferencePaper)paper).setDataSet(
				list_name, book_title, author_first, author_last, year,
				address, editors, publisher, title, page_start, page_end
			);
		} else if (type.equals("journal paper")) {
			((JournalPaper)paper).setDataSet(
				list_name, book_title, title, author_first,
				author_last, year, volume, page_start, page_end
			);
		}
	}
	
	public void updatePaper(String list_name, String pid, String field_name, String value) {
		if (list_name == null) {
			list_name = this.master_list_name;
		}
		this.papers.get(pid).updateField(list_name, field_name, value);
	}
	
	public void updatePaper(String list_name, String pid, String field_name, int value) {
		if (list_name == null) {
			list_name = this.master_list_name;
		}
		this.papers.get(pid).updateField(list_name, field_name, value);
	}
		
	public void loadPaperReferenceListFile(String filepath, String name) throws IOException, MissingPaperException {
		
		BufferedReader in = new BufferedReader(new FileReader(filepath));
		
		String line;
		String[] tmp;
		Paper paper;
		Paper reference;
		
		// load the file reference list, if one exists. if not create it
		SoftReferenceList<Paper> file_references = this.file_references_lists.get(name);
		if (file_references == null) {
			file_references = new SoftReferenceList<Paper>();
			this.file_references_lists.put(name, file_references);
		}
		
		while ((line = in.readLine()) != null) {
			
			// skip empty lines
			if (line.trim().equals("")) {
				continue;
			}
			
			// spit paper and references
			tmp = line.split(":", 2);
			
			// see if paper has been loaded previously
			paper = this.papers.get(tmp[0]);
			// does not yet exist
			if (paper == null) {
				throw new MissingPaperException(String.format("The paper with id, %s, does not exist.", tmp[0]));
			}
			
			// split references
			tmp = tmp[1].split("\\s+");
			
			for (String ref: tmp) {
				// skip empty
				if (ref.trim().equals("")) {
					continue;
				}
				reference = this.papers.get(ref);
				if (reference == null) {
					throw new MissingPaperException(String.format("The paper with id, %s, does not exist.", ref));
				}
				file_references.put(name, reference);
				reference.addCitation(paper);
				paper.addReference(reference);
			}
		}
	}
	
	public void setCurrentWorkingPaper(String id) {
		
		int index;
		int i = 1;
		
		if (id.startsWith("P")) {
			this.current_working_paper = this.papers.get(id);
		} else if (id.startsWith("R") || id.startsWith("C")) {
			
			List<Paper> papers;
			
			if (id.startsWith("R")) {
				papers = this.current_working_paper.getReferences();
			} else {
				papers = this.current_working_paper.getCitations();
			}
			
			try {
				index = Integer.parseInt(id.substring(1));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Provided id, " + id + ", is invalid.");
			}
			
			// to catch 0 indexed and make it 1
			if (index == 0) {
				index = 1;
			}
			// get reference by index, kinda sucks doing it this way but c'est la vie
			for (Paper paper: papers) {
				if (index == i) {
					this.current_working_paper = paper;
					break;
				}
				i++;
			}
		}
		System.out.println("Current working paper set to: " + this.current_working_paper.getId());
	}
	
	public void addPapersToMasterList(String name) {
		Paper paper;
		for (String k: this.file_paper_lists.get(name).keySet()) {
			paper = this.papers.get(k);
			paper.addToList(this.master_list_name, name);
			this.master_paper_list.put(k, paper);
		}
	}
	
	public void addReferencesToMasterList(String name) {
		for (String k: this.file_references_lists.get(name).keySet()) {
			this.master_references_list.put(k, this.references.get(k));
		}
	}
	
	public void clearMasterPaperList() {
		this.master_paper_list.clear();
	}
	
	public void clearMasterReferenceList() {
		this.master_references_list.clear();
	}
	
	public void printPaperDescriptionLists() {
		int i = 1;
		for (String list_name: this.file_paper_lists.keySet()) {
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
		for (Paper paper: this.master_paper_list) {
			this.printPaper(paper, this.master_list_name, references);
			System.out.println();
		}
	}
	
	public void printPaperList(String name) {
		this.printPaperList(name, false);
	}
	
	public void printPaperList(String name, boolean references) {
		for (Paper paper: this.file_paper_lists.get(name)) {
			this.printPaper(paper, name, references);
			System.out.println();
		}
	}
	
	public void printCurrentWorkingPaper() {
		if (this.current_working_paper != null) {
			this.printPaper(this.current_working_paper, this.master_list_name, true);
		}
	}
	
	private void printPaper(Paper paper, String list_name, boolean include_refs_cits) {
		
		System.out.println(paper.getPrintable(list_name, ""));
		if (include_refs_cits) {

			if (paper.getReferences().isEmpty()) {
				System.out.println("\n\tNO REFERENCES\n");
				
			} else {
				System.out.println("\n\tREFERENCES");
				
				int index = 0;
			
				for (Paper reference: paper.getReferences()) {
					System.out.format("\tR%d: ", index);
					try {
						System.out.println(reference.getPrintable(list_name, "\t"));
					} catch (InvalidListException e) {
						try {
							System.out.println(reference.getPrintable(this.master_list_name, "\t"));
						} catch (InvalidListException e2) {
							System.out.format("%s doesn't exist in list master list\n\n", reference.getId());
						}
					}
					index++;
				}
			}
			
			if (paper.getReferences().isEmpty()) {
				System.out.println("\n\tNO CITATIONS\n");
				
			} else {
				System.out.println("\n\tCITATIONS");
				
				int index = 0;
			
				for (Paper citation: paper.getCitations()) {
					System.out.format("\tC%d: ", index);
					try {
						System.out.println(citation.getPrintable(list_name, "\t"));
					} catch (InvalidListException e) {
						try {
							System.out.println(citation.getPrintable(this.master_list_name, "\t"));
						} catch (InvalidListException e2) {
							System.out.format("%s doesn't exist in list master list\n\n", citation.getId());
						}
					}
					index++;
				}
			}
		}
		
	}
	
}
