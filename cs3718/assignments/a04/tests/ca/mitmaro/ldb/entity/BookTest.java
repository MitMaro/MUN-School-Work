package ca.mitmaro.ldb.entity;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ca.mitmaro.ldb.exception.InvalidListException;

public class BookTest {

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
	public void addToList_missingOldList() {
		Book pap = new Book("id");
		pap.addToList("new_list", "old_list");
	}

	@Test
	public void setDataSet() {
		Book book = new Book("id");
		book.setDataSet("list_name", this.init_context);

		Assert.assertEquals(book.getTitle("list_name"), "Book Title");
		Assert.assertEquals(book.getAuthorFirst("list_name"), "Author First");
		Assert.assertEquals(book.getAuthorLast("list_name"), "Author Last");
		Assert.assertEquals(book.getYear("list_name"), 2000);
		Assert.assertEquals(book.getAddress("list_name"), "Address");
		Assert.assertEquals(book.getPublisher("list_name"), "Publisher");
	}

	@Test(dependsOnMethods = {"setDataSet"})
	public void update() {
		Book book = new Book("id");
		book.setDataSet("list_name", this.init_context);
		book.update("list_name", this.update_context);

		Assert.assertEquals(book.getTitle("list_name"), "Book Title Edit");
		Assert.assertEquals(book.getAuthorFirst("list_name"), "Author First Edit");
		Assert.assertEquals(book.getAuthorLast("list_name"), "Author Last Edit");
		Assert.assertEquals(book.getYear("list_name"), 2001);
		Assert.assertEquals(book.getAddress("list_name"), "Address Edit");
		Assert.assertEquals(book.getPublisher("list_name"), "Publisher Edit");
	}

	@Test(dependsOnMethods = {"setDataSet"})
	public void getPrintable() {
		Book book = new Book("id");
		book.setDataSet("list_name", this.init_context);
		Assert.assertEquals(
			book.getPrintable("list_name", ""),
			"id: Author Last, Author First (2000) Book Title\n\tPublisher; Address.\n"
		);
	}
}
