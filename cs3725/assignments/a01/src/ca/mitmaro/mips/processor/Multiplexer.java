/**
 * CS 3725 - Assignment #1
 * By: Tim Oram (200529220)
 * Simple MIPS Processor
 *
 * Multiplexer - choose one, or choose the other
 *     This one works on ints as ints
 */
package ca.mitmaro.mips.processor;

public class Multiplexer {
	
	// the values
	private int value_a = 0;
	private int value_b = 0;
	
	// the selector value (boolean for 2-1 mux)
	private boolean use_a = true;
	
	// set the values
	public void setValueA(int v) {
		this.value_a = v;
	}
	public void setValueB(int v) {
		this.value_b = v;
	}
	
	// set which one to use
	public void useA() {
		this.use_a = true;
	}
	public void useB() {
		this.use_a = false;
	}
	
	// get the value depending on the use variable
	public int getValue() {
		if (this.use_a) {
			return this.value_a;
		} else {
			return this.value_b;
		}
	}
}
