/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
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
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;
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
import ca.mitmaro.ldb.exception.MissingPaperException;

public class Application {
	private static enum InputTag {PID, ADD, AUT, BED, BNP, BTL, JNP, JNT, JNV, PTY, PUB, PYR, TLE, END}
	
	private CommandLineInterface intrface; // can be substituted later for other interfaces
	
	private FileUtil file_util;
	
	private Terminal terminal;
	
	// global lists
	private LinkedHashMap<String, Paper> papers;
	private LinkedHashMap<String, Paper> references;
	
	private Paper current_working_paper;

	// the key of the master list
	private final String master_list_name = "_ _ _M A S T E R_ _ _";
	
	// the "master" list of paper
	private LinkedHashSet<Paper> master_paper_list;
	// the "master" list of references
	private LinkedHashSet<Paper> master_references_list;
	
	// the named lists of paper
	private LinkedHashMap<String, LinkedHashSet<Paper>> file_paper_lists;
	// the named lists of references
	private LinkedHashMap<String, LinkedHashSet<Paper>> file_references_lists;
	
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
		this.references = new LinkedHashMap<String, Paper>();
		this.file_paper_lists = new LinkedHashMap<String, LinkedHashSet<Paper>>();
		this.file_references_lists = new LinkedHashMap<String, LinkedHashSet<Paper>>();
		this.master_paper_list = new LinkedHashSet<Paper>();
		this.master_references_list = new LinkedHashSet<Paper>();
	}
	
	public void addPapersToMasterList(String name) {
		for (Paper paper: this.file_paper_lists.get(name)) {
			paper.addToList(this.master_list_name, name);
			this.master_paper_list.add(paper);
		}
	}
	
	public void addReferencesToMasterList(String name) {
		for (Paper paper: this.file_references_lists.get(name)) {
			this.master_references_list.add(paper);
		}
	}
	
	public void clearMasterPaperList() {
		this.master_paper_list.clear();
	}
	
	public void clearMasterReferenceList() {
		this.master_references_list.clear();
	}
	
	private Paper createPaper(String pid, String type) {
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
	
	public Paper getCurrentWorkingPaper() {
		return this.current_working_paper;
	}
	
	public Set<String> getFileListNames() {
		return this.file_paper_lists.keySet();
	}
	
	public Set<String> getFileReferenceListNames() {
		return this.file_references_lists.keySet();
	}
	
	public CommandLineInterface getInterface() {
		return this.intrface;
	}
	
	public String getMasterListName() {
		return this.master_list_name;
	}
	
	public LinkedHashSet<Paper> getMasterListPapers() {
		return this.master_paper_list;
	}
	
	public Paper getPaper(String pid) {
		return this.papers.get(pid);
	}

	public LinkedHashSet<Paper> getPaperList(String list_name) {
		return this.file_paper_lists.get(list_name);
	}

	public void loadPaperDescriptionListFile(File file, String name) throws IOException {
		
		BufferedReader in = this.file_util.getBufferedReader(file);
		
		String line = "";
		String[] tmp;
		InputTag input_tag;
		
		// load the file paper list, if one exists. if not create it
		LinkedHashSet<Paper> file_paper = this.file_paper_lists.get(name);
		if (file_paper == null) {
			file_paper = new LinkedHashSet<Paper>();
			this.file_paper_lists.put(name, file_paper);
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
					
					paper.update(name, context);
					
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

	public void loadPaperReferenceListFile(File file, String name) throws IOException, MissingPaperException {
		
		BufferedReader in = this.file_util.getBufferedReader(file);
		
		String line;
		String[] tmp;
		Paper paper;
		Paper reference;
		
		// load the file reference list, if one exists. if not create it
		LinkedHashSet<Paper> file_references = this.file_references_lists.get(name);
		if (file_references == null) {
			file_references = new LinkedHashSet<Paper>();
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
				file_references.add(reference);
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
		this.terminal.out().println("Current working paper set to: " + this.current_working_paper.getId());
	}

	public void setInterface (CommandLineInterface intf) {
		this.intrface = intf;
	}

	public void updatePaper(Paper paper, String list_name, UpdateContext context) {
		paper.update(list_name, context);
	}

	public void sortPaperListByAuthor(String list_name) {
		ArrayList<Paper> tmp = new ArrayList<Paper>(this.file_paper_lists.get(list_name));
		Collections.sort(tmp, new PaperCompareAuthor(list_name));
		this.file_paper_lists.put(list_name, new LinkedHashSet<Paper>(tmp));
	}
	
	public void sortPaperListByPID(String list_name) {
		ArrayList<Paper> tmp = new ArrayList<Paper>(this.file_paper_lists.get(list_name));
		Collections.sort(tmp, new PaperComparePID());
		this.file_paper_lists.put(list_name, new LinkedHashSet<Paper>(tmp));
	}
	
	public void sortMasterListByAuthor() {
		ArrayList<Paper> tmp = new ArrayList<Paper>(this.master_paper_list);
		Collections.sort(tmp, new PaperCompareAuthor(this.getMasterListName()));
		this.master_paper_list = new LinkedHashSet<Paper>(tmp);
	}
	
	public void sortMasterListByPID() {
		ArrayList<Paper> tmp = new ArrayList<Paper>(this.master_paper_list);
		Collections.sort(tmp, new PaperComparePID());
		this.master_paper_list = new LinkedHashSet<Paper>(tmp);
	}

	public void saveState() throws IOException {
		ObjectOutputStream writer;

		writer = this.file_util.getBufferedObjectWriter("./data/papers.lst");
		writer.writeObject(this.papers);
		writer.close();
		
		writer = this.file_util.getBufferedObjectWriter("./data/references.lst");
		writer.writeObject(this.references);
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
	
	@SuppressWarnings("unchecked")
	public void readState() throws IOException, ClassNotFoundException {
		ObjectInputStream reader;
		
		reader = this.file_util.getBufferedObjectReader("./data/papers.lst");
		this.papers = (LinkedHashMap<String, Paper>)reader.readObject();
		
		reader = this.file_util.getBufferedObjectReader("./data/references.lst");
		this.references = (LinkedHashMap<String, Paper>)reader.readObject();
		
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
