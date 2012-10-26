package ca.mitmaro.ldb.entity;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import ca.mitmaro.ldb.exception.InvalidListException;

public class PaperTest {

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
		this.init_context.volume = 3;
		
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

	@SuppressWarnings("serial")
	private static class PaperImpl extends Paper {
		public PaperImpl(String id) {
			super(id);
		}
		
		@Override
		public String getPrintable(String list_name, String padding) {
			return null;
		}
		
		@Override
		public String[] getFields() {
			return null;
		}
		
		@Override
		protected Context getContext() {
			return new Paper.Context();
		}
	}
	
	@Test
	public void addCitation() {
		PaperImpl pap = new PaperImpl("id");
		PaperImpl pap2 = new PaperImpl("id2"); 
		pap.addCitation(pap2);
		
		Assert.assertTrue(pap.getCitations().contains(pap2));
	}

	@Test
	public void addReference() {
		PaperImpl pap = new PaperImpl("id");
		PaperImpl pap2 = new PaperImpl("id2"); 
		pap.addReference(pap2);

		Assert.assertTrue(pap.getReferences().contains(pap2));
	}

	@Test(expectedExceptions= {InvalidListException.class})
	public void addToList_missingOldList() throws InvalidListException {
		PaperImpl pap = new PaperImpl("id");
		pap.addToList("new_list", "old_list");
	}

	@Test(expectedExceptions= {InvalidListException.class})
	public void getAuthorFirst_Fail() throws InvalidListException {
		PaperImpl pap = new PaperImpl("id");
		pap.getAuthorFirst("not-there");
	}

	@Test(expectedExceptions= {InvalidListException.class})
	public void getAuthorLast_Fail() throws InvalidListException {
		PaperImpl pap = new PaperImpl("id");
		pap.getAuthorLast("not-there");
	}

	@Test(expectedExceptions= {InvalidListException.class})
	public void getYear_Fail() throws InvalidListException {
		PaperImpl pap = new PaperImpl("id");
		pap.getYear("not-there");
	}

	@Test(expectedExceptions= {InvalidListException.class})
	public void getTitle_Fail() throws InvalidListException {
		PaperImpl pap = new PaperImpl("id");
		pap.getTitle("not-there");
	}
	
	@Test
	public void setDataSet() throws InvalidListException {
		PaperImpl pap = new PaperImpl("id");
		pap.setDataSet("list_name", this.init_context);
		Assert.assertEquals(pap.getTitle("list_name"), "Title");
		Assert.assertEquals(pap.getAuthorFirst("list_name"), "Author First");
		Assert.assertEquals(pap.getAuthorLast("list_name"), "Author Last");
		Assert.assertEquals(pap.getYear("list_name"), 2000);
	}
	
	@Test(dependsOnMethods = {"setDataSet"})
	public void addToList_Success() throws InvalidListException {
		PaperImpl pap = new PaperImpl("id");
		pap.setDataSet("old_list", this.init_context);
		pap.addToList("new_list", "old_list");
		
		// updating old list shouldn't effect the new
		pap.update("old_list", this.update_context);
		
		Assert.assertEquals(pap.getTitle("new_list"), "Title");
		Assert.assertEquals(pap.getAuthorLast("new_list"), "Author Last");
		Assert.assertEquals(pap.getAuthorFirst("new_list"), "Author First");
		Assert.assertEquals(pap.getYear("new_list"), 2000);
	}
	
	@Test
	public void update() throws InvalidListException {
		PaperImpl pap = new PaperImpl("id");
		pap.setDataSet("list_name", this.init_context);
		pap.update("list_name", this.update_context);
		Assert.assertEquals(pap.getTitle("list_name"), "Title Edit");
		Assert.assertEquals(pap.getAuthorLast("list_name"), "Author Last Edit");
		Assert.assertEquals(pap.getAuthorFirst("list_name"), "Author First Edit");
		Assert.assertEquals(pap.getYear("list_name"), 2001);
	}
}