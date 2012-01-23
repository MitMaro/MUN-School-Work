/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #1                       //
//  Program File Name: IMDB.java                               //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline.help;

public class Message {
	
	private String usage;
	private String help;
	
	public 	Message(String usage, String help) {
		this.usage = usage;
		this.help = help;
	}
	
	public String getUsage() {
		return this.usage;
	}
	
	public String getHelp() {
		return this.help;
	}
}
