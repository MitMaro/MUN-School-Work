/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;

import ca.mitmaro.commandline.command.annotation.Command;
import ca.mitmaro.commandline.command.annotation.Help;
import ca.mitmaro.commandline.command.annotation.Helps;
import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.commandline.userinterface.NumberedMenu;
import ca.mitmaro.commandline.userinterface.SimplePrompt;
import ca.mitmaro.commandline.userinterface.YesNoQuestion;
import ca.mitmaro.io.FileUtil;
import ca.mitmaro.lang.StringUtils;
import ca.mitmaro.ldb.CommandLineInterface.StatusCode;
import ca.mitmaro.ldb.entity.Paper;
import ca.mitmaro.ldb.entity.UpdateContext;
import ca.mitmaro.ldb.exception.InvalidListException;
import ca.mitmaro.ldb.exception.MissingPaperException;

/**
 * Contains a series of command methods
 *
 * @author Tim Oram (MitMaro)
 */
public class Commands {
	
	/**
	 * The application instance
	 */
	private Application application;
	/**
	 * The terminal interface
	 */
	private Terminal terminal;
	/**
	 * The file utility interface
	 */
	private FileUtil file_util;
	
	/**
	 * The edit menu instance
	 */
	private NumberedMenu edit_menu;
	/**
	 * The edit menu prompt instance
	 */
	private SimplePrompt edit_prompt;
	/**
	 * The exit prompt instance
	 */
	private YesNoQuestion exit_prompt;
	/**
	 * The update context
	 */
	private UpdateContext context;
	
	/**
	 * Construct a command class instance
	 * @param application The application instance
	 * @param terminal The terminal interface
	 * @param file_util The file utilities interface
	 */
	public Commands(Application application, Terminal terminal, FileUtil file_util) {
		this.application = application;
		this.terminal = terminal;
		this.file_util = file_util;
		this.edit_menu = new NumberedMenu(this.terminal);
		this.edit_prompt = new SimplePrompt("New Value:", this.terminal);
		this.context = new UpdateContext();

		this.exit_prompt = new YesNoQuestion(terminal);
		this.exit_prompt.setTitle("Are you sure you wish to exit?");
	}
	
	/**
	 * Set the edit menu instance
	 * @param menu The new instance
	 */
	public void setEditMenu(NumberedMenu menu) {
		this.edit_menu = menu;
	}
	
	/**
	 * Set the edit prompt instance
	 * @param prompt The new instance
	 */
	public void setEditPrompt(SimplePrompt prompt) {
		this.edit_prompt = prompt;
	}
	
	/**
	 * Set the update context instance
	 * @param context
	 */
	public void setUpdateContext(UpdateContext context) {
		this.context = context;
	}

	/**
	 * Tha add papers command
	 * @param args An array or arguments
	 * @return
	 */
	@Command(name="addpap")
	@Help(
		usage="addpap NAME",
		help="Add the paper description list stored by name NAME to the" +
		" master paper description list."
	)
	public int addPaperCommand(String[] args) {
		
		if (args.length != 1) {
			throw new IllegalArgumentException("Missing parameter.");
		}
		
		String name = args[0];
		try {
			this.application.addPapersToMasterList(name);
		} catch (InvalidListException e) {
			this.terminal.out().println(e.getMessage());
			return StatusCode.MISSING_LIST;
		}
		return StatusCode.OK;
	}
	
	/**
	 * The add reference command
	 * @param args An array or arguments
	 * @return
	 */
	@Command(name="addref")
	@Help(
		usage="addref NAME",
		help="Add the paper reference list stored by name NAME to the" +
		" master paper reference list."
	)
	public int addReferenceCommand(String[] args) {
		
		if (args.length != 1) {
			throw new IllegalArgumentException("Missing parameter.");
		}
		
		String name = args[0];
		try {
			this.application.addReferencesToMasterList(name);
		} catch (InvalidListException e) {
			this.terminal.out().println(e.getMessage());
			return StatusCode.MISSING_LIST;
		}
		return StatusCode.OK;
	}
	
