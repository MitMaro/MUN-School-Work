package ca.mitmaro.ldb.entity;

import org.testng.Assert;
import org.testng.annotations.Test;

import ca.mitmaro.ldb.exception.InvalidListException;

public class ConferencePaperTest {

	@Test(expectedExceptions = { InvalidListException.class })
	public void addToList_missingOldList() {
		ConferencePaper pap = new ConferencePaper("id", "type");
		pap.addToList("new_list", "old_list");
	}

	@Test
	public void setDataSet() {
		ConferencePaper pap = new ConferencePaper("id", "type");
		pap.setDataSet(
			"list_name", "A Title", "First Name", "Last Name",
			2011, "My Address", "My Editors", "My Publisher",
			"My Chapter Title", 1, 1
		);
		Assert.assertEquals(pap.getTitle("list_name"), "A Title");
		Assert.assertEquals(pap.getAuthorFirst("list_name"), "First Name");
		Assert.assertEquals(pap.getAuthorLast("list_name"), "Last Name");
		Assert.assertEquals(pap.getYear("list_name"), 2011);
		Assert.assertEquals(pap.getAddress("list_name"), "My Address");
		Assert.assertEquals(pap.getPublisher("list_name"), "My Publisher");
		Assert.assertEquals(pap.getEditors("list_name"), "My Editors");
		Assert.assertEquals(pap.getPages("list_name"), "1");
		Assert.assertEquals(pap.getChapterTitle("list_name"), "My Chapter Title");
		
	}

	@Test(dependsOnMethods = {"setDataSet"})
	public void getPages_samePage() {
		ConferencePaper pap = new ConferencePaper("id", "type");
		pap.setDataSet(
			"list_name", "A Title", "First Name", "Last Name",
			2011, "My Address", "My Editors", "My Publisher",
			"My Chapter Title", 1, 1
		);
		
		Assert.assertEquals(pap.getPages("list_name"), "1");
	}

	@Test(dependsOnMethods = {"setDataSet"})
	public void getPages_differentPage() {
		ConferencePaper pap = new ConferencePaper("id", "type");
		pap.setDataSet(
			"list_name", "A Title", "First Name", "Last Name",
			2011, "My Address", "My Editors", "My Publisher",
			"My Chapter Title", 1, 2
		);
		
		Assert.assertEquals(pap.getPages("list_name"), "1-2");
		
	}

	@Test(dependsOnMethods = {"setDataSet"})
	public void getPrintable() {
		ConferencePaper pap = new ConferencePaper("id", "type");
		pap.setDataSet(
			"list_name", "A Title", "First Name", "Last Name",
			2011, "My Address", "My Editors", "My Publisher",
			"My Chapter Title", 1, 2
		);
		Assert.assertEquals(
			pap.getPrintable("list_name", ""),
			"id: Last Name, First Name (2011) My Chapter Title\n\t" +
			"In My Editors (eds.) A Title\n\t" +
			"My Publisher; My Address. 1-2.\n"
		);
	}
	
	@Test(dependsOnMethods = {"setDataSet"})
	public void updateField() {
		ConferencePaper pap = new ConferencePaper("id", "type");
		pap.setDataSet(
			"list_name", "A Title", "First Name", "Last Name",
			2011, "My Address", "My Editors", "My Publisher",
			"My Chapter Title", 1, 1
		);
		pap.updateField("list_name", "author_first", "New First");
		pap.updateField("list_name", "author_last", "New Last");
		pap.updateField("list_name", "title", "New Title");
		pap.updateField("list_name", "year", 2222);
		pap.updateField("list_name", "address", "New Address");
		pap.updateField("list_name", "publisher", "New Publisher");
		pap.updateField("list_name", "editors", "New Editors");
		pap.updateField("list_name", "start_page", 3);
		pap.updateField("list_name", "end_page", 5);
		pap.updateField("list_name", "chapter_title", "New Chapter Title");
		
		Assert.assertEquals(pap.getAuthorFirst("list_name"), "New First");
		Assert.assertEquals(pap.getAuthorLast("list_name"), "New Last");
		Assert.assertEquals(pap.getTitle("list_name"), "New Title");
		Assert.assertEquals(pap.getYear("list_name"), 2222);
		Assert.assertEquals(pap.getAddress("list_name"), "New Address");
		Assert.assertEquals(pap.getPublisher("list_name"), "New Publisher");
		Assert.assertEquals(pap.getEditors("list_name"), "New Editors");
		Assert.assertEquals(pap.getChapterTitle("list_name"), "New Chapter Title");
		Assert.assertEquals(pap.getPages("list_name"), "3-5");
	}
}
