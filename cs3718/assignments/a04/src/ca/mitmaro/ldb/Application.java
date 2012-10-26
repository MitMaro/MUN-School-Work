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
	
	/**
	 * The terminal interface
	 */
	private Terminal terminal;
	
	/**
	 * The global list of papers
	 */
	private LinkedHashMap<String, Paper> papers;
	
	/**
	 * The current working paper
	 */
	private Paper current_working_paper;

	/**
	 * The key of the master list
	 */
	private final String master_list_name = "_ _ _M A S T E R_ _ _";
	
	/**
	 * The master list of papers
	 */
	private LinkedHashSet<Paper> master_paper_list;
	
	/**
	 * The master reference list
	 */
	private LinkedHashSet<Paper> master_references_list;
	
	/**
	 * The named list of papers
	 */
	private LinkedHashMap<String, LinkedHashSet<Paper>> file_paper_lists;
	
	/**
	 * The named list of references
	 */
	private LinkedHashMap<String, LinkedHashSet<Paper>> file_references_lists;
	
	/**
	 * Constructs the application class
	 * 
	 * @param file_manager The file util file mananger
	 * @param terminal A terminal interface
	 */
	public Application(FileUtil file_manager, Terminal terminal) {
		this.file_util = file_manager;
		this.terminal = terminal;
		
		File f = new File("./data/papers.lst"	);
		
		if (f.isFile()) {
			try {
				this.readState();
				return;
			} catch (IOException e) {
				this.terminal.err().println("IO Error Reading Saved Data");
			} catch (ClassNotFoundException e) {
				this.terminal.err().println("Class Missing While Reading Saved Data");
			}
		}
		
		// load empty lists on first load/error
		this.papers = new LinkedHashMap<String, Paper>();
		this.file_paper_lists = new LinkedHashMap<String, LinkedHashSet<Paper>>();
		this.file_references_lists = new LinkedHashMap<String, LinkedHashSet<Paper>>();
		this.master_paper_list = new LinkedHashSet<Paper>();
		this.master_references_list = new LinkedHashSet<Paper>();
	}
	
	/**
	 * Add paper to the master paper list
	 * 
	 * @param list_name The list name to add
	 * @throws InvalidListException If list name is invalid
	 */
	public void addPapersToMasterList(final String list_name) throws InvalidListException {
		
		if (!this.file_paper_lists.containsKey(list_name)) {
			throw new InvalidListException(list_name);
		}
		
		for (Paper paper: this.file_paper_lists.get(list_name)) {
			paper.addToList(this.master_list_name, list_name);
			this.master_paper_list.add(paper);
		}
	}
	
	/**
	 * Add references to the master paper list
	 * 
	 * @param list_name The list name to add
	 * @throws InvalidListException If list name is invalid
	 */
	public void addReferencesToMasterList(final String list_name) throws InvalidListException {

		if (!this.file_references_lists.containsKey(list_name)) {
			throw new InvalidListException(list_name);
		}
		
		for (Paper paper: this.file_references_lists.get(list_name)) {
			this.master_references_list.add(paper);
		}
	}
	
	/**
	 * Clear the master paper list
	 */
	public void clearMasterPaperList() {
		this.master_paper_list.clear();
	}
	
	/**
	 * Clear the master reference list
	 */
	public void clearMasterReferenceList() {
		this.master_references_list.clear();
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
	public Set<String> getFileListNames() {
		return this.file_paper_lists.keySet();
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
		return this.master_list_name;
	}
	
	/**
	 * @return the list of papers in the master list
	 */
	public LinkedHashSet<Paper> getMasterListPapers() {
		return this.master_paper_list;
	}
	
	/**
	 * Get a paper from a paper id
	 * 
	 * @param pid The paper id
	 * @return a paper instance
	 * @throws MissingPaperException if paper doens't exist
	 */
	public Paper getPaper(final String pid) throws MissingPaperException {
		if (this.papers.containsKey(pid)) {
			return this.papers.get(pid);
		}
		throw new MissingPaperException(pid);
	}

	/**
	 * Get a list of papers from a paper list
	 * @param list_name The name of the list
	 * @return A list of papers
	 * @throws InvalidListException If list doesn't exist
	 */
	public LinkedHashSet<Paper> getPaperList(final String list_name) throws InvalidListException {
		if (this.file_paper_lists.containsKey(list_name)) {
			return this.file_paper_lists.get(list_name);
		}
		throw new InvalidListException(list_name);
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
		
		// load the file paper list, if one exists. if not create it
		LinkedHashSet<Paper> file_paper = this.file_paper_lists.get(list_name);
		if (file_paper == null) {
			file_paper = new LinkedHashSet<Paper>();
			this.file_paper_lists.put(list_name, file_paper);
		}
		

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
					paper = this.papers.get(pid);
					// does not yet exist
					if (paper == null) {
						paper = this.createPaper(pid, type);
						this.papers.put(pid, paper);
					}
					
					paper.update(list_name, context);
					
					// update paper for this list
					file_paper.add(paper);
					
					// reset values to be safe
					pid = "";
					type = "";
					context = new UpdateContext();
					break;
			}
			
		}
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
			paper = this.papers.get(tmp[0]);
			// does not yet exist
			if (paper == null) {
				throw new MissingPaperException(tmp[0]);
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
					throw new MissingPaperException(tmp[0]);
				}
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
			this.current_working_paper = this.papers.get(id);
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
	public void updatePaper(final Paper paper, final String list_name, final UpdateContext context) {
		paper.update(list_name, context);
	}

	/**
	 * Sort a paper list by author name
	 * 
	 * @param list_name The name of the list
	 * @throws InvalidListException If the list doesn't exist
	 */
	public void sortPaperListByAuthor(final String list_name) throws InvalidListException {
		
		if (!this.file_paper_lists.containsKey(list_name)) {
			throw new InvalidListException(list_name);
		}
		
		ArrayList<Paper> tmp = new ArrayList<Paper>(this.file_paper_lists.get(list_name));
		Collections.sort(tmp, new PaperCompareAuthor(list_name));
		this.file_paper_lists.put(list_name, new LinkedHashSet<Paper>(tmp));
	}
	
	/**
	 * Sort a paper list by pid
	 * 
	 * @param list_name The name of the list
	 * @throws InvalidListException if the list doesn't exist
	 */
	public void sortPaperListByPID(final String list_name) throws InvalidListException {
		
		if (!this.file_paper_lists.containsKey(list_name)) {
			throw new InvalidListException(list_name);
		}
		
		ArrayList<Paper> tmp = new ArrayList<Paper>(this.file_paper_lists.get(list_name));
		Collections.sort(tmp, new PaperComparePID());
		this.file_paper_lists.put(list_name, new LinkedHashSet<Paper>(tmp));
	}
	
	/**
	 * Sort the master paper list by author name
	 */
	public void sortMasterListByAuthor() {
		ArrayList<Paper> tmp = new ArrayList<Paper>(this.master_paper_list);
		Collections.sort(tmp, new PaperCompareAuthor(this.getMasterListName()));
		this.master_paper_list = new LinkedHashSet<Paper>(tmp);
	}
	
	/**
	 * Sort the master paper list by paper id
	 */
	public void sortMasterListByPID() {
		ArrayList<Paper> tmp = new ArrayList<Paper>(this.master_paper_list);
		Collections.sort(tmp, new PaperComparePID());
		this.master_paper_list = new LinkedHashSet<Paper>(tmp);
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
		writer.writeObject(this.master_paper_list);
		writer.close();
		
		writer = this.file_util.getBufferedObjectWriter("./data/master_references.lst");
		writer.writeObject(this.master_references_list);
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
		
		reader = this.file_util.getBufferedObjectReader("./data/papers.lst");
		this.papers = (LinkedHashMap<String, Paper>)reader.readObject();
		
		reader = this.file_util.getBufferedObjectReader("./data/master_paper.lst");
		this.master_paper_list = (LinkedHashSet<Paper>)reader.readObject();
		
		reader = this.file_util.getBufferedObjectReader("./data/master_references.lst");
		this.master_references_list = (LinkedHashSet<Paper>)reader.readObject();
		
		reader = this.file_util.getBufferedObjectReader("./data/papers_list.lst");
		this.file_paper_lists = (LinkedHashMap<String, LinkedHashSet<Paper>>)reader.readObject();
		
		reader = this.file_util.getBufferedObjectReader("./data/references_list.lst");
		this.file_references_lists = (LinkedHashMap<String, LinkedHashSet<Paper>>)reader.readObject();
	}
}
