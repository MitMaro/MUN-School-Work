/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline.help;

/**
 * A help message class
 * 
 * @author Tim Oram (MitMaro)
 */
public class Message {
	
	/**
	 * The usage syntax
	 */
	private String usage;
	
	/**
	 * The help message text
	 */
	private String help;
	
	/**
	 * Constructs a message from the provided usage and help message text
	 * 
	 * @param usage The usage pattern
	 * @param help The help text
	 */
	public 	Message(String usage, String help) {
		this.usage = usage;
		this.help = help;
	}
	
	/**
	 * @return The usage pattern
	 */
	public String getUsage() {
		return this.usage;
	}
	
	/**
	 * @return The help text message
	 */
	public String getHelp() {
		return this.help;
	}
}
