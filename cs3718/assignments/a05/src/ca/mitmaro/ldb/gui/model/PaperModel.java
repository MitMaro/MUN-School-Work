package ca.mitmaro.ldb.gui.model;

import ca.mitmaro.ldb.entity.Book;
import ca.mitmaro.ldb.entity.BookChapter;
import ca.mitmaro.ldb.entity.ConferencePaper;
import ca.mitmaro.ldb.entity.JournalPaper;
import ca.mitmaro.ldb.entity.PHDThesis;
import ca.mitmaro.ldb.entity.Paper;
import ca.mitmaro.ldb.exception.InvalidListException;

public class PaperModel extends PresentationModel {
	public enum Type {BOOK, BOOK_CHAPTER, CONFERENCE_PAPER, JOURNAL_PAPER, PHD_THESIS};
	
	protected Type type;
	protected String paper_id = "";
	protected String author_first = "";
	protected String author_last = "";
	protected String title = "";
	protected String year = "";
	protected String book_title = "";
	protected String address = "";
	protected String publisher = "";
	protected String chapter_title = "";
	protected String pages = "";
	protected String editors = "";
	protected String paper_title = "";
	protected String journal_title = "";
	protected String volume = "";

	MainModel main_model;
	
	public static final String EVENT_PAPER_TYPE_CHANGED = "paper_type_changed";
	public static final String EVENT_FIELD_CHANGED = "field_changed";
	
	public PaperModel(MainModel main_model) {
		
		this.main_model = main_model;
		
		this.registerEvents(new String[]{
			PaperModel.EVENT_FIELD_CHANGED,
			PaperModel.EVENT_PAPER_TYPE_CHANGED
		});
		
	}
	
	public boolean isInvalidPaper() {
		boolean common = (
			this.paper_id.isEmpty() ||
			this.author_first.isEmpty() ||
			this.author_last.isEmpty() ||
			this.year.isEmpty()
		);
		
		switch (this.type) {
			case BOOK:
				return (
					common ||
					this.book_title.isEmpty() ||
					this.address.isEmpty() ||
					this.publisher.isEmpty()
				);
			case BOOK_CHAPTER:
			case CONFERENCE_PAPER:
				return (
					common ||
					this.book_title.isEmpty() ||
					this.address.isEmpty() ||
					this.publisher.isEmpty() ||
					this.chapter_title.isEmpty() ||
					this.pages.isEmpty() ||
					this.editors.isEmpty()
				);
			case JOURNAL_PAPER:
				return (
					common ||
					this.pages.isEmpty() ||
					this.paper_title.isEmpty() ||
					this.volume.isEmpty() ||
					this.journal_title.isEmpty()
				);
			case PHD_THESIS:
				return (
					common ||
					this.title.isEmpty() ||
					this.address.isEmpty()
				);
			default:
				return false;
		}
	}
	
	public void setPaperField(String key, String value) {
		if (key.equals("paper_id")) {
			this.paper_id = value;
		} else if(key.equals("author_first")) {
			this.author_first = value;
		} else if(key.equals("author_last")) {
			this.author_last = value;
		} else if(key.equals("title")) {
			this.title = value;
		} else if(key.equals("year")) {
			this.year = value;
		} else if(key.equals("book_title")) {
			this.book_title = value;
		} else if(key.equals("address")) {
			this.address = value;
		} else if(key.equals("publisher")) {
			this.publisher = value;
		} else if(key.equals("chapter_title")) {
			this.chapter_title = value;
		} else if(key.equals("pages")) {
			this.pages = value;
		} else if(key.equals("editors")) {
			this.editors = value;
		} else if(key.equals("paper_title")) {
			this.paper_title = value;
		} else if(key.equals("journal_title")) {
			this.journal_title = value;
		} else if(key.equals("volume")) {
			this.volume = value;
		}
		this.triggerEvent(PaperModel.EVENT_FIELD_CHANGED);
	}	
	
	public String getPaperField(String key) {
		if (key.equals("paper_id")) {
			return this.paper_id;
		} else if(key.equals("author_first")) {
			return this.author_first;
		} else if(key.equals("author_last")) {
			return this.author_last;
		} else if(key.equals("title")) {
			return this.title;
		} else if(key.equals("year")) {
			return this.year;
		} else if(key.equals("book_title")) {
			return this.book_title;
		} else if(key.equals("address")) {
			return this.address;
		} else if(key.equals("publisher")) {
			return this.publisher;
		} else if(key.equals("chapter_title")) {
			return this.chapter_title;
		} else if(key.equals("pages")) {
			return this.pages;
		} else if(key.equals("editors")) {
			return this.editors;
		} else if(key.equals("paper_title")) {
			return this.paper_title;
		} else if(key.equals("journal_title")) {
			return this.journal_title;
		} else if(key.equals("volume")) {
			return this.volume;
		}
		throw new RuntimeException(String.format("GUI Requesting invalid paper key: %s", key));
	}
	
