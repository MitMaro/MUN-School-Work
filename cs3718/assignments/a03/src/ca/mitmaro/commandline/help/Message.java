/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline.help;

public class Message {
	
	private String usage;
	private String help;
	
	/**
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
