package ca.mitmaro.ldb.entity;

import org.testng.Assert;
import org.testng.annotations.Test;

import ca.mitmaro.ldb.exception.InvalidListException;

public class PHDThesisTest {

	@Test(expectedExceptions = { InvalidListException.class })
	public void addToList_missingOldList() {
		PHDThesis thesis = new PHDThesis("id", "type");
		thesis.addToList("new_list", "old_list");
	}

	@Test
	public void setDataSet() {
		PHDThesis thesis = new PHDThesis("id", "type");
		thesis.setDataSet(
			"list_name", "My Title", "First Name", "Last Name", 2011, "My Address"
		);
		Assert.assertEquals(thesis.getTitle("list_name"), "My Title");
		Assert.assertEquals(thesis.getAuthorFirst("list_name"), "First Name");
		Assert.assertEquals(thesis.getAuthorLast("list_name"), "Last Name");
		Assert.assertEquals(thesis.getYear("list_name"), 2011);
		Assert.assertEquals(thesis.getAddress("list_name"), "My Address");
	}

	@Test(dependsOnMethods = {"setDataSet"})
	public void updateField() {
		PHDThesis thesis = new PHDThesis("id", "type");
		thesis.setDataSet(
				"list_name", "My Title", "First Name", "Last Name", 2011, "My Address"
			);
		thesis.updateField("list_name", "author_first", "New First");
		thesis.updateField("list_name", "author_last", "New Last");
		thesis.updateField("list_name", "title", "New Title");
		thesis.updateField("list_name", "year", 2222);
		thesis.updateField("list_name", "address", "New Address");
		Assert.assertEquals(thesis.getAuthorFirst("list_name"), "New First");
		Assert.assertEquals(thesis.getAuthorLast("list_name"), "New Last");
		Assert.assertEquals(thesis.getTitle("list_name"), "New Title");
		Assert.assertEquals(thesis.getYear("list_name"), 2222);
		Assert.assertEquals(thesis.getAddress("list_name"), "New Address");
	}

	@Test(dependsOnMethods = {"setDataSet"})
	public void getPrintable() {
		PHDThesis thesis = new PHDThesis("id", "type");
		thesis.setDataSet(
				"list_name", "My Title", "First Name", "Last Name", 2011, "My Address"
			);
		Assert.assertEquals(
			thesis.getPrintable("list_name", ""),
			"id: Last Name, First Name (2011) My Title\n\tMy Address.\n"
		);
	}
}
