package ca.mitmaro.ldb.entity;

import org.testng.Assert;
import org.testng.annotations.Test;

import ca.mitmaro.ldb.exception.InvalidListException;

public class JournalPaperTest {

	@Test(expectedExceptions = { InvalidListException.class })
	public void addToList_missingOldList() {
		JournalPaper pap = new JournalPaper("id", "type");
		pap.addToList("new_list", "old_list");
	}

	@Test
	public void setDataSet() {
		JournalPaper pap = new JournalPaper("id", "type");
		pap.setDataSet(
			"list_name", "Journal Title", "Paper Title",
			"First Name", "Last Name", 2011, 2, 1, 1
		);
		Assert.assertEquals(pap.getTitle("list_name"), "Journal Title");
		Assert.assertEquals(pap.getAuthorFirst("list_name"), "First Name");
		Assert.assertEquals(pap.getAuthorLast("list_name"), "Last Name");
		Assert.assertEquals(pap.getYear("list_name"), 2011);
		Assert.assertEquals(pap.getPages("list_name"), "1");
		Assert.assertEquals(pap.getVolume("list_name"), 2);
	}

	@Test(dependsOnMethods = {"setDataSet"})
	public void getPages_samePage() {
		JournalPaper pap = new JournalPaper("id", "type");
		pap.setDataSet(
			"list_name", "Journal Title", "Paper Title",
			"First Name", "Last Name", 2011, 2, 1, 1
		);
		
		Assert.assertEquals(pap.getPages("list_name"), "1");
	}

	@Test(dependsOnMethods = {"setDataSet"})
	public void getPages_differentPage() {
		JournalPaper pap = new JournalPaper("id", "type");
		pap.setDataSet(
			"list_name", "Journal Title", "Paper Title",
			"First Name", "Last Name", 2011, 2, 1, 2
		);
		
		Assert.assertEquals(pap.getPages("list_name"), "1-2");
		
	}

	
	@Test(dependsOnMethods = {"setDataSet"})
	public void getPrintable() {
		JournalPaper pap = new JournalPaper("id", "type");
		pap.setDataSet(
			"list_name", "Journal Title", "Paper Title",
			"First Name", "Last Name", 2011, 2, 1, 1
		);
		Assert.assertEquals(
			pap.getPrintable("list_name", ""),
			"id: Last Name, First Name (2011) Paper Title\n\t" +
			"Journal Title, 2, 1.\n"
		);
	}
	
	@Test(dependsOnMethods = {"setDataSet"})
	public void updateField() {
		JournalPaper pap = new JournalPaper("id", "type");
		pap.setDataSet(
			"list_name", "Journal Title", "Paper Title",
			"First Name", "Last Name", 2011, 2, 1, 1
		);		pap.updateField("list_name", "author_first", "New First");
		pap.updateField("list_name", "author_last", "New Last");
		pap.updateField("list_name", "title", "New Title");
		pap.updateField("list_name", "year", 2222);
		pap.updateField("list_name", "start_page", 3);
		pap.updateField("list_name", "end_page", 5);
		pap.updateField("list_name", "volume", 22);
		pap.updateField("list_name", "paper_title", "New Journal Title");
		
		Assert.assertEquals(pap.getAuthorFirst("list_name"), "New First");
		Assert.assertEquals(pap.getAuthorLast("list_name"), "New Last");
		Assert.assertEquals(pap.getTitle("list_name"), "New Title");
		Assert.assertEquals(pap.getPaperTitle("list_name"), "New Journal Title");
		Assert.assertEquals(pap.getYear("list_name"), 2222);
		Assert.assertEquals(pap.getVolume("list_name"), 22);
		Assert.assertEquals(pap.getPages("list_name"), "3-5");
	}
}
