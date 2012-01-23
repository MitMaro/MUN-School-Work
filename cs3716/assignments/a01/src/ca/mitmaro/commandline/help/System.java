/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #1                       //
//  Program File Name: IMDB.java                               //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline.help;

import java.util.ArrayList;
import java.util.LinkedHashMap;


public class System {
	
	private String global_message = "";
	
	private int max_command_name_length = 0;
	
	public final LinkedHashMap<String, ArrayList<Message>> messages = new LinkedHashMap<String, ArrayList<Message>>();
	
	public void addMessage(String name, Message help_msg) {
		
		if (this.max_command_name_length < help_msg.getUsage().length()) {
			this.max_command_name_length = help_msg.getUsage().length();
		}
		
		if (!this.messages.containsKey(name)) {
			this.messages.put(name, new ArrayList<Message>());
		}
		
		this.messages.get(name).add(help_msg);
	}
	
	public int getMaxCommandNameLength() {
		return this.max_command_name_length;
	}
	
	public void setGloablMessage(String msg) {
		this.global_message = msg;
	}
	
	public String getGlobalMessage() {
		return this.global_message;
	}
}
