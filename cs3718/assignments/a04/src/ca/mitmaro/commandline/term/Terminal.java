/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #3                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline.term;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * A terminal io wrapper
 * 
 * @author Tim Oram (MitMaro)
 *
 */
public class Terminal {
	
	/**
	 * The prompt string
	 */
	private String prompt = "$ ";
	
	
	/**
	 * The (virtual) width of the terminal window  
	 */
	private int width = 80;
	
	/**
	 * The input stream 
	 */
	private BufferedReader in;
	
	/**
	 * The output stream
	 */
	private PrintWriter out;
	
	/**
	 * The error output stream 
	 */
	private PrintWriter err;
	
	/**
	 * Create a terminal interface providing an input/output/error stream
	 * 
	 * @param in An input stream
	 * @param out An output stream
	 * @param err An error stream
	 */
	public Terminal(BufferedReader in, PrintWriter out, PrintWriter err) {
		this.in = in;
		this.out = out;
		this.err = err;
	}
	
	/**
	 * Set the virtual width of the terminal window
	 * 
	 * @param width The width
	 */
	public void setTermWidth(int width) {
		this.width = width;
	}
	
	public void flush() {
		this.out.flush();
		this.err.flush();
	}
	
	/**
	 * @return The virtual terminal width
	 */
	public int getTermWidth() {
		return this.width;
	}
	
	/**
	 * Print of the prompt
	 */
	public void prompt() {
		this.out.print(this.prompt);
		this.out.flush();
	}
	
	/**
	 * Set the prompt string
	 * 
	 * @param prompt
	 */
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	
	/**
	 * @return The input reader
	 */
	public BufferedReader in() {
		return this.in;
	}
	
	/**
	 * @return The output writer
	 */
	public PrintWriter out() {
		return this.out;
	}
	
	/**
	 * @return The error output writer
	 */
	public PrintWriter err() {
		return this.err;
	}
	
}
