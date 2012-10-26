package ca.mitmaro.ldb.gui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import ca.mitmaro.ldb.Application;
import ca.mitmaro.ldb.entity.Book;
import ca.mitmaro.ldb.entity.BookChapter;
import ca.mitmaro.ldb.entity.ConferencePaper;
import ca.mitmaro.ldb.entity.JournalPaper;
import ca.mitmaro.ldb.entity.PHDThesis;
import ca.mitmaro.ldb.entity.Paper;
import ca.mitmaro.ldb.entity.UpdateContext;
import ca.mitmaro.ldb.exception.InvalidListException;
import ca.mitmaro.ldb.exception.MissingPaperException;

public class MainModel extends PresentationModel {
	
	private String current_list_name;
	
	private Paper current_working_paper;

	private Application application;
	
	public static final String EVENT_LIST_ADDED = "list_added";
	public static final String EVENT_LIST_CHANGED = "list_changed";
	public static final String EVENT_LIST_REMOVED = "list_removed";
	public static final String EVENT_PAPER_CHANGED = "paper_changed";
	public static final String EVENT_PAPER_REMOVED = "paper_removed";
	public static final String EVENT_PAPER_UPDATED = "paper_updated";
	public static final String EVENT_CITATION_SELECTED = "citation_selected";
	public static final String EVENT_CITATION_REMOVED = "citation_removed";
	public static final String EVENT_REFERENCE_SELECTED = "reference_selected";
	public static final String EVENT_REFERENCE_REMOVED = "reference_removed";
	
	public MainModel(Application application) {
		
		this.application = application;
		this.current_list_name = "Master List";
		
		this.registerEvents(new String[]{
			MainModel.EVENT_LIST_ADDED,
			MainModel.EVENT_LIST_CHANGED,
			MainModel.EVENT_LIST_REMOVED,
			MainModel.EVENT_PAPER_CHANGED,
			MainModel.EVENT_PAPER_REMOVED,
			MainModel.EVENT_PAPER_UPDATED,
			MainModel.EVENT_CITATION_SELECTED,
			MainModel.EVENT_CITATION_REMOVED,
			MainModel.EVENT_REFERENCE_SELECTED,
			MainModel.EVENT_REFERENCE_REMOVED
		});
	}

	public String[] getLists() {
		ArrayList<String> list = this.application.getFileListNames();
		list.add(0,"Master List"); // master list is always first
		return list.toArray(new String[list.size()]);
	}

	public Paper[] getFullPapersList() {
		ArrayList<Paper> papers = this.application.getFullPapersList();
		return papers.toArray(new Paper[papers.size()]);
	}
	
	public boolean isValidListName(String list_name) {
		if (this.application.getFileListNames().contains(list_name)) {
			return false;
		}
		return list_name.matches("^[a-zA-Z0-9]{3,}$");
	}
	
	public void addList(String list_name) {
		this.application.createList(list_name);
		this.triggerEvent(MainModel.EVENT_LIST_ADDED);
	}
	
	public void deleteCurrentList() {
		this.application.deleteList(this.getCurrentList());
		this.triggerEvent(MainModel.EVENT_LIST_REMOVED);
		this.setWorkingList("Master List");
	}
	
	public void setWorkingList(String list_name) {
		if (list_name == null) {
			throw new RuntimeException();
		}
		this.current_list_name = list_name;
		this.triggerEvent(MainModel.EVENT_LIST_CHANGED);
	}

	public String getList() {
		return this.current_list_name;
	}
	
	public String getCurrentList() {
		if (this.current_list_name.equals("Master List")) {
			return this.application.getMasterListName();
		}
		return this.current_list_name;
	}
	
	public void setWorkingPaperFromPid(String pid) {
		try {
			this.setWorkingPaper(this.application.getPaper(pid));
		} catch (MissingPaperException e) {
			throw new RuntimeException(String.format("Missing paper: %s", pid));
		}
	}
	
