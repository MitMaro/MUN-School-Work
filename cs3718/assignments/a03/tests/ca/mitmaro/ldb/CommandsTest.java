package ca.mitmaro.ldb;

import java.io.IOException;
import java.io.PrintWriter;

import junit.framework.Assert;

import org.mockito.InOrder;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.commandline.userinterface.NumberedMenu;
import ca.mitmaro.commandline.userinterface.SimplePrompt;
import ca.mitmaro.ldb.CommandLineInterface.StatusCode;
import ca.mitmaro.ldb.entity.Paper;
import ca.mitmaro.ldb.exception.MissingPaperException;

import static org.mockito.Mockito.*;

public class CommandsTest {
	
	Application application;
	Terminal terminal;
	Commands commands;
	
	@BeforeTest
	public void beforeClass() {
		this.application = mock(Application.class/*, withSettings().verboseLogging()*/);
		this.terminal = mock(Terminal.class);
		when(this.terminal.out()).thenReturn(mock(PrintWriter.class));
		when(this.terminal.err()).thenReturn(mock(PrintWriter.class));
		this.commands = new Commands(this.application, this.terminal);
	}

	@Test
	public void addPaperCommand() {
		String args[] = {"paper_name"};
		Assert.assertEquals(StatusCode.OK, this.commands.addPaperCommand(args));
		verify(this.application).addPapersToMasterList("paper_name");
	}

	@Test(expectedExceptions= {IllegalArgumentException.class})
	public void addPaperCommand_fail() {
		String args[] = {};
		this.commands.addPaperCommand(args);
	}

	@Test
	public void addReferenceCommand() {
		String args[] = {"ref_name"};
		Assert.assertEquals(StatusCode.OK, this.commands.addReferenceCommand(args));
		verify(this.application).addReferencesToMasterList("ref_name");
	}
	
	@Test(expectedExceptions= {IllegalArgumentException.class})
	public void addReferenceCommand_fail() {
		String args[] = {};
		this.commands.addPaperCommand(args);
	}
	
	@Test
	public void clearPaperCommand() {
		String args[] = {};
		Assert.assertEquals(StatusCode.OK, this.commands.clearPaperCommand(args));
		verify(this.application).clearMasterPaperList();
	}
	
	@Test(expectedExceptions= {IllegalArgumentException.class})
	public void clearPaperCommand_fail() {
		String args[] = {"bad arg"};
		this.commands.clearPaperCommand(args);
	}

	@Test
	public void clearReferenceCommand() {
		String args[] = {};
		Assert.assertEquals(StatusCode.OK, this.commands.clearReferenceCommand(args));
		verify(this.application).clearMasterReferenceList();
	}
	
	@Test(expectedExceptions= {IllegalArgumentException.class})
	public void clearReferenceCommand_fail() {
		String args[] = {"bad arg"};
		this.commands.clearReferenceCommand(args);
	}

	@Test
	public void editPaperCommand() throws IOException{
		NumberedMenu menu = mock(NumberedMenu.class);
		SimplePrompt prompt = mock(SimplePrompt.class);
		Paper paper = mock(Paper.class);
		this.commands.setEditMenu(menu);
		this.commands.setEditPrompt(prompt);
		
		when(this.application.getPaper("P001")).thenReturn(paper);
		when(paper.getType()).thenReturn("book");
		String args[] = {"P001", "in", "list_name"};
		when(menu.waitForResponse())
			.thenReturn("Author")
			.thenReturn("Year")
			.thenReturn("Book Title")
			.thenReturn("Address")
			.thenReturn("Publisher")
			.thenReturn("Title")
			.thenReturn("Chapter Title")
			.thenReturn("Pages")
			.thenReturn("Pages")
			.thenReturn("Editors")
			.thenReturn("Paper Title")
			.thenReturn(null);
		;
		when(prompt.waitForResponse())
			.thenReturn("Last, First")
			.thenReturn("2000")
			.thenReturn("Book Title")
			.thenReturn("Address")
			.thenReturn("Publisher")
			.thenReturn("Title")
			.thenReturn("Chapter Title")
			.thenReturn("1")
			.thenReturn("2-3")
			.thenReturn("Editors")
			.thenReturn("Paper Title")
		;
		this.commands.editPaperCommand(args);
		

		InOrder call_order = inOrder(this.application);
		call_order.verify(this.application).updatePaper("list_name", "P001", "author_last", "Last");
		call_order.verify(this.application).updatePaper("list_name", "P001", "author_first", "First");
		call_order.verify(this.application).updatePaper("list_name", "P001", "year", 2000);
		call_order.verify(this.application).updatePaper("list_name", "P001", "book_title", "Book Title");
		call_order.verify(this.application).updatePaper("list_name", "P001", "address", "Address");
		call_order.verify(this.application).updatePaper("list_name", "P001", "publisher", "Publisher");
		call_order.verify(this.application).updatePaper("list_name", "P001", "title", "Title");
		call_order.verify(this.application).updatePaper("list_name", "P001", "chapter_title", "Chapter Title");
		call_order.verify(this.application).updatePaper("list_name", "P001", "page_start", 1);
		call_order.verify(this.application).updatePaper("list_name", "P001", "page_end", 1);
		call_order.verify(this.application).updatePaper("list_name", "P001", "page_start", 2);
		call_order.verify(this.application).updatePaper("list_name", "P001", "page_end", 3);
		call_order.verify(this.application).updatePaper("list_name", "P001", "editors", "Editors");
		call_order.verify(this.application).updatePaper("list_name", "P001", "paper_title", "Paper Title");
	}
	
	@Test(expectedExceptions= {IllegalArgumentException.class})
	public void editPaperCommand_missingPaper() throws IOException{
		when(this.application.getPaper("P001")).thenReturn(null);

		String args[] = {"P001", "in", "list_name"};
		this.commands.editPaperCommand(args);
	}
	
	@Test
	public void listPaperListsCommand() {
		String args[] = {};
		Assert.assertEquals(StatusCode.OK, this.commands.listPaperListsCommand(args));
		verify(this.application).printPaperDescriptionLists();
	}

	@Test(expectedExceptions= {IllegalArgumentException.class})
	public void listPaperListsCommand_invalid() {
		String args[] = {"invalid arg"};
		this.commands.listPaperListsCommand(args);
	}
	
	@Test
	public void listReferenceListsCommand() {
		String args[] = {};
		Assert.assertEquals(StatusCode.OK, this.commands.listReferenceListsCommand(args));
		verify(this.application).printPaperReferenceLists();
	}

	@Test(expectedExceptions= {IllegalArgumentException.class})
	public void listReferenceListsCommand_invalid() {
		String args[] = {"invalid arg"};
		this.commands.listReferenceListsCommand(args);
	}

	@Test
	public void loadPaperListCommand() throws IOException {
		String args[] = {"file_path", "as", "list_name"};
		Assert.assertEquals(StatusCode.OK, this.commands.loadPaperListCommand(args));
		verify(this.application).loadPaperDescriptionListFile("file_path", "list_name");
	}

	@Test(expectedExceptions= {IllegalArgumentException.class})
	public void loadPaperListCommand_invalidArgs() throws IOException {
		String args[] = {"one arg"};
		this.commands.loadPaperListCommand(args);
	}

	@Test
	public void loadReferenceListCommand() throws IOException, MissingPaperException {
		String args[] = {"file_path", "as", "list_name"};
		Assert.assertEquals(StatusCode.OK, this.commands.loadReferenceListCommand(args));
		verify(this.application).loadPaperReferenceListFile("file_path", "list_name");
	}

	@Test(expectedExceptions= {IllegalArgumentException.class})
	public void loadReferenceListCommand_invalidArgs() throws IOException {
		String args[] = {"one arg"};
		this.commands.loadReferenceListCommand(args);
	}

	@Test
	public void printCurrentWorkingPaperCommand() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void printPaperCommand() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void printReferenceCommand() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void setCurrentWorkingPaperCommand() {
		throw new RuntimeException("Test not implemented");
	}
}
