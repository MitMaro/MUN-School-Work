/////////////////////////////////////////////////////////////////
//  CS 3716 (Winter 2012), Assignment #1                       //
//  Program File Name: IMDB.java                               //
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
	
	public final BufferedReader in;
	public final PrintWriter out;
	
	public Terminal() {
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.out = new PrintWriter(System.out);
	}
	
	public void setTermWidth(int width) {
		this.width = width;
	}
	
	public int getTermWidth() {
		return this.width;
	}
	
	public void prompt() {
		this.out.print(this.prompt);
		this.out.flush();
	}
	
	public Terminal setPrompt(String prompt) {
		this.prompt = prompt;
		return this;
	}
	
}
