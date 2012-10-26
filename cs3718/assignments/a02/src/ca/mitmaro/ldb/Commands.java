/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import ca.mitmaro.commandline.command.annotation.Command;
import ca.mitmaro.commandline.command.annotation.Help;
import ca.mitmaro.commandline.command.annotation.Helps;
import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.commandline.userinterface.NumberedMenu;
import ca.mitmaro.commandline.userinterface.SimplePrompt;
import ca.mitmaro.lang.StringUtils;
import ca.mitmaro.ldb.CommandLineInterface.StatusCode;
import ca.mitmaro.ldb.entity.Paper;
import ca.mitmaro.ldb.exception.MissingPaperException;

public class Commands {
	
	private Application application;
	private Terminal terminal;
	
	private NumberedMenu edit_menu;
	private SimplePrompt edit_prompt;
	
	public Commands(Application application, Terminal terminal) {
		this.application = application;
		this.terminal = terminal;
		this.edit_menu = new NumberedMenu(this.terminal);
		this.edit_prompt = new SimplePrompt("New Value:", this.terminal);
	}
	
	public void setEditMenu(NumberedMenu menu) {
		this.edit_menu = menu;
	}
	
	public void setEditPrompt(SimplePrompt prompt) {
		this.edit_prompt = prompt;
	}

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
		this.application.addPapersToMasterList(name);
		return StatusCode.OK;
	}
	
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
		this.application.addReferencesToMasterList(name);
		return StatusCode.OK;
	}
	
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
		}
		
		paper = this.application.getPaper(args[0]);
		if (paper == null) {
			throw new IllegalArgumentException("Invalid paper.");
		}
		String type = paper.getType();
		ArrayList<String> options = new ArrayList<String>();
		
		// edit menu field options
		options.add("Author");
		options.add("Year");
		if (type.equals("book")) {
			options.add("Book Title");
			options.add("Address");
			options.add("Publisher");
		} else if (type.equals("phd thesis")) {
			options.add("Title");
			options.add("Publisher");
		} else if (type.equals("book chapter") || type.equals("conference paper")) {
			options.add("Book Title");
			options.add("Chapter Title");
			options.add("Pages");
			options.add("Address");
			options.add("Publisher");
			options.add("Editors");
		} else if (type.equals("journal paper")) {
			options.add("Paper Title");
			options.add("Volume");
			options.add("Pages");
		}
		this.edit_menu.setOptions(options);
		this.edit_menu.disableAbortPrompt();
		this.edit_menu.setTitle("Select data field to edit (empty input to finish):");
		
		String field = null;
		String new_value;
		String[] tmp;
		do {
			try {
				field = this.edit_menu.waitForResponse();
				// break out when no field selected
				if (field == null) {
					break;
				}
				
				if (field.equals("Author")) {
					this.edit_prompt.setTitle("New Value (Format: last, first)");
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
						this.application.updatePaper(list_name, args[0], "author_last", tmp[0].trim());
						this.application.updatePaper(list_name, args[0], "author_first", tmp[1].trim());
					} else {
						this.terminal.out().println("Invalid input for author name");
					}
				} else if (field.equals("Year")) {
					this.application.updatePaper(list_name, args[0], "year", Integer.parseInt(new_value));
				} else if (field.equals("Book Title")) {
					this.application.updatePaper(list_name, args[0], "book_title", new_value);
				} else if (field.equals("Address")) {
					this.application.updatePaper(list_name, args[0], "address", new_value);
				} else if (field.equals("Publisher")) {
					this.application.updatePaper(list_name, args[0], "publisher", new_value);
				} else if (field.equals("Title")) {
					this.application.updatePaper(list_name, args[0], "title", new_value);
				} else if (field.equals("Chapter Title")) {
					this.application.updatePaper(list_name, args[0], "chapter_title", new_value);
				} else if (field.equals("Pages")) {
					if (new_value.contains("-")) {
						tmp = new_value.split("-", 2);
						this.application.updatePaper(list_name, args[0], "page_start", Integer.parseInt(tmp[0]));
						this.application.updatePaper(list_name, args[0], "page_end", Integer.parseInt(tmp[1]));
					} else {
						this.application.updatePaper(list_name, args[0], "page_start", Integer.parseInt(new_value));
						this.application.updatePaper(list_name, args[0], "page_end", Integer.parseInt(new_value));
					}
				} else if (field.equals("Editors")) {
					this.application.updatePaper(list_name, args[0], "editors", new_value);
				} else if (field.equals("Paper Title")) {
					this.application.updatePaper(list_name, args[0], "paper_title", new_value);
				}
			} catch (NumberFormatException e) {
				this.terminal.out().println("Invalid input provided.");
			}
			
			
		} while(true);
		
		
		return StatusCode.OK;
		
	}
	
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
		
		File f = new File(filepath);
		
		if (!f.isFile() || !f.canRead()) {
			throw new IllegalArgumentException("Invalid filepath provided: " + filepath);
		}
		
		String name = args[2];
		try {
			this.application.loadPaperDescriptionListFile(filepath, name);
		} catch (IOException e) {
			this.terminal.err().println("IOError Error: " + e.getMessage());
			return StatusCode.IO_ERROR;
		}
		
		return StatusCode.OK;
	}

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
		
		File f = new File(filepath);
		
		if (!f.isFile() || !f.canRead()) {
			throw new IllegalArgumentException("Invalid filepath provided: " + filepath);
		}
		
		String name = args[2];
		
		try {
			this.application.loadPaperReferenceListFile(filepath, name);
		} catch (IOException e) {
			this.terminal.err().println("IOError Error: " + e.getMessage());
			return StatusCode.IO_ERROR;
		} catch (MissingPaperException e) {
			this.terminal.err().println(e.getMessage());
			return StatusCode.MISSING_PAPER;
		}
		
		return StatusCode.OK;
	}

	@Command(name="listpap")
	@Help(
		usage="listpap",
		help="List all paper description lists stored in the system."
	)
	public int listPaperListsCommand(String[] args) {
		if (args != null && args.length != 0) {
			throw new IllegalArgumentException("Invalid parameter(s).");
		}
		
		this.application.printPaperDescriptionLists();
		return StatusCode.OK;
	}

	@Command(name="listref")
	@Help(
		usage="listref",
		help="List all paper reference lists stored in the system."
	)
	public int listReferenceListsCommand(String[] args) {
		if (args != null && args.length != 0) {
			throw new IllegalArgumentException("Invalid parameter(s).");
		}
		
		this.application.printPaperReferenceLists();
		return StatusCode.OK;
	}
	
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
		if (args == null || args.length == 0) {
			this.application.printMasterPaperList();
		} else if (args.length == 1) {
			this.application.printPaperList(args[0]);
		}
		return StatusCode.OK;
	}
	
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
		if (args == null || args.length == 0) {
			this.application.printMasterPaperList(true);
		} else if (args.length == 1) {
			this.application.printPaperList(args[0], true);
		}
		return StatusCode.OK;
	}

	@Command(name="printcwp")
	@Help(
		usage="printcwp",
		help="Print current working paper and its references."
	)
	public int printCurrentWorkingPaperCommand(String[] args) {
		if (args != null && args.length != 0) {
			throw new IllegalArgumentException("Invalid parameter(s).");
		}
		
		this.application.printCurrentWorkingPaper();
		return StatusCode.OK;
	}
	
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
		this.application.setCurrentWorkingPaper(id);
		return StatusCode.OK;
	}
}