	public void setPaperType(Type type) {
		this.type = type;
		this.triggerEvent(PaperModel.EVENT_PAPER_TYPE_CHANGED);
	}
	
	public Type getPaperType() {
		return this.type;
	}
	
	public void createPaper() {
		
		int year = 0;
		int page_start = 0;
		int page_end = 0;
		int volume = 0;
		String[] tmp;
		
		if (!this.year.isEmpty()) {
			year = Integer.parseInt(this.year);
		}
		
		if (!this.pages.isEmpty()) {
			if (this.pages.contains("-")) {
				tmp = this.pages.split("-", 2);
				page_start = Integer.parseInt(tmp[0]);
				page_end = Integer.parseInt(tmp[1]);
			} else {
				page_start = page_end = Integer.parseInt(this.pages);
			}
		}

		if (!this.volume.isEmpty()) {
			volume = Integer.parseInt(this.volume);
		}
		
		switch (this.type) {
			case BOOK:
				this.main_model.updateBook(
					this.paper_id,
					this.book_title,
					this.author_first,
					this.author_last,
					year,
					this.address,
					this.publisher
				);
				break;
			case BOOK_CHAPTER:
				this.main_model.updateBookChapter(
					this.paper_id,
					this.book_title,
					this.chapter_title,
					this.author_first,
					this.author_last,
					year,
					page_start,
					page_end,
					this.address,
					this.publisher,
					this.editors
				);
				break;
			case PHD_THESIS:
				this.main_model.updatePHDThesis(
					this.paper_id,
					this.title,
					this.author_first,
					this.author_last,
					year,
					this.address
				);
				break;
			case JOURNAL_PAPER:
				this.main_model.updateJournalPaper(
					this.paper_id,
					this.paper_title,
					this.journal_title,
					this.author_first,
					this.author_last,
					year,
					volume,
					page_start,
					page_end
				);
				break;
			case CONFERENCE_PAPER:
				this.main_model.updateConference(
					this.paper_id,
					this.book_title,
					this.chapter_title,
					this.author_first,
					this.author_last,
					year,
					page_start,
					page_end,
					this.address,
					this.publisher,
					this.editors
				);
				break;
		}
	}

	public void setPaper(Paper paper) {
		this.paper_id = paper.getId();
		try {
			this.year = new Integer(paper.getYear(this.main_model.getCurrentList())).toString();
			this.author_first = paper.getAuthorFirst(this.main_model.getCurrentList());
			this.author_last = paper.getAuthorLast(this.main_model.getCurrentList());
			if (paper instanceof Book) {
				this.setPaperType(Type.BOOK);
				this.book_title = paper.getTitle(this.main_model.getCurrentList());
				this.address = ((Book)paper).getAddress(this.main_model.getCurrentList());
				this.publisher = ((Book)paper).getPublisher(this.main_model.getCurrentList());
			} else if (paper instanceof BookChapter) {
				this.setPaperType(Type.BOOK_CHAPTER);
				BookChapter bch = (BookChapter)paper;
				this.book_title = bch.getBookTitle(this.main_model.getCurrentList());
				this.chapter_title = bch.getChapterTitle(this.main_model.getCurrentList());
				this.setPaperField("pages", bch.getPages(this.main_model.getCurrentList()));
				this.address = bch.getAddress(this.main_model.getCurrentList());
				this.publisher = bch.getPublisher(this.main_model.getCurrentList());
				this.editors = bch.getEditors(this.main_model.getCurrentList());
			} else if (paper instanceof ConferencePaper) {
				this.setPaperType(Type.CONFERENCE_PAPER);
				ConferencePaper cp = (ConferencePaper)paper;
				this.book_title = cp.getBookTitle(this.main_model.getCurrentList());
				this.chapter_title = cp.getChapterTitle(this.main_model.getCurrentList());
				this.setPaperField("pages", cp.getPages(this.main_model.getCurrentList()));
				this.address = cp.getAddress(this.main_model.getCurrentList());
				this.publisher = cp.getPublisher(this.main_model.getCurrentList());
				this.editors = cp.getEditors(this.main_model.getCurrentList());
			} else if (paper instanceof JournalPaper) {
				this.setPaperType(Type.JOURNAL_PAPER);
				JournalPaper jp = (JournalPaper)paper;
				this.paper_title = jp.getPaperTitle(this.main_model.getCurrentList());
				this.journal_title = jp.getJournalTitle(this.main_model.getCurrentList());
				this.volume = new Integer(jp.getVolume(this.main_model.getCurrentList())).toString();
				this.setPaperField("pages", jp.getPages(this.main_model.getCurrentList()));
			} else if (paper instanceof PHDThesis) {
				this.setPaperType(Type.PHD_THESIS);
				this.title = ((PHDThesis)paper).getTitle(this.main_model.getCurrentList());
				this.address = ((PHDThesis)paper).getAddress(this.main_model.getCurrentList());
			}
		} catch (InvalidListException e) {
			throw new RuntimeException(String.format("Invalid list name: %s", this.main_model.getWorkingPaper()));
		}
	}

}
