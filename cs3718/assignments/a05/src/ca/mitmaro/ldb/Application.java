/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.io.FileUtil;
import ca.mitmaro.ldb.entity.Book;
import ca.mitmaro.ldb.entity.BookChapter;
import ca.mitmaro.ldb.entity.ConferencePaper;
import ca.mitmaro.ldb.entity.JournalPaper;
import ca.mitmaro.ldb.entity.PHDThesis;
import ca.mitmaro.ldb.entity.Paper;
import ca.mitmaro.ldb.entity.PaperList;
import ca.mitmaro.ldb.entity.PaperList.SortOrder;
import ca.mitmaro.ldb.entity.UpdateContext;
import ca.mitmaro.ldb.exception.InvalidListException;
import ca.mitmaro.ldb.exception.MissingPaperException;

/**
 * The main application class. Contains various methods for manipulating lists of papers
 *
 * @author Tim Oram (MitMaro)
 */
public class Application {
	/**
	 * The input file tag operations
	 */
	private static enum InputTag {PID, ADD, AUT, BED, BNP, BTL, JNP, JNT, JNV, PTY, PUB, PYR, TLE, END}
	
	/**
	 * The command line interface for this application
	 */
	private CommandLineInterface intrface; // can be substituted later for other interfaces
	
	/**
	 * An instance of the file util class
	 */
	private FileUtil file_util;
	
	private GraphicalUserInterface gui = null;
	
	/**
	 * The terminal interface
	 */
	private Terminal terminal;
	
	/**
	 * The global list of papers
	 */
	private LinkedHashSet<Paper> papers;
	
	/**
	 * The current working paper
	 */
	private Paper current_working_paper;

	/**
	 * The key of the master list
	 */
	private PaperList master_list;
	
	/**
	 * The paper lists
	 */
	private LinkedHashSet<PaperList> file_paper_lists;
	
	/**
	 * The named list of references
	 */
	private LinkedHashMap<String, LinkedHashSet<Paper>> file_references_lists;
	
	
	public void run(boolean set_visible) {
		if (this.gui == null) {
			this.gui = new GraphicalUserInterface(this);
		}
		this.gui.run(set_visible);
	}
	
	/**
	 * Constructs the application class
	 * 
	 * @param file_manager The file util file mananger
	 * @param terminal A terminal interface
	 */
	public Application(FileUtil file_manager, Terminal terminal) {
		this.file_util = file_manager;
		this.terminal = terminal;
		
		File f = new File("./data/papers.lst");
		
		if (f.isFile()) {
			try {
				this.readState();
				return;
			} catch (IOException e) {
				this.terminal.err().println("IO Error Reading Saved Data");
				this.terminal.err().println(e.getMessage());
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				this.terminal.err().println("Class Missing While Reading Saved Data");
				this.terminal.err().println(e.getMessage());
			}
		}
		
		// load empty lists on first load/error
		this.papers = new LinkedHashSet<Paper>();
		this.file_paper_lists = new LinkedHashSet<PaperList>();
		this.file_references_lists = new LinkedHashMap<String, LinkedHashSet<Paper>>();
		this.master_list = new PaperList("_ _ _M A S T E R_ _ _");
	}
	
	/**
	 * Add paper to the master paper list
	 * 
	 * @param list_name The list name to add
	 * @throws InvalidListException If list name is invalid
	 */
	public void addPapersToMasterList(final String list_name) throws InvalidListException {
		
		if (!this.file_paper_lists.contains(new PaperList(list_name))) {
			System.out.println("here");
			throw new InvalidListException(list_name);
		}
		
		for (Paper paper: this.papers) {
			if (paper.inList(list_name)) {
				paper.addToList(this.getMasterListName(), list_name);
			}
		}
		
	}
	
	/**
	 * Clear the master paper list
	 */
	public void clearMasterPaperList() {
		for (Paper pap: this.papers) {
			pap.removeList(this.getMasterListName());
		}
	}
	
	/**
	 * Create a new paper from a paper id and a type
	 * @param pid The paper id
	 * @param type The type of paper
	 * @return A paper object
	 */
	private Paper createPaper(final String pid, final String type) {
		if (type.equals("book")) {
			return new Book(pid);
		} else if (type.equals("phd thesis")) {
			return new PHDThesis(pid);
		} else if (type.equals("book chapter")) {
			return new BookChapter(pid);
		} else if (type.equals("conference paper")) {
			return new ConferencePaper(pid);
		} else if (type.equals("journal paper")) {
			return new JournalPaper(pid);
		}
		// we are allowed to assume correctly formatted files
		throw new AssertionError("This should not have happened.");
	}
	