	/**
	 * The clear paper command
	 * @param args An array or arguments
	 * @return
	 */
	@Command(name="clearpap")
	@Help(
		usage="clearpap",
		help="Remove all entries from the master paper description list."
	)
	public int clearPaperCommand(String[] args) {

		if (args != null && args.length != 0) {
			throw new IllegalArgumentException("Unkown Parameter");
		}
		
		this.application.clearMasterPaperList();
		return StatusCode.OK;
	}

	
	/**
	 * The clear reference command
	 * @param args An array or arguments
	 * @return
	 */
	@Command(name="clearref")
	@Help(
		usage="clearref",
		help="Remove all entries from the master paper reference list."
	)
	public int clearReferenceCommand(String[] args) {
		
		if (args != null && args.length != 0) {
			throw new IllegalArgumentException("Unkown Parameter");
		}
		
		this.application.clearMasterReferenceList();
		return StatusCode.OK;
	}
	
	/**
	 * The edit paper reference
	 * @param args An array of arguments
	 * @return
	 */
	@Command(name="editpap")
	@Helps({
		@Help(
			usage="editpap PID",
			help="Add the paper reference list stored by name NAME to the" +
			" master paper reference list."
		),
		@Help(
			usage="editpap PID in NAME",
			help="Edit any data fields (except PID) associated with the" +
			" paper with paper ID PID in the paper description list stored" +
			" by name NAME."
		)
	})
	public int editPaperCommand(String[] args) {
		
		Paper paper;
		String list_name = null;
		if (args.length != 1 && (args.length != 3 || !args[1].equals("in"))) {
			throw new IllegalArgumentException("Missing parameter.");
		}
		
		if (args.length == 3) {
			list_name = args[2];
		} else {
			list_name = this.application.getMasterListName();
		}
		
		this.terminal.out().format("Paper: %s\n", args[0]);
		try {
			paper = this.application.getPaper(args[0]);
		} catch (MissingPaperException e) {
			this.terminal.out().println(e.getMessage());
			return StatusCode.MISSING_PAPER;
		}
		if (paper == null) {
			throw new IllegalArgumentException("Invalid paper.");
		}
		
		this.edit_menu.setOptions(Arrays.asList(paper.getFields()));
		this.edit_menu.disableAbortPrompt();
		this.edit_menu.setTitle("Select data field to edit (empty input to finish):");
		
		String field = null;
		String new_value;
		String[] tmp;
		this.context.reset();
		
		do {
			try {
				field = this.edit_menu.waitForResponse();
				// break out when no field selected
				if (field == null) {
					break;
				}
				
				if (field.equals("Author")) {
					this.edit_prompt.setTitle("New Value (Format: last, first)");
				} else if (field.equals("Pages")) {
					this.edit_prompt.setTitle("New Value (Format: # or #-#)");
				} else {
					this.edit_prompt.setTitle("New Value");
				}
				
				new_value = this.edit_prompt.waitForResponse();
			} catch (IOException e) {
				this.terminal.err().println("IOError Error: " + e.getMessage());
				return StatusCode.IO_ERROR;
			}
			
			try {
				if (field.equals("Author")) {
					if (new_value.contains(",")) {
						tmp = new_value.split(",", 2);
						this.context.author_first = tmp[1].trim();
						this.context.author_last = tmp[0].trim();
					} else {
						this.terminal.out().println("Invalid input for author name");
					}
				} else if (field.equals("Year")) {
					this.context.year = Integer.parseInt(new_value);
				} else if (field.equals("Volume")) {
					this.context.volume = Integer.parseInt(new_value);
				} else if (field.equals("Book Title")) {
					this.context.book_title =  new_value;
				} else if (field.equals("Address")) {
					this.context.address = new_value;
				} else if (field.equals("Publisher")) {
					this.context.publisher = new_value;
				} else if (field.equals("Title")) {
					this.context.title = new_value;
				} else if (field.equals("Journal Title")) {
					this.context.journal_title = new_value;
				} else if (field.equals("Chapter Title")) {
					this.context.chapter_title = new_value;
				} else if (field.equals("Pages")) {
					if (new_value.contains("-")) {
						tmp = new_value.split("-", 2);
						this.context.start_page = Integer.parseInt(tmp[0]);
						this.context.end_page = Integer.parseInt(tmp[1]);
					} else {
						this.context.start_page = this.context.end_page = Integer.parseInt(new_value);
					}
				} else if (field.equals("Editors")) {
					this.context.editors = new_value;
				} else if (field.equals("Paper Title")) {
					this.context.paper_title = new_value;
				} else {
					this.terminal.err().println(String.format("Missing Update: %s", field));
				}
			} catch (NumberFormatException e) {
				this.terminal.out().println("Invalid input provided.");
			}
			
			
		} while(true);
		
		this.terminal.out().println("Finished Editing");
		
		this.application.updatePaper(paper, list_name, this.context);
		
		return StatusCode.OK;
		
	}
	
