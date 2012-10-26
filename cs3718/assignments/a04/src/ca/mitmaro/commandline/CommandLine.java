/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
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

/**
 * A command line application base class. Contains a basc while loop,
 * return code system, command system and a help system.
 * 
 * @author Tim Oram (MitMaro)
 *
 */
/**
 * @author mitmaro
 *
 */
/**
 * @author mitmaro
 *
 */
public abstract class CommandLine {
	
	/**
	 * Status code enum (more or less)
	 * 
	 * @author mitmaro
	 *
	 */
	public class StatusCode {
		/**
		 * The exit return code
		 */
		public static final int EXIT = 0;
		/**
		 * A non-fatal return code
		 */
		public static final int NON_FATAL = 1;
		/**
		 * Defines a user defined code. Used in classes that extend this one 
		 */
		public static final int USER = 2;
		/**
		 * An IO error exception
		 */
		public static final int IO_ERROR = 4;
		/**
		 * A non fatal invalid operation code
		 */
		public static final int INVALID_OPERATION = 8 | StatusCode.NON_FATAL;
		/**
		 * A fatal error
		 */
		public static final int FATAL_ERROR = 16;
		
		/**
		 * Ok is a non fatal code
		 */
		public static final int OK = StatusCode.NON_FATAL;
	}
	
	/**
	 * A command with a method and object (reflection based)
	 */
	private static class Command {
		private Object obj;
		private Method method;
		
		/**
		 * Constructs a Command given the object of the command and the method
		 * 
		 * @param obj The command class object
		 * @param method The method
		 */
		public Command(Object obj, Method method) {
			this.obj = obj;
			this.method = method;
		}
		
		/**
		 * Invoke this command passing the arguments provided into the method
		 * 
		 * @param args The args for the method being invoked
		 * @return A StatusCode as an int
		 * @throws Throwable Any exception created by this method is escalated up
		 */
		public int invoke(String[] args) throws Throwable{
			try {
				return (Integer)this.method.invoke(this.obj, (Object)args);
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
	
	/**
	 * An instance of the help system
	 */
	protected final System help = new System();
	
	/**
	 * A mapping of command names to command classes
	 */
	private final HashMap<String, Command> commands = new HashMap<String, Command>();
	
	/**
	 * Catch any exceptions and don't let them get to the default exception handler
	 */
	private boolean catch_exceptions = true;
	/**
	 * Display a stack trace when there is an exception
	 */
	private boolean display_trace = true;
	
	/**
	 * A terminal instance
	 */
	protected final Terminal terminal;
	
	/**
	 * @param terminal The terminal interface
	 */
	public CommandLine(Terminal terminal) {
		this.terminal = terminal;
	}
	
	/**
	 * Helper methof to add the default commands class
	 */
	public void addDefaultCommands() {
		this.addCommandClass(new DefaultCommands(this.help, this.terminal));
	}
	
	/**
	 * Adds all commands to the interface annotated in the class
	 * @param obj An object containing methods annotated as commands
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
	 * @return True if the command exists, else false
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
	 * @param args The arguments to pass along to the command
	 * @return A StatusCode
	 */
	public int runCommand(String name, String[] args) throws Throwable {
		return this.commands.get(name).invoke(args);
	}
	
	/**
	 * Set to catch exceptions or not
	 * @param v The value
	 */
	public void catchExceptions(boolean v) {
		this.catch_exceptions = v;
	}
	
	
	/**
	 * Set to display a trace or not
	 * @param v The value
	 */
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

	/**
	 * The exception handler for an invalid argument
	 * 
	 * @param e The exception
	 */
	public void invalidArgumentHandler(IllegalArgumentException e) {
		this.terminal.err().println("Argument Error: " + e.getMessage() + "\n");
	}

	
	/**
	 * The IO Exception handler
	 * @param e The exception instance
	 */
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

