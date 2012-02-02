/**
 * CS 3725 - Assignment #1
 * By: Tim Oram (200529220)
 * Simple MIPS Processor
 *
 * Register Multiplexer - choose one register, or choose the other
 *     This one works on bytes as bytes
 */
package ca.mitmaro.mips.processor;

public class RegisterMultiplexer {
	
	// the register values
	private byte value_a = 0;
	private byte value_b = 0;
	
	// selector
	private boolean use_a = true;
	
	// set the values
	public void setValueA(byte v) {
		this.value_a = v;
	}
	public void setValueB(byte v) {
		this.value_b = v;
	}
	
	// selector
	public void useA() {
		this.use_a = true;
	}
	public void useB() {
		this.use_a = false;
	}
	
	// get which register to use
	public byte getValue() {
		if (this.use_a) {
			return this.value_a;
		} else {
			return this.value_b;
		}
	}
}
