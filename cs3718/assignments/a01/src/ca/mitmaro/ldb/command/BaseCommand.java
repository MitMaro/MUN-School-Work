/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #1                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.ldb.command;

import java.util.ArrayList;

import ca.mitmaro.ldb.Application;

public abstract class BaseCommand {
	
	public static class HelpMessage {
		
		private String usage;
		private String help;
		
		public HelpMessage(String usage, String help) {
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
	
	private ArrayList<HelpMessage> help_messages;
	
	protected final Application application;

	public BaseCommand(Application application, ArrayList<HelpMessage> msgs) {
		this.application = application;
		this.help_messages = msgs;
	}
	
	public BaseCommand(Application application, String usage, String help) {
		this.application = application;
		this.help_messages = new ArrayList<HelpMessage>();
		this.help_messages.add(new HelpMessage(usage, help));
	}
	
	public ArrayList<HelpMessage> getHelpMessages() {
		return this.help_messages;
	}
	
	public abstract int call(String[] args);
	
}
