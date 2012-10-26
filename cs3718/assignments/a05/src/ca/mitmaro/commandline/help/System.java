/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline.help;

import java.util.ArrayList;
import java.util.LinkedHashMap;


/**
 * A help system containing usage and help messages and a global help message
 * 
 * @author Tim Oram (MitMaro)
 */
public class System {
	
	/**
	 * The global help message
	 */
	private String global_message = "";
	
	/**
	 * The max length of the command usage names
	 */
	private int max_command_name_length = 0;
	
	/**
	 * The help messages list
	 */
	public final LinkedHashMap<String, ArrayList<Message>> messages = new LinkedHashMap<String, ArrayList<Message>>();
	
	/**
	 * Add a message to a command
	 * 
	 * @param name The name of the command
	 * @param help_msg The help message class for the command
	 */
	public void addMessage(String name, Message help_msg) {
		
		if (this.max_command_name_length < help_msg.getUsage().length()) {
			this.max_command_name_length = help_msg.getUsage().length();
		}
		
		if (!this.messages.containsKey(name)) {
			this.messages.put(name, new ArrayList<Message>());
		}
		
		this.messages.get(name).add(help_msg);
	}
	
	/**
	 * @return The length of the command name with the greatest length.s
	 */
	public int getMaxCommandNameLength() {
		return this.max_command_name_length;
	}
	
	/**
	 * Sets the global help message that is printed before the command list
	 * 
	 * @param msg The help message
	 */
	public void setGloablMessage(String msg) {
		this.global_message = msg;
	}
	
	/**
	 * @return The global help message
	 */
	public String getGlobalMessage() {
		return this.global_message;
	}
}
