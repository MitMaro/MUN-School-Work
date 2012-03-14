/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

import ca.mitmaro.commandline.command.DefaultCommands;
import ca.mitmaro.commandline.help.Message;
import ca.mitmaro.commandline.help.System;
import ca.mitmaro.commandline.term.Terminal;

public abstract class CommandLine {
	
	public class StatusCode {
		public static final int EXIT = 0;
		public static final int NON_FATAL = 1;
		public static final int USER = 2;
		public static final int IO_ERROR = 4;
		public static final int INVALID_OPERATION = 8 | NON_FATAL;
		
		public static final int OK = NON_FATAL;
	}
	
	private static class Command {
		private Object obj;
		private Method method;
		
		/**
		 * @param name The name of the command
		 * @param method The method
		 */
		public Command(Object obj, Method method) {
			this.obj = obj;
			this.method = method;
		}
		
		public int invoke(String[] args) throws Throwable{
			try {
				return (Integer)method.invoke(this.obj, (Object)args);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				throw e.getCause();
			}
			return -1;
		}
	}
	
	protected final System help = new System();
	
	private final HashMap<String, Command> commands = new HashMap<String, Command>();
	
	private boolean catch_exceptions = true;
	private boolean display_trace = true;
	
	protected final Terminal terminal;
	
	/**
	 * @param terminal The terminal interface
	 */
	public CommandLine(Terminal terminal) {
		this.terminal = terminal;
	}
	
	public void addDefaultCommands() {
		this.addCommandClass(new DefaultCommands(this.help, this.terminal));
	}
	
	/**
	 * Adds all commands to the interface annotated in the class
	 * @param obj The object for the method
	 * @param cls
	 */
	public void addCommandClass(Object obj) {
		
		Class<?> cls = obj.getClass();
		
		ca.mitmaro.commandline.command.annotation.Command cmd_ann;
		ca.mitmaro.commandline.command.annotation.Help help_ann;
		ca.mitmaro.commandline.command.annotation.Helps helps_ann;
		
		Message help;
		Command cmd;
		
		for (Method met: cls.getDeclaredMethods()) {
			cmd_ann = met.getAnnotation(ca.mitmaro.commandline.command.annotation.Command.class);
			help_ann = met.getAnnotation(ca.mitmaro.commandline.command.annotation.Help.class);
			helps_ann = met.getAnnotation(ca.mitmaro.commandline.command.annotation.Helps.class);
			help = null;
			if (cmd_ann != null) {
				cmd = new Command(obj, met);
				this.commands.put(cmd_ann.name(), cmd);
				
				// single help
				if (help_ann != null) {
					help = new Message(help_ann.usage(), help_ann.help());
					this.help.addMessage(cmd_ann.name(), help);
				}
				
				// list of helps
				if (helps_ann != null) {
					for (ca.mitmaro.commandline.command.annotation.Help ann: helps_ann.value()) {
						help = new Message(ann.usage(), ann.help());
						this.help.addMessage(cmd_ann.name(), help);
					}
				}
				
			}
		}
	}
	
	/**
	 * Get a set of command names
	 * 
	 * @return The command names
	 */
	public Set<String> getCommandNames() {
		return this.commands.keySet();
	}
	
	/**
	 * True if the command exists
	 * 
	 * @param name A command name
	 * @return The command
	 */
	public boolean hasCommand(String name) {
		return this.commands.containsKey(name);
	}
	
	/**
	 * Get a command by name
	 * 
	 * @param name A command name
	 * @return The command
	 */
	public Command getCommand(String name) {
		return this.commands.get(name);
	}
	
	/**
	 * Run the command with the given name
	 * @param name The command name
	 * @return
	 */
	public int runCommand(String name, String[] args) throws Throwable {
		return this.commands.get(name).invoke(args);
	}
	
	public void catchExceptions(boolean v) {
		this.catch_exceptions = v;
	}
	
	public void displayTrace(boolean v) {
		this.display_trace = v;
	}
	
	/**
	 * Runs the command line application.
	 * @throws Any exception generated
	 */
	public int run() throws Throwable {
		
		int code;
		
		while (true) {
			this.terminal.flush();
			if (this.catch_exceptions) {
				try {
					code = this.mainloop();
					if (code == 0 || (code & StatusCode.NON_FATAL) != StatusCode.NON_FATAL) {
						return code;
					}
				} catch (IllegalArgumentException e) {
					this.invalidArgumentHandler(e);
					continue;
				} catch (InvocationTargetException e) {
					throw e.getCause();
				} catch (IOException e) {
					this.ioExceptionHandler(e);
					return StatusCode.IO_ERROR; // IO error
				} catch (Exception e) {
					if (this.display_trace) {
						e.printStackTrace(this.terminal.err());
					}
					continue;
				}
			} else {
				code = this.mainloop();
				if (code == 0 || (code & StatusCode.NON_FATAL) != StatusCode.NON_FATAL) {
					return code;
				}
			}
		}
	}

	public void invalidArgumentHandler(IllegalArgumentException e) {
		this.terminal.err().println("Argument Error: " + e.getMessage() + "\n");
	}

	public void ioExceptionHandler(IOException e) {
		this.terminal.err().println("IO Error: " + e.getMessage() + "\n");
	}
	
	/**
	 * The main loop of the application. If this method returns a values
	 * other than 1 the loop will exit. The returned value will be passed
	 * to System.exit().
	 * @return A status code
	 */
	public abstract int mainloop() throws Throwable;
	
}