	/**
	 * The load paper command
	 * @param args An array or arguments
	 * @return
	 */
	@Command(name="loadpap")
	@Help(
		usage="loadpap FILE as NAME",
		help="Load a paper description list stored in file FILE into the" +
		" system and store under name NAME."
	)
	public int loadPaperListCommand(String[] args) {
		
		if (args == null || args.length < 3) {
			throw new IllegalArgumentException("Missing parameters.");
		}
		
		if (args.length > 3) {
			args[2] = StringUtils.join(Arrays.copyOfRange(args, 2, args.length));
		}
		
		
		if (!args[1].equals("as")) {
			throw new IllegalArgumentException("Invalid parameter: " + args[1]);
		}
		
		String filepath = args[0];
		
		File file = this.file_util.openFile(filepath);
		
		if (!file.isFile() || !file.canRead()) {
			throw new IllegalArgumentException("Invalid filepath provided: " + filepath);
		}
		
		String name = args[2];
		try {
			this.application.loadPaperDescriptionListFile(file, name);
		} catch (IOException e) {
			this.terminal.err().println("IOError Error: " + e.getMessage());
			return StatusCode.IO_ERROR;
		}
		
		return StatusCode.OK;
	}

	/**
	 * The load reference command
	 * @param args An array or arguments
	 * @return
	 */
	@Command(name="loadref")
	@Help(
		usage="loadref FILE as NAME",
		help="Load a paper reference list stored in file FILE into the" +
		" system and store under name NAME."
	)
	public int loadReferenceListCommand(String[] args) {
		if (args == null || args.length < 3) {
			throw new IllegalArgumentException("Missing parameters.");
		}
		
		if (args.length > 3) {
			args[2] = StringUtils.join(Arrays.copyOfRange(args, 2, args.length));
		}
		
		if (!args[1].equals("as")) {
			throw new IllegalArgumentException("Invalid parameter: " + args[1]);
		}
		
		String filepath = args[0];
		
		File file = this.file_util.openFile(filepath);
		
		if (!file.isFile() || !file.canRead()) {
			throw new IllegalArgumentException("Invalid filepath provided: " + filepath);
		}
		
		String name = args[2];
		
		try {
			this.application.loadPaperReferenceListFile(file, name);
		} catch (IOException e) {
			this.terminal.err().println("IOError Error: " + e.getMessage());
			return StatusCode.IO_ERROR;
		} catch (MissingPaperException e) {
			this.terminal.err().println(e.getMessage());
			return StatusCode.MISSING_PAPER;
		}
		
		return StatusCode.OK;
	}

	/**
	 * The list paper command
	 * @param args An array or arguments
	 * @return
	 */
	@Command(name="listpap")
	@Help(
		usage="listpap",
		help="List all paper description lists stored in the system."
	)
	public int listPaperListsCommand(String[] args) {
		if (args != null && args.length != 0) {
			throw new IllegalArgumentException("Invalid parameter(s).");
		}
		
		int i = 1;
		for (String list_name: this.application.getFileListNames()) {
			this.terminal.out().println(String.format("\t%d: %s", i, list_name));
			i++;
		}
		
		return StatusCode.OK;
	}

	/**
	 * The list reference command
	 * @param args An array or arguments
	 * @return
	 */
	@Command(name="listref")
	@Help(
		usage="listref",
		help="List all paper reference lists stored in the system."
	)
	public int listReferenceListsCommand(String[] args) {
		if (args != null && args.length != 0) {
			throw new IllegalArgumentException("Invalid parameter(s).");
		}
		
		int i = 1;
		for (String list_name: this.application.getFileReferenceListNames()) {
			this.terminal.out().println(String.format("\t%d: %s", i, list_name));
			i++;
		}
		
		return StatusCode.OK;
	}
	
