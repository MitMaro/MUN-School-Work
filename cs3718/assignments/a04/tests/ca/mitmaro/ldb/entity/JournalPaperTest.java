package ca.mitmaro.ldb.entity;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ca.mitmaro.ldb.exception.InvalidListException;

public class JournalPaperTest {

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
		this.init_context.paper_title = "Paper Title";
		this.init_context.journal_title = "Journal Title";
		this.init_context.volume = 1;
		
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
		this.update_context.paper_title = "Paper Title Edit";
		this.update_context.journal_title = "Journal Title Edit";
		this.update_context.volume = 2;
	}

	@Test
	public void setDataSet() throws InvalidListException {
		JournalPaper pap = new JournalPaper("id");
		pap.setDataSet("list_name", this.init_context);

		Assert.assertEquals(pap.getTitle("list_name"), "Journal Title");
		Assert.assertEquals(pap.getPaperTitle("list_name"), "Paper Title");
		Assert.assertEquals(pap.getAuthorFirst("list_name"), "Author First");
		Assert.assertEquals(pap.getAuthorLast("list_name"), "Author Last");
		Assert.assertEquals(pap.getPaperTitle("list_name"), "Paper Title");
		Assert.assertEquals(pap.getYear("list_name"), 2000);
		Assert.assertEquals(pap.getPages("list_name"), "1");
		Assert.assertEquals(pap.getVolume("list_name"), 1);
		
	}

	@Test(dependsOnMethods = {"setDataSet"})
	public void getPages_samePage() throws InvalidListException {
		JournalPaper pap = new JournalPaper("id");
		pap.setDataSet("list_name", this.init_context);
		
		Assert.assertEquals(pap.getPages("list_name"), "1");
	}

	@Test(dependsOnMethods = {"setDataSet"})
	public void getPages_differentPage() throws InvalidListException {
		JournalPaper pap = new JournalPaper("id");

		UpdateContext context = new UpdateContext();
		context.start_page = 1;
		context.end_page = 2;
		pap.setDataSet("list_name", context);
		
		Assert.assertEquals(pap.getPages("list_name"), "1-2");
		
	}

	
	@Test(dependsOnMethods = {"setDataSet"})
	public void getPrintable() throws InvalidListException {
		JournalPaper pap = new JournalPaper("id");
		pap.setDataSet("list_name", this.init_context);
		Assert.assertEquals(
			pap.getPrintable("list_name", ""),
			"id: Author Last, Author First (2000) Paper Title\n\t" +
			"Journal Title, 1, 1.\n"
		);
	}
	
	@Test(dependsOnMethods = {"setDataSet"})
	public void update() throws InvalidListException {
		JournalPaper pap = new JournalPaper("id");
		pap.setDataSet("list_name", this.init_context);
		pap.update("list_name", this.update_context);

		Assert.assertEquals(pap.getTitle("list_name"), "Journal Title Edit");
		Assert.assertEquals(pap.getPaperTitle("list_name"), "Paper Title Edit");
		Assert.assertEquals(pap.getAuthorFirst("list_name"), "Author First Edit");
		Assert.assertEquals(pap.getAuthorLast("list_name"), "Author Last Edit");
		Assert.assertEquals(pap.getYear("list_name"), 2001);
		Assert.assertEquals(pap.getVolume("list_name"), 2);
		Assert.assertEquals(pap.getPages("list_name"), "2-3");
	}
}