	/**
	 * @return the current working paper
	 */
	public Paper getCurrentWorkingPaper() {
		return this.current_working_paper;
	}
	
	/**
	 * @return the paper file list names
	 */
	public ArrayList<String> getFileListNames() {
		
		ArrayList<String> names = new ArrayList<String>();
		
		for (PaperList pap: this.file_paper_lists) {
			names.add(pap.getListName());
		}
		
		return names;
	}
	
	/**
	 * @return the reference file list names
	 */
	public Set<String> getFileReferenceListNames() {
		return this.file_references_lists.keySet();
	}
	
	/**
	 * @return the user interface
	 */
	public CommandLineInterface getInterface() {
		return this.intrface;
	}
	
	/**
	 * @return the master list name
	 */
	public String getMasterListName() {
		return this.master_list.getListName();
	}
	
	/**
	 * @return the list of papers in the master list
	 */
	public ArrayList<Paper> getMasterListPapers() {
		return this.getPapers(this.master_list);
	}
	
	/**
	 * Get a list of papers from a paper list
	 * @param list_name The name of the list
	 * @return A list of papers
	 * @throws InvalidListException If list doesn't exist
	 */
	public ArrayList<Paper> getPaperList(String list_name) throws InvalidListException {
		
		if (list_name.equals(this.getMasterListName())) {
			return this.getPapers(this.master_list);
		}
		
		for (PaperList pap: this.file_paper_lists) {
			if (pap.getListName().equals(list_name)) {
				return this.getPapers(pap);
			}
		}
		
		throw new InvalidListException(list_name);
	}
	
	private ArrayList<Paper> getPapers(PaperList pap_list) {
		ArrayList<Paper> papers = new ArrayList<Paper>();
		
		for (Paper pap: this.papers) {
			if (pap.inList(pap_list.getListName())) {
				papers.add(pap);
			}
		}
		
		switch (pap_list.getSortOrder()) {
			case AUTHOR:
				Collections.sort(papers, new PaperCompareAuthor(pap_list.getListName()));
				break;
			case NATURAL:
				Collections.sort(papers, new PaperComparePID());
				break;
		}
		
		return papers;
		
	}

	public ArrayList<Paper> getFullPapersList() {
		return new ArrayList<Paper>(this.papers);
	}
	
	/**
	 * Get a paper from a paper id
	 * 
	 * @param pid The paper id
	 * @return a paper instance
	 * @throws MissingPaperException if paper doens't exist
	 */
	public Paper getPaper(final String pid) throws MissingPaperException {
		for (Paper pap: this.papers) {
			if (pap.getId().equals(pid)) {
				return pap;
			}
		}
		throw new MissingPaperException(pid);
	}
	
	/**
	 * Loads a paper description list, parses it and adds papers
	 * @param file The file object pointing to the list
	 * @param list_name A name of the list
	 * @throws IOException
	 */
	public void loadPaperDescriptionListFile(final File file, final String list_name) throws IOException {
		
		BufferedReader in = this.file_util.getBufferedReader(file);
		
		String line = "";
		String[] tmp;
		InputTag input_tag;
		
		this.file_paper_lists.add(new PaperList(list_name));
		
		// possible fields
		String pid = "";
		String type = "";
		Paper paper = null;
		UpdateContext context = new UpdateContext();
		
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
				this.terminal.err().format("Invalid input tag: %s\n", tmp[0]);
				return;
			}
			