	/**
	 * The print paper command
	 * @param args An array or arguments
	 * @return
	 */
	@Command(name="printpap")
	@Helps({
		@Help(
			usage="printpap",
			help="Print the master paper description list."
		),
		@Help(
			usage="printpap NAME",
			help="Print the paper description list stored by name NAME."
		)
	})
	public int printPaperCommand(String[] args) {
		
		LinkedHashSet<Paper> papers;
		String list_name;

		try {
			if (args == null || args.length == 0) {
				papers = this.application.getMasterListPapers();
				list_name = this.application.getMasterListName();
			} else if (args.length == 1) {
				papers = this.application.getPaperList(args[0]);
				list_name = args[0];
			} else {
				throw new IllegalArgumentException("Invalid parameter(s).");
			}
			for (Paper paper: papers) {
				this.printPaper(paper, list_name, false, false);
			}
		} catch (InvalidListException e) {
			this.terminal.out().println(e.getMessage());
			return StatusCode.MISSING_LIST;
		}
		
		return StatusCode.OK;
	}
	
	/**
	 * The print reference command
	 * @param args An array or arguments
	 * @return
	 */
	@Command(name="printref")
	@Helps({
		@Help(
			usage="printref",
			help="Print the master paper reference list."
		),
		@Help(
			usage="printref NAME",
			help="Print the paper reference list stored by name NAME."
		)
	})
	public int printReferenceCommand(String[] args) {
		
		LinkedHashSet<Paper> papers;
		String list_name;
		
		try {
			if (args == null || args.length == 0) {
				papers = this.application.getMasterListPapers();
				list_name = this.application.getMasterListName();
			} else if (args.length == 1) {
				papers = this.application.getPaperList(args[0]);
				list_name = args[0];
			} else {
				throw new IllegalArgumentException("Invalid parameter(s).");
			}
			
			for (Paper paper: papers) {
				this.printPaper(paper, list_name, true, false);
			}
			
		} catch (InvalidListException e) {
			this.terminal.out().println(e.getMessage());
			return StatusCode.MISSING_LIST;
		}
		return StatusCode.OK;
	}

	/**
	 * The print current working paper command
	 * @param args An array or arguments
	 * @return
	 */
	@Command(name="printcwp")
	@Help(
		usage="printcwp",
		help="Print current working paper and its references."
	)
	public int printCurrentWorkingPaperCommand(String[] args) {
		if (args != null && args.length != 0) {
			throw new IllegalArgumentException("Invalid parameter(s).");
		}
		try {
			this.printPaper(this.application.getCurrentWorkingPaper(), this.application.getMasterListName(), true, true);
		} catch (InvalidListException e) {
			this.terminal.out().println(e.getMessage());
			return StatusCode.MISSING_LIST;
		}
		return StatusCode.OK;
	}
	
	/**
	 * The set current working paper command
	 * @param args An array or arguments
	 * @return
	 */
	@Command(name="setcwp")
	@Helps({
		@Help(
			usage="setcwp PID",
			help="Set current working paper to paper in master paper" +
			" description list with paper ID PID."
		),
		@Help(
			usage="setcwp RX",
			help="Set current working paper to reference X (1-indexed)" +
			" of the current working paper."
		),
		@Help(
			usage="setcwp CX",
			help="Set current working paper to citing-paper X (1-indexed) of the " +
			"current working paper."
		)
	})
	public int setCurrentWorkingPaperCommand(String[] args) {
		
		if (args.length != 1) {
			throw new IllegalArgumentException("Missing parameter.");
		}
		
		String id = args[0];
		try {
			this.application.setCurrentWorkingPaper(id);
		} catch (MissingPaperException e) {
			this.terminal.out().println(e.getMessage());
			return StatusCode.MISSING_PAPER;
		}
		return StatusCode.OK;
	}
	
