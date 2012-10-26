/////////////////////////////////////////////////////////////////
//  CS 3718 (Winter 2012), Assignment #2                       //
//  Program File Name: LDB.java                                //
//       Student Name: Tim Oram                                //
//         Login Name: oram                                    //
//              MUN #: 200529220                               //
/////////////////////////////////////////////////////////////////
package ca.mitmaro.commandline.term;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Terminal {
	
	private String prompt = "$ ";
	
	private int width = 80;
	
	private BufferedReader in;
	private PrintWriter out;
	private PrintWriter err;
	
	public Terminal() {
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.out = new PrintWriter(System.out);
		this.err = new PrintWriter(System.err);
	}
	
	public Terminal(BufferedReader in, PrintWriter out, PrintWriter err) {
		this.in = in;
		this.out = out;
		this.err = err;
	}
	
	/**
	 * Set the virtual width of the terminal window
	 * 
	 * @param width The width
	 * @return A fluid interface
	 */
	public Terminal setTermWidth(int width) {
		this.width = width;
		return this;
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
	 * @return A fluid interface
	 */
	public Terminal prompt() {
		this.out.print(this.prompt);
		this.out.flush();
		return this;
	}
	
	/**
	 * @param prompt
	 * @return A fluid interface
	 */
	public Terminal setPrompt(String prompt) {
		this.prompt = prompt;
		return this;
	}
	
	public BufferedReader in() {
		return this.in;
	}
	
	public PrintWriter out() {
		return this.out;
	}
	
	public PrintWriter err() {
		return this.err;
	}
	
}
