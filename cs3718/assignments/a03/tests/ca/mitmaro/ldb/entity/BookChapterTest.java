package ca.mitmaro.ldb.entity;

import org.testng.Assert;
import org.testng.annotations.Test;

import ca.mitmaro.ldb.exception.InvalidListException;

public class BookChapterTest {

	@Test(expectedExceptions= {InvalidListException.class})
	public void addToList_missingOldList() {
		BookChapter chap = new BookChapter("id", "type");
		chap.addToList("new_list", "old_list");
	}

	@Test
	public void setDataSet() {
		BookChapter chap = new BookChapter("id", "type");
		chap.setDataSet(
			"list_name", "A Title", "First Name", "Last Name",
			2011, "My Address", "My Editors", "My Publisher",
			"My Chapter Title", 1, 1
		);
		Assert.assertEquals(chap.getTitle("list_name"), "A Title");
		Assert.assertEquals(chap.getAuthorFirst("list_name"), "First Name");
		Assert.assertEquals(chap.getAuthorLast("list_name"), "Last Name");
		Assert.assertEquals(chap.getYear("list_name"), 2011);
		Assert.assertEquals(chap.getAddress("list_name"), "My Address");
		Assert.assertEquals(chap.getPublisher("list_name"), "My Publisher");
		Assert.assertEquals(chap.getEditors("list_name"), "My Editors");
		Assert.assertEquals(chap.getPages("list_name"), "1");
		Assert.assertEquals(chap.getChapterTitle("list_name"), "My Chapter Title");
		
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
		BookChapter chap = new BookChapter("id", "type");
		chap.setDataSet(
			"list_name", "A Title", "First Name", "Last Name",
			2011, "My Address", "My Editors", "My Publisher",
			"My Chapter Title", 1, 2
		);
		Assert.assertEquals(
			chap.getPrintable("list_name", ""),
			"id: Last Name, First Name (2011) My Chapter Title\n\t" +
			"In My Editors (eds.) A Title\n\t" +
			"My Publisher; My Address. 1-2.\n"
		);
	}
	
	@Test(dependsOnMethods = {"setDataSet"})
	public void updateField() {
		BookChapter chap = new BookChapter("id", "type");
		chap.setDataSet(
			"list_name", "A Title", "First Name", "Last Name",
			2011, "My Address", "My Editors", "My Publisher",
			"My Chapter Title", 1, 1
		);
		chap.updateField("list_name", "author_first", "New First");
		chap.updateField("list_name", "author_last", "New Last");
		chap.updateField("list_name", "title", "New Title");
		chap.updateField("list_name", "year", 2222);
		chap.updateField("list_name", "address", "New Address");
		chap.updateField("list_name", "publisher", "New Publisher");
		chap.updateField("list_name", "editors", "New Editors");
		chap.updateField("list_name", "start_page", 3);
		chap.updateField("list_name", "end_page", 5);
		chap.updateField("list_name", "chapter_title", "New Chapter Title");
		
		Assert.assertEquals(chap.getAuthorFirst("list_name"), "New First");
		Assert.assertEquals(chap.getAuthorLast("list_name"), "New Last");
		Assert.assertEquals(chap.getTitle("list_name"), "New Title");
		Assert.assertEquals(chap.getYear("list_name"), 2222);
		Assert.assertEquals(chap.getAddress("list_name"), "New Address");
		Assert.assertEquals(chap.getPublisher("list_name"), "New Publisher");
		Assert.assertEquals(chap.getEditors("list_name"), "New Editors");
		Assert.assertEquals(chap.getChapterTitle("list_name"), "New Chapter Title");
		Assert.assertEquals(chap.getPages("list_name"), "3-5");
	}
}