	/**
	 * Prompts the user to confirm quit. Returns a 0 if quit was a confirm, else returns
	 * a 1.
	 * 
	 * @param args The arguments passed to this command
	 * @return 1 is the command was successful, a number > 1 for an error and 0 for a non-error quit.
	 */
	@Command(name="sortpap")
	@Helps({
		@Help(
			usage="sortpap by {\"PID\", \"author\"}",
			help="Sort the papers in the master paper description list by author name or PID."
		),
		@Help(
			usage="sortpap in NAME by {\"PID\", \"author\"}",
			help="Sort the papers in the paper description list stored by name NAME by author name or PID."
		)
	})
	public int sortCommand(String[] args) {
		
		if (args.length == 2) {
			if (args[1].equals("PID")) {
				this.application.sortMasterListByPID();
			} else if (args[1].equals("author")) {
				this.application.sortMasterListByAuthor();
			}
		} else if (args.length == 4) {
			try {
				if (args[1].equals("PID")) {
					this.application.sortPaperListByPID(args[1]);
				} else if (args[1].equals("author")) {
					this.application.sortPaperListByAuthor(args[1]);
				}
			} catch (InvalidListException e) {
				this.terminal.out().println(e.getMessage());
				return StatusCode.MISSING_LIST;
			}
		} else {
			throw new IllegalArgumentException("Invalid parameters.");
		}
		return StatusCode.OK;
	}
	
	/**
	 * Prompts the user to confirm quit. Returns a 0 if quit was a confirm, else returns
	 * a 1.
	 * 
	 * @param args The arguments passed to this command
	 * @return StatusCode.OK if confirm quit was unsuccessful else StatusCode.EXIT
	 * @throws IllegalArgumentException is provided arguments are invalid
	 */
	@ca.mitmaro.commandline.command.annotation.Command(name="exit")
	@ca.mitmaro.commandline.command.annotation.Help(usage="exit", help="Exit the application.")
	public int exitCommand(String[] args) {
		
		try {
			if (this.exit_prompt.waitForResponse()) {
				this.application.saveState();
				return StatusCode.EXIT;
			}
		} catch (IOException e) {
			this.terminal.out().println("An error occured. Aborting quit.");
			e.printStackTrace(this.terminal.err());
			return StatusCode.FATAL_ERROR;
		}
		return StatusCode.OK;
	}
	
	
	/**
	 * Prints the provided paper to the terminal outout
	 * @param paper The paper to print
	 * @param list_name The list to print from
	 * @param include_refs To include or exclude references
	 * @param include_cits To include or exclude citations
	 * @throws InvalidListException If the list name doesn't exists
	 */
	private void printPaper(Paper paper, String list_name, boolean include_refs, boolean include_cits) throws InvalidListException {
		
		this.terminal.out().println(paper.getPrintable(list_name, ""));
		if (include_refs) {

			if (paper.getReferences().isEmpty()) {
				this.terminal.out().println("\n\tNO REFERENCES\n");
				
			} else {
				this.terminal.out().println("\n\tREFERENCES");
				
				int index = 0;
			
				for (Paper reference: paper.getReferences()) {
					this.terminal.out().format("\tR%d: ", index);
					try {
						this.terminal.out().println(reference.getPrintable(list_name, "\t"));
					} catch (InvalidListException e) {
						try {
							this.terminal.out().println(reference.getPrintable(this.application.getMasterListName(), "\t"));
						} catch (InvalidListException e2) {
							this.terminal.out().format("%s doesn't exist in list master list\n\n", reference.getId());
						}
					}
					index++;
				}
			}
		}
		if (include_cits) {
			if (paper.getCitations().isEmpty()) {
				this.terminal.out().println("\n\tNO CITATIONS\n");
				
			} else {
				this.terminal.out().println("\n\tCITATIONS");
				
				int index = 0;
			
				for (Paper citation: paper.getCitations()) {
					this.terminal.out().format("\tC%d: ", index);
					try {
						this.terminal.out().println(citation.getPrintable(list_name, "\t"));
					} catch (InvalidListException e) {
						try {
							this.terminal.out().println(citation.getPrintable(this.application.getMasterListName(), "\t"));
						} catch (InvalidListException e2) {
							this.terminal.out().format("%s doesn't exist in list master list\n\n", citation.getId());
						}
					}
					index++;
				}
			}
		}
	}
}
