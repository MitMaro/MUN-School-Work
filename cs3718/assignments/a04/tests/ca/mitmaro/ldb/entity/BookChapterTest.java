package ca.mitmaro.ldb.entity;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ca.mitmaro.ldb.exception.InvalidListException;

public class BookChapterTest {

	UpdateContext init_context;
	UpdateContext update_context;
	
	@BeforeTest
	public void beforeClass() {
		this.init_context = new UpdateContext();
		this.init_context.author_last = "Author Last";
		this.init_context.author_first = "Author First";
		this.init_context.year = 2000;
		this.init_context.book_title = "Book Title";
		this.init_context.address = "Address";
		this.init_context.publisher = "Publisher";
		this.init_context.title = "Title";
		this.init_context.chapter_title = "Chapter Title";
		this.init_context.start_page = 1;
		this.init_context.end_page = 1;
		this.init_context.editors = "Editors";
		
		this.update_context = new UpdateContext();
		this.update_context.author_last = "Author Last Edit";
		this.update_context.author_first = "Author First Edit";
		this.update_context.year = 2001;
		this.update_context.book_title = "Book Title Edit";
		this.update_context.address = "Address Edit";
		this.update_context.publisher = "Publisher Edit";
		this.update_context.title = "Title Edit";
		this.update_context.chapter_title = "Chapter Title Edit";
		this.update_context.start_page = 2;
		this.update_context.end_page = 3;
		this.update_context.editors = "Editors Edit";
	}

	
	@Test(expectedExceptions= {InvalidListException.class})
	public void addToList_missingOldList() throws InvalidListException {
		BookChapter chap = new BookChapter("id");
		chap.addToList("new_list", "old_list");
	}

	@Test
	public void setDataSet() throws InvalidListException {
		BookChapter chap = new BookChapter("id");
		chap.setDataSet("list_name", this.init_context);
		Assert.assertEquals(chap.getTitle("list_name"), "Book Title");
		Assert.assertEquals(chap.getAuthorFirst("list_name"), "Author First");
		Assert.assertEquals(chap.getAuthorLast("list_name"), "Author Last");
		Assert.assertEquals(chap.getYear("list_name"), 2000);
		Assert.assertEquals(chap.getAddress("list_name"), "Address");
		Assert.assertEquals(chap.getPublisher("list_name"), "Publisher");
		Assert.assertEquals(chap.getEditors("list_name"), "Editors");
		Assert.assertEquals(chap.getPages("list_name"), "1");
		Assert.assertEquals(chap.getChapterTitle("list_name"), "Chapter Title");
		
	}

	@Test(dependsOnMethods = {"setDataSet"})
	public void getPages_samePage() throws InvalidListException {
		ConferencePaper pap = new ConferencePaper("id");
		UpdateContext context = new UpdateContext();
		context.start_page = context.end_page = 1;
		pap.setDataSet("list_name", context);
		
		Assert.assertEquals(pap.getPages("list_name"), "1");
	}

	@Test(dependsOnMethods = {"setDataSet"})
	public void getPages_differentPage() throws InvalidListException {
		ConferencePaper pap = new ConferencePaper("id");
		UpdateContext context = new UpdateContext();
		context.start_page = 1;
		context.end_page = 2;
		pap.setDataSet("list_name", context);
		
		Assert.assertEquals(pap.getPages("list_name"), "1-2");
		
	}
	
	@Test(dependsOnMethods = {"setDataSet"})
	public void getPrintable() throws InvalidListException {
		BookChapter chap = new BookChapter("id");
		chap.setDataSet("list_name", this.init_context);
		Assert.assertEquals(
			chap.getPrintable("list_name", ""),
			"id: Author Last, Author First (2000) Chapter Title\n\t" +
			"In Editors (eds.) Book Title\n\t" +
			"Publisher; Address. 1.\n"
		);
	}
	
	@Test(dependsOnMethods = {"setDataSet"})
	public void update() throws InvalidListException {
		BookChapter chap = new BookChapter("id");

		chap.setDataSet("list_name", this.init_context);
		chap.update("list_name", this.update_context);
		Assert.assertEquals(chap.getTitle("list_name"), "Book Title Edit");
		Assert.assertEquals(chap.getAuthorFirst("list_name"), "Author First Edit");
		Assert.assertEquals(chap.getAuthorLast("list_name"), "Author Last Edit");
		Assert.assertEquals(chap.getYear("list_name"), 2001);
		Assert.assertEquals(chap.getAddress("list_name"), "Address Edit");
		Assert.assertEquals(chap.getPublisher("list_name"), "Publisher Edit");
		Assert.assertEquals(chap.getEditors("list_name"), "Editors Edit");
		Assert.assertEquals(chap.getPages("list_name"), "2-3");
		Assert.assertEquals(chap.getChapterTitle("list_name"), "Chapter Title Edit");
		
	}
}
