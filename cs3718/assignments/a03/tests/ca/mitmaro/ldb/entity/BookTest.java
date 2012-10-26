package ca.mitmaro.ldb.entity;

import org.testng.Assert;
import org.testng.annotations.Test;

import ca.mitmaro.ldb.exception.InvalidListException;

public class BookTest {

	@Test(expectedExceptions= {InvalidListException.class})
	public void addToList_missingOldList() {
		Book pap = new Book("id", "type");
		pap.addToList("new_list", "old_list");
	}

	@Test
	public void setDataSet() {
		Book book = new Book("id", "type");
		book.setDataSet(
			"list_name", "A Title", "First Name", "Last Name",
			2011, "My Address", "My Publisher"
		);
		Assert.assertEquals(book.getTitle("list_name"), "A Title");
		Assert.assertEquals(book.getAuthorFirst("list_name"), "First Name");
		Assert.assertEquals(book.getAuthorLast("list_name"), "Last Name");
		Assert.assertEquals(book.getYear("list_name"), 2011);
		Assert.assertEquals(book.getAddress("list_name"), "My Address");
		Assert.assertEquals(book.getPublisher("list_name"), "My Publisher");
	}

	@Test(dependsOnMethods = {"setDataSet"})
	public void updateField() {
		Book book = new Book("id", "type");
		book.setDataSet(
			"list_name", "A Title", "First Name", "Last Name",
			2011, "My Address", "My Publisher"
		);
		book.updateField("list_name", "author_first", "New First");
		book.updateField("list_name", "author_last", "New Last");
		book.updateField("list_name", "title", "New Title");
		book.updateField("list_name", "year", 2222);
		book.updateField("list_name", "address", "New Address");
		book.updateField("list_name", "publisher", "New Publisher");
		Assert.assertEquals(book.getAuthorFirst("list_name"), "New First");
		Assert.assertEquals(book.getAuthorLast("list_name"), "New Last");
		Assert.assertEquals(book.getTitle("list_name"), "New Title");
		Assert.assertEquals(book.getYear("list_name"), 2222);
		Assert.assertEquals(book.getAddress("list_name"), "New Address");
		Assert.assertEquals(book.getPublisher("list_name"), "New Publisher");
	}

	@Test(dependsOnMethods = {"setDataSet"})
	public void getPrintable() {
		Book book = new Book("id", "type");
		book.setDataSet(
			"list_name", "A Title", "First Name", "Last Name",
			2011, "My Address", "My Publisher"
		);
		Assert.assertEquals(
			book.getPrintable("list_name", ""),
			"id: Last Name, First Name (2011) A Title\n\tMy Publisher; My Address.\n"
		);
	}
}
