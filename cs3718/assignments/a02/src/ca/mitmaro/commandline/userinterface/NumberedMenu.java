/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline.userinterface;

import java.util.ArrayList;
import java.util.Collection;
import java.io.IOException;

import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.commandline.userinterface.YesNoQuestion;

public class NumberedMenu extends TextBased<String> {
	
	private ArrayList<String> options;
	
	private String menu_cache = null;
	
	private String default_item = null;
	
	private YesNoQuestion abort_prompt;
	
	private boolean allow_abort = true;
	
	private boolean disable_abort_prompt = false;
	
	/**
	 * @param term The terminal interface
	 */
	public NumberedMenu(Terminal term) {
		super(term);
		this.abort_prompt = new YesNoQuestion(term);
		this.abort_prompt.setTitle("No option selected. Abort?");
		this.abort_prompt.setPrompt(" abort: ");
	}

	/**
	 * Sets the menu items/options that this menu will display
	 * @param options The menu items/options
	 */
	public void setOptions(Collection<String> options) {
		this.options = new ArrayList<String>(options);
	}
	
	/**
	 * Set the default option that is used when an empty input is provided
	 * 
	 * @param deflt The default menu item
	 */
	public void setDefaultOption(String deflt) {
		this.default_item = deflt;
	}
	
	/**
	 * Disable the prompt the appears when aborting the menu
	 */
	public void disableAbortPrompt() {
		this.disable_abort_prompt = true;
	}
	
	public void disableAbort() {
		this.allow_abort = false;
	}
	
	/* (non-Javadoc)
	 * @see ca.mitmaro.commandline.userinterface.TextBased#waitForResponse()
	 */
	public String waitForResponse() throws IOException {
		
		String line = null;
		do {
			
			if (this.options.size() == 0) {
				return null;
			}
			
			this.printTitle();
			this.terminal.out().println(this.buildMenu());
			this.printPrompt();
			
			line = this.terminal.in().readLine().trim();
			
			// return a default on no option
			if (line.trim().equals("")) {
				if (this.default_item != null) {
					return this.default_item;
				} else if (this.allow_abort) {
					if (this.disable_abort_prompt || this.abort_prompt.waitForResponse().equals("yes")) {
						return null;
					}
				}
				continue;
			}
			
			if (this.options.contains(line)) {
				return line;
			} else if (line.equals("[" + this.default_item + "]")) { // handle default item
				return this.default_item;
			} else {
				try {
					return this.options.get(Integer.parseInt(line) - 1);
				} catch (NumberFormatException e) {
				} catch (IndexOutOfBoundsException e) {
				}
			}
			
			this.terminal.out().println("Don't understand " + line);
			this.terminal.out().flush();
			
		} while (true);
	}
	
	/**
	 * Builds the menu text. Multiple calls to this method will use a cached copy of the menu.
	 * 
	 * @return The menu text
	 */
	private String buildMenu() {
		if (this.menu_cache == null) {
			
			int max_option_length = 0;
			
			ArrayList<String> opts = new ArrayList<String>(this.options);
			
			// highlight default option
			if (this.default_item != null) {
				opts.set(this.options.indexOf(this.default_item), "[" + this.default_item + "]");
			}
			
			for (String option: opts) {
				if (option.length() > max_option_length) {
					max_option_length = option.length();
				}
			}
			
			StringBuilder builder = new StringBuilder();
			
			int num_columns = (int)Math.floor(this.terminal.getTermWidth() / (max_option_length + 7));
			
			int column_number = 0;
			
			int command_number = 1;
			
			for (String option: opts) {
				
				builder.append(String.format("%3d: %-" + max_option_length + "s  ", command_number, option));
				column_number++;
				command_number++;
				if (column_number > num_columns) {
					builder.append("\n");
					column_number = 0;
				}
			}
			
			this.menu_cache = builder.toString();
		}
		
		return this.menu_cache;
		
	}
	
}
