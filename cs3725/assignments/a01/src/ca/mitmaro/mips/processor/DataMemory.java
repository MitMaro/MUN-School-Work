/**
 * CS 3725 - Assignment #1
 * By: Tim Oram (200529220)
 * Simple MIPS Processor
 *
 * DataMemory - We need data (this is the .data)
 */
package ca.mitmaro.mips.processor;

public class DataMemory {
	
	// our ram
	private Memory memory;
	
	// the address where to set
	private int address;
	
	// the processor always trys to write every cycle, this disables the write
	private boolean write_enabled = false;
	
	public DataMemory(Memory memory) {
		this.memory = memory;
	}
	
	// set the address for read/write
	public void setAddress(int address) {
		this.address = address;
	}
	
	// write data to memoy
	public void writeData(int data) {
		if (this.write_enabled) {
			this.memory.set(this.address, data);
		}
	}
	
	// write data to memory, returns the next address to write data in sequence
	public int writeRawData(int address, byte[] data) {
		int i = this.memory.setRaw(address, data);
		return i + this.address;
	}
	
	// read data from memory at address
	public int readData() {
		return this.memory.get(this.address);
	}
	
	// enable writing for this cycle
	public void enableWrite() {
		this.write_enabled = true;
	}
	
	// disable writing for this cycle
	public void disableWrite() {
		this.write_enabled = false;
	}
	
}