			switch (input_tag) {
				case PID:
					pid = tmp[1].trim();
					break;
				case ADD:
					context.address = tmp[1].trim();
					break;
				case AUT:
					tmp = tmp[1].split(",", 2);
					context.author_first = tmp[1].trim();
					context.author_last = tmp[0].trim();
					break;
				case BED:
					context.editors = tmp[1].trim();
					break;
				case BNP:
				case JNP:
					if (tmp[1].contains("-")) {
						try {
							tmp = tmp[1].split("-", 2);
							context.start_page = Integer.parseInt(tmp[0]);
							context.end_page = Integer.parseInt(tmp[1]);
						} catch (NumberFormatException e) {
							// pass - we are allowed to assume correctly formatted files
							throw new AssertionError("This should not have happened. Input: "  +  tmp[0] + "-" + tmp[1]);
						}
					} else {
						try {
							context.start_page = context.end_page = Integer.parseInt(tmp[1]);
						} catch (NumberFormatException e) {
							// pass - we are allowed to assume correctly formatted files
							throw new AssertionError("This should not have happened. Input: "  +  tmp[0]);
						}
					}
					break;
				case BTL:
					context.book_title = tmp[1].trim();
					break;
				case JNT:
					context.journal_title = tmp[1].trim();
					break;
				case JNV:
					try {
						context.volume = Integer.parseInt(tmp[1]);
					} catch (NumberFormatException e) {
						// pass - we are allowed to assume correctly formatted files
						throw new AssertionError("This should not have happened. Input: "  +  tmp[1]);
					}
					break;
				case PTY:
					type = tmp[1].trim().toLowerCase();
					break;
				case PUB:
					context.publisher = tmp[1].trim();
					break;
				case PYR:
					try {
						context.year = Integer.parseInt(tmp[1].trim());
					} catch (NumberFormatException e) {
						// pass - we are allowed to assume correctly formatted files
						throw new AssertionError("This should not have happened. Input: "  +  tmp[1]);
					}
					break;
				case TLE:
					context.title = tmp[1].trim();
					context.paper_title = tmp[1].trim();
					context.chapter_title = tmp[1].trim();
					break;
				case END:
					
					// see if paper has been loaded previously
					try {
						paper = this.getPaper(pid);
					} catch (MissingPaperException e) { // does not yet exist
						paper = this.createPaper(pid, type);
						this.papers.add(paper);
					}
					
					paper.update(list_name, context);
					
					// reset values to be safe
					pid = "";
					type = "";
					context = new UpdateContext();
					break;
			}
			
		}
	}
	
	public void createList(String list_name) {
		this.file_paper_lists.add(new PaperList(list_name));
	}

	public void deleteList(String list_name) {
		this.file_paper_lists.remove(new PaperList(list_name));
		
		for (Paper paper: this.papers) {
			paper.removeList(list_name);
		}
		
	}
	
	public void removePaperFromList(Paper paper, String list_name) {
		paper.removeList(list_name);
	}
	
	/**
	 * Load a paper reference list
	 * 
	 * @param file A file instance to the reference file
	 * @param list_name The name of the reference list
	 * @throws IOException
	 * @throws MissingPaperException If a paper in the reference map doesn't exist
	 */
	public void loadPaperReferenceListFile(final File file, final String list_name) throws IOException, MissingPaperException {
		
		BufferedReader in = this.file_util.getBufferedReader(file);
		
		String line;
		String[] tmp;
		Paper paper;
		Paper reference;
		
		// load the file reference list, if one exists. if not create it
		LinkedHashSet<Paper> file_references = this.file_references_lists.get(list_name);
		if (file_references == null) {
			file_references = new LinkedHashSet<Paper>();
			this.file_references_lists.put(list_name, file_references);
		}
		
		while ((line = in.readLine()) != null) {
			
			// skip empty lines
			if (line.trim().equals("")) {
				continue;
			}
			
			// spit paper and references
			tmp = line.split(":", 2);
			
			// see if paper has been loaded previously
			paper = this.getPaper(tmp[0]);
			
			// split references
			tmp = tmp[1].split("\\s+");
			
			for (String ref: tmp) {
				// skip empty
				if (ref.trim().equals("")) {
					continue;
				}
				
				reference = this.getPaper(ref);
				
				file_references.add(reference);
				reference.addCitation(paper);
				paper.addReference(reference);
			}
		}
	}

	/**
	 * Set the current working paper by id
	 * 
	 * @param id The id of the paper
	 * @throws MissingPaperException If no paper with id exists
	 */
	public void setCurrentWorkingPaper(final String id) throws MissingPaperException {
		
		int index;
		int i = 1;
		
		if (id.startsWith("P")) {
			this.current_working_paper = this.getPaper(id);
		} else if (id.startsWith("R") || id.startsWith("C")) {
			
			LinkedHashSet<Paper> papers;
			
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
		throw new MissingPaperException(id);
	}

	/**
	 * Set the user interface
	 * 
	 * @param intf
	 */
	public void setInterface (CommandLineInterface intf) {
		this.intrface = intf;
	}

	/**
	 * Update a paper given a paper, list name and an update context
	 * 
	 * @param paper The paper to update
	 * @param list_name The list to update paper on
	 * @param context The update context
	 */
	public void updatePaper(Paper paper, String list_name, UpdateContext context) {
		if (!this.papers.contains(paper)) {
			this.papers.add(paper);
		}
		paper.update(list_name, context);
	}
	
	public void removeReference(Paper paper, Paper ref) {
		System.out.println(String.format("Removing %s as reference of %s", ref.getId(), paper.getId()));
		paper.removeReference(ref);
		ref.removeCitation(paper);
	}
	
	public void removeCitation(Paper paper, Paper ref) {
		System.out.println(String.format("Removing %s as citation of %s", ref.getId(), paper.getId()));
		paper.removeCitation(ref);
		ref.removeReference(paper);
	}

	/**
	 * Sort a paper list by author name
	 * 
	 * @param list_name The name of the list
	 * @throws InvalidListException If the list doesn't exist
	 */
	public void sortPaperList(SortOrder order, String list_name, boolean asc) throws InvalidListException {
		
		if (list_name.equals(this.getMasterListName())) {
			this.master_list.setSortOrder(order, asc);
		} else {
			for (PaperList pap: this.file_paper_lists) {
				if (pap.getListName().equals(list_name)) {
					pap.setSortOrder(order, asc);
					return;
				}
			}
		}
		
		throw new InvalidListException(list_name);
	}
	
	/**
	 * Sort the master paper list by author name
	 * @throws InvalidListException 
	 */
	public void sortMasterList(SortOrder order, boolean asc) {
		try {
			this.sortPaperList(order, this.getMasterListName(), asc);
		} catch (InvalidListException e) {
			// pass (master list always exists)
		}
	}
	
	/**
	 * Save the state of all lists in the system using serialization
	 * 
	 * @throws IOException
	 */
	public void saveState() throws IOException {
		ObjectOutputStream writer;
		
		writer = this.file_util.getBufferedObjectWriter("./data/papers.lst");
		writer.writeObject(this.papers);
		writer.close();
		
		writer = this.file_util.getBufferedObjectWriter("./data/master_paper.lst");
		writer.writeObject(this.master_list);
		writer.close();
		
		writer = this.file_util.getBufferedObjectWriter("./data/papers_list.lst");
		writer.writeObject(this.file_paper_lists);
		writer.close();
		
		writer = this.file_util.getBufferedObjectWriter("./data/references_list.lst");
		writer.writeObject(this.file_references_lists);
		writer.close();
	}
	
	/**
	 * Load the list state from a serialized state
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public void readState() throws IOException, ClassNotFoundException {
		ObjectInputStream reader;
		File f;
		
		f = this.file_util.openFile("./data/papers.lst");
		if (f.canRead()) {
			reader = this.file_util.getBufferedObjectReader(f);
			LinkedHashSet<Paper> papers = (LinkedHashSet<Paper>)reader.readObject();

			// Workaround for Bug Id#4957674: http://bugs.sun.com/view_bug.do?bug_id=4957674
			this.papers = new LinkedHashSet<Paper>();
			for (Paper pap: papers) {
				this.papers.add(pap);
				LinkedHashSet<Paper> cits = pap.getCitations();
				pap.clearCitations();
				for (Paper cit: cits) {
					pap.addCitation(cit);
				}
				LinkedHashSet<Paper> refs = pap.getReferences();
				pap.clearReferences();
				for (Paper ref: refs) {
					pap.addReference(ref);
				}
			}
		}

		f = this.file_util.openFile("./data/master_paper.lst");
		if (f.canRead()) {
			reader = this.file_util.getBufferedObjectReader(f);
			this.master_list = (PaperList)reader.readObject();
		}

		f = this.file_util.openFile("./data/papers_list.lst");
		if (f.canRead()) {
			reader = this.file_util.getBufferedObjectReader(f);
			LinkedHashSet<PaperList> paper_lists = (LinkedHashSet<PaperList>)reader.readObject();

			// and again (May not need this one as I am not overwriting hashCode and equals) 
			this.file_paper_lists = new LinkedHashSet<PaperList>();
			for (PaperList pap: paper_lists) {
				this.file_paper_lists.add(pap);
			}
		}
		
		this.file_references_lists = new LinkedHashMap<String, LinkedHashSet<Paper>>();
		
	}

}