	public void setWorkingPaper(Paper paper) {
		this.current_working_paper = paper;
		this.triggerEvent(MainModel.EVENT_PAPER_CHANGED);
	}
	
	public String getPrintablePaper() {
		if (this.current_working_paper == null) {
			return "";
		}
		
		try {
			String list_name = this.getCurrentList();
			
			if (!this.current_working_paper.inList(list_name)) {
				list_name = this.current_working_paper.getList();
			}
			
			return this.current_working_paper.getPrintable(list_name, "");
		} catch (InvalidListException e) {
			throw new RuntimeException(String.format("Invalid list name: %s", this.getList()));
		}
	}
	
	public String getPaperName() {
		try {
			return this.current_working_paper.getTitle(this.getCurrentList());
		} catch (InvalidListException e) {
			throw new RuntimeException(String.format("Invalid list name: %s", this.getList()));

		}
	}
	
	public boolean canRemovePaper() {
		return this.current_working_paper != null;
	}
	
	public Paper[] getListPapers() {
		try {
			ArrayList<Paper> papers = this.application.getPaperList(this.getCurrentList());
			return papers.toArray(new Paper[papers.size()]);
		} catch (InvalidListException e) {
			throw new RuntimeException("Invalid list name provided by GUI", e);
		}
	}

	public Paper[] getCitations() {
		
		if (this.current_working_paper == null) {
			return new Paper[0];
		}
		
		LinkedHashSet<Paper> papers = this.current_working_paper.getCitations();
		return papers.toArray(new Paper[papers.size()]);
	}

	public Paper[] getReferences() {
		
		if (this.current_working_paper == null) {
			return new Paper[0];
		}
		
		LinkedHashSet<Paper> papers = this.current_working_paper.getReferences();
		return papers.toArray(new Paper[papers.size()]);
	}
	
	public boolean isListDeletable() {
		return !this.getList().equals("Master List");
	}
	
	
	public void updateBook(
		String pid,
		String title,
		String author_first,
		String author_last,
		int year,
		String address,
		String publisher
	) {
		
		UpdateContext context = new UpdateContext();
		
		context.author_first = author_first;
		context.author_last = author_last;
		context.year = year;
		context.book_title = title;
		context.address = address;
		context.publisher = publisher;
		
		Paper paper;
		try {
			paper = this.application.getPaper(pid);
		} catch (MissingPaperException e) {
			paper = new Book(pid); 
		}
		
		this.updatePaper(paper, context);
	}

	public void updateBookChapter(
		String pid,
		String book_title,
		String chapter_title,
		String author_first,
		String author_last,
		int year,
		int page_start,
		int page_end,
		String address,
		String publisher,
		String editors
	) {
		UpdateContext context = new UpdateContext();
		
		context.book_title = book_title;
		context.chapter_title = chapter_title;
		context.author_first = author_first;
		context.author_last = author_last;
		context.year = year;
		context.start_page = page_start;
		context.end_page = page_end;
		context.address = address;
		context.publisher = publisher;
		context.editors = editors;
		
		Paper paper;
		try {
			paper = this.application.getPaper(pid);
		} catch (MissingPaperException e) {
			paper = new BookChapter(pid); 
		}
		
		this.updatePaper(paper, context);
	}
	
	public void updateConference(
		String pid,
		String book_title,
		String chapter_title,
		String author_first,
		String author_last,
		int year,
		int page_start,
		int page_end,
		String address,
		String publisher,
		String editors
	) {
		UpdateContext context = new UpdateContext();
		
		context.book_title = book_title;
		context.chapter_title = chapter_title;
		context.author_first = author_first;
		context.author_last = author_last;
		context.year = year;
		context.start_page = page_start;
		context.end_page = page_end;
		context.address = address;
		context.publisher = publisher;
		context.editors = editors;
		
		Paper paper;
		try {
			paper = this.application.getPaper(pid);
		} catch (MissingPaperException e) {
			paper = new ConferencePaper(pid); 
		}
		
		this.updatePaper(paper, context);
	}

