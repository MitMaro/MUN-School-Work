/**
 * Tim Oram's Commons Libray - Command Line Interface
 * By: Tim Oram
 */
package ca.mitmaro.commandline.userinterface;

import java.io.IOException;

import ca.mitmaro.commandline.term.Terminal;

public class YesNoQuestion extends TextBased<String> {
	
	public YesNoQuestion(Terminal term) {
		super(term);
	}
	
	public String waitForResponse() throws IOException {
		
		String line = null;
		int numeric;
		do {
			this.printTitle();
			this.terminal.out.println("  1: Yes\t\t2: No");
			this.printPrompt();
			
			line = this.terminal.in.readLine();
			
			line = line.trim().toLowerCase();
			
			if (line.equals("yes") || line.equals("y") || line.equals("1")) {
				return "yes";
			} else if (line.equals("no") || line.equals("n") || line.equals("2")) {
				return "no";
			}
			
			this.terminal.out.println("Didn't understand the response");
			this.terminal.out.flush();
			
		} while (true);
	}
	
}
