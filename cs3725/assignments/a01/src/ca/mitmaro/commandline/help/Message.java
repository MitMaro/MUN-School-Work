/**
 * Tim Oram's Commons Libray - Command Line Interface
 * By: Tim Oram
 */
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