	public void updateJournalPaper(
		String pid,
		String paper_title,
		String journal_title,
		String author_first,
		String author_last,
		int year,
		int volume,
		int page_start,
		int page_end
	) {
		UpdateContext context = new UpdateContext();
		
		context.paper_title = paper_title;
		context.journal_title = journal_title;
		context.author_first = author_first;
		context.author_last = author_last;
		context.year = year;
		context.volume = volume;
		context.start_page = page_start;
		context.end_page = page_end;
		
		Paper paper;
		try {
			paper = this.application.getPaper(pid);
		} catch (MissingPaperException e) {
			paper = new JournalPaper(pid); 
		}
		
		this.updatePaper(paper, context);
	}
	
	public void updatePHDThesis(
		String pid,
		String title,
		String author_first,
		String author_last,
		int year,
		String address
	) {
		UpdateContext context = new UpdateContext();
		
		context.title = title;
		context.author_first = author_first;
		context.author_last = author_last;
		context.year = year;
		context.address = address;
		
		Paper paper;
		try {
			paper = this.application.getPaper(pid);
		} catch (MissingPaperException e) {
			paper = new PHDThesis(pid); 
		}
		
		this.updatePaper(paper, context);
	}
	
	private void updatePaper(Paper paper, UpdateContext context) {
		this.application.updatePaper(paper, this.getCurrentList(), context);
		this.triggerEvent(MainModel.EVENT_PAPER_UPDATED);
		this.setWorkingPaper(paper);
	}
	
	public void deleteWorkingPaper() {
		if (this.current_working_paper != null) {
			this.application.removePaperFromList(this.current_working_paper, this.getCurrentList());
			this.triggerEvent(MainModel.EVENT_PAPER_REMOVED);
		}
	}

	public Paper getWorkingPaper() {
		return this.current_working_paper;
	}
	
	public void removeCitation(String pid) {
		Paper cit;
		try {
			cit = this.application.getPaper(pid);
		} catch (MissingPaperException e) {
			throw new RuntimeException(String.format("Invalid paper, %s, given from GUI", pid));
		}
		this.application.removeCitation(this.current_working_paper, cit);
		this.triggerEvent(MainModel.EVENT_CITATION_REMOVED);
	}
	
	public void removeReference(String pid) {
		Paper ref;
		try {
			ref = this.application.getPaper(pid);
		} catch (MissingPaperException e) {
			throw new RuntimeException(String.format("Invalid paper, %s, given from GUI", pid));
		}
		this.application.removeReference(this.current_working_paper, ref);
		this.triggerEvent(MainModel.EVENT_REFERENCE_REMOVED);
		
	}

	public void loadPaperDataFile(String file_name, String list_name) {
		try {
			this.application.loadPaperDescriptionListFile(new File(file_name), list_name);
			this.triggerEvent(MainModel.EVENT_LIST_ADDED);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("IO Error on load file. I can't handle this exception right now :(");
		}
	}
	
	public void loadRefenceDataFile(String file_name, String list_name) {
		try {
			this.application.loadPaperReferenceListFile(new File(file_name), list_name);
			this.triggerEvent(MainModel.EVENT_PAPER_UPDATED);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("IO Error on load file. I can't handle this exception right now :(");
		} catch (MissingPaperException e) {
			throw new RuntimeException("IO Error on load file. I can't handle this exception right now :(");
		}
	}

	public void addToMaster() {
		try {
			this.application.addPapersToMasterList(this.getCurrentList());
		} catch (InvalidListException e) {
			throw new RuntimeException("GUI provided invalid list name");
		}
	}

	public void save() {
		try {
			this.application.saveState();
		} catch (IOException e) {
			throw new RuntimeException("Error happened.");
		}
	}

	public void clearMasterList() {
		this.application.clearMasterPaperList();
		this.triggerEvent(MainModel.EVENT_LIST_CHANGED);
	}
}
