package ca.mitmaro.ldb.entity;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import ca.mitmaro.ldb.exception.InvalidListException;

public class PaperTest {

	@DataProvider(name = "updateDataInt")
	public Object[][] updateDataInt() {
		return new Object[][] {
			{ "year", new Integer(2222) },
		};
	}
	
	private static class PaperImpl extends Paper {
		public PaperImpl(String id, String type) {
			super(id, type);
		}
		@Override
		public String getPrintable(String list_name, String padding) {
			return null;
		}
	}
	
	@Test
	public void addCitation() {
		PaperImpl pap = new PaperImpl("id", "type");
		PaperImpl pap2 = new PaperImpl("id2", "type"); 
		pap.addCitation(pap2);
		
		Assert.assertEquals(pap.getCitations().get("id2"), pap2);
	}

	@Test
	public void addReference() {
		PaperImpl pap = new PaperImpl("id", "type");
		PaperImpl pap2 = new PaperImpl("id2", "type"); 
		pap.addReference(pap2);
		
		Assert.assertEquals(pap.getReferences().get("id2"), pap2);
	}

	@Test(expectedExceptions= {InvalidListException.class})
	public void addToList_missingOldList() {
		PaperImpl pap = new PaperImpl("id", "type");
		pap.addToList("new_list", "old_list");
	}

	@Test(expectedExceptions= {InvalidListException.class})
	public void getAuthorFirst_Fail() {
		PaperImpl pap = new PaperImpl("id", "type");
		pap.getAuthorFirst("not-there");
	}

	@Test(expectedExceptions= {InvalidListException.class})
	public void getAuthorLast_Fail() {
		PaperImpl pap = new PaperImpl("id", "type");
		pap.getAuthorLast("not-there");
	}

	@Test(expectedExceptions= {InvalidListException.class})
	public void getYear_Fail() {
		PaperImpl pap = new PaperImpl("id", "type");
		pap.getYear("not-there");
	}

	@Test(expectedExceptions= {InvalidListException.class})
	public void getTitle_Fail() {
		PaperImpl pap = new PaperImpl("id", "type");
		pap.getTitle("not-there");
	}
	
	@Test
	public void setDataSet() {
		PaperImpl pap = new PaperImpl("id", "type");
		pap.setDataSet("list_name", "A Title", "First Name", "Last Name", 2011);
		Assert.assertEquals(pap.getTitle("list_name"), "A Title");
		Assert.assertEquals(pap.getAuthorFirst("list_name"), "First Name");
		Assert.assertEquals(pap.getAuthorLast("list_name"), "Last Name");
		Assert.assertEquals(pap.getYear("list_name"), 2011);
	}
	
	@Test(dependsOnMethods = {"setDataSet"})
	public void addToList_Success() {
		PaperImpl pap = new PaperImpl("id", "type");
		pap.setDataSet("old_list", "A Title", "First Name", "Last Name", 2011);
		pap.addToList("new_list", "old_list");
		Assert.assertEquals(pap.getTitle("new_list"), "A Title");
		Assert.assertEquals(pap.getAuthorFirst("new_list"), "First Name");
		Assert.assertEquals(pap.getAuthorLast("new_list"), "Last Name");
		Assert.assertEquals(pap.getYear("new_list"), 2011);
	}
	
	@Test
	public void updateFieldStringValue() {
		PaperImpl pap = new PaperImpl("id", "type");
		pap.setDataSet("list_name", "A Title", "First Name", "Last Name", 2011);
		pap.updateField("list_name", "author_first", "New First");
		pap.updateField("list_name", "author_last", "New Last");
		pap.updateField("list_name", "title", "New Title");
		pap.updateField("list_name", "year", 2222);
		Assert.assertEquals(pap.getAuthorFirst("list_name"), "New First");
		Assert.assertEquals(pap.getAuthorLast("list_name"), "New Last");
		Assert.assertEquals(pap.getTitle("list_name"), "New Title");
		Assert.assertEquals(pap.getYear("list_name"), 2222);
	}
}