package ca.mitmaro.ldb;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import junit.framework.Assert;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.commandline.userinterface.NumberedMenu;
import ca.mitmaro.commandline.userinterface.SimplePrompt;
import ca.mitmaro.io.FileUtil;
import ca.mitmaro.ldb.CommandLineInterface.StatusCode;
import ca.mitmaro.ldb.entity.Paper;
import ca.mitmaro.ldb.entity.UpdateContext;
import ca.mitmaro.ldb.exception.InvalidListException;
import ca.mitmaro.ldb.exception.MissingPaperException;

import static org.mockito.Mockito.*;

public class CommandsTest {
	
	Application application;
	Terminal terminal;
	Commands commands;
	FileUtil file_util;
	
	@BeforeTest
	public void beforeClass() {
		this.application = mock(Application.class/*, withSettings().verboseLogging()*/);
		this.terminal = mock(Terminal.class);
		this.file_util = mock(FileUtil.class);
		when(this.terminal.out()).thenReturn(mock(PrintWriter.class));
		when(this.terminal.err()).thenReturn(mock(PrintWriter.class));
		this.commands = new Commands(this.application, this.terminal, this.file_util);
	}

	@Test
	public void addPaperCommand() throws InvalidListException {
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
	public void addReferenceCommand() throws InvalidListException {
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
	public void editPaperCommand() throws IOException, MissingPaperException{
		NumberedMenu menu = mock(NumberedMenu.class);
		SimplePrompt prompt = mock(SimplePrompt.class);
		Paper paper = mock(Paper.class);
		this.commands.setEditMenu(menu);
		this.commands.setEditPrompt(prompt);
		
		when(this.application.getPaper("P001")).thenReturn(paper);
		when(paper.getFields()).thenReturn(
			new String[]{
				"Author", "Year", "Book Title", "Address", "Publisher", "Title", 
				"Chapter Title", "Journal Title", "Pages", "Editors", "Paper Title"
			}
		);
		String args[] = {"P001", "in", "list_name"};
		
		UpdateContext context = new UpdateContext();
		
		this.commands.setUpdateContext(context);
		
		when(menu.waitForResponse())
			.thenReturn("Author")
			.thenReturn("Year")
			.thenReturn("Book Title")
			.thenReturn("Address")
			.thenReturn("Publisher")
			.thenReturn("Title")
			.thenReturn("Chapter Title")
			.thenReturn("Journal Title")
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
			.thenReturn("Journal Title")
			.thenReturn("1")
			.thenReturn("Editors")
			.thenReturn("Paper Title")
		;
		this.commands.editPaperCommand(args);

		Assert.assertEquals("First", context.author_first);
		Assert.assertEquals("Last", context.author_last );
		Assert.assertEquals(2000, context.year );
		Assert.assertEquals("Book Title", context.book_title );
		Assert.assertEquals("Address", context.address );
		Assert.assertEquals("Publisher", context.publisher );
		Assert.assertEquals("Paper Title", context.title );
		Assert.assertEquals("Chapter Title", context.chapter_title );
		Assert.assertEquals("Journal Title", context.journal_title );
		Assert.assertEquals(1, context.start_page );
		Assert.assertEquals(1, context.end_page );
		Assert.assertEquals("Editors", context.editors );
		
		verify(this.application).updatePaper(paper, "list_name", context);
	}

	@Test(expectedExceptions= {IllegalArgumentException.class})
	public void editPaperCommand_missingPaper() throws IOException, MissingPaperException{
		when(this.application.getPaper("P001")).thenReturn(null);

		String args[] = {"P001", "in", "list_name"};
		this.commands.editPaperCommand(args);
	}
	
	@Test
	public void listPaperListsCommand() {
		String args[] = {};
		Assert.assertEquals(StatusCode.OK, this.commands.listPaperListsCommand(args));
		verify(this.application).getFileListNames();
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
		verify(this.application).getFileReferenceListNames();
	}

	@Test(expectedExceptions= {IllegalArgumentException.class})
	public void listReferenceListsCommand_invalid() {
		String args[] = {"invalid arg"};
		this.commands.listReferenceListsCommand(args);
	}

	@Test
	public void loadPaperListCommand() throws IOException {
		String args[] = {"file_path", "as", "list_name"};
		File f = mock(File.class);
		when(f.canRead()).thenReturn(true);
		when(f.isFile()).thenReturn(true);
		when(this.file_util.openFile("file_path")).thenReturn(f);
		Assert.assertEquals(StatusCode.OK, this.commands.loadPaperListCommand(args));
		verify(this.application).loadPaperDescriptionListFile(f, "list_name");
	}

	@Test(expectedExceptions= {IllegalArgumentException.class})
	public void loadPaperListCommand_invalidArgs() throws IOException {
		String args[] = {"one arg"};
		this.commands.loadPaperListCommand(args);
	}

	@Test
	public void loadReferenceListCommand() throws IOException, MissingPaperException {
		String args[] = {"file_path", "as", "list_name"};
		File f = mock(File.class);
		when(f.canRead()).thenReturn(true);
		when(f.isFile()).thenReturn(true);
		when(this.file_util.openFile("file_path")).thenReturn(f);
		Assert.assertEquals(StatusCode.OK, this.commands.loadReferenceListCommand(args));
		verify(this.application).loadPaperReferenceListFile(f, "list_name");
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
