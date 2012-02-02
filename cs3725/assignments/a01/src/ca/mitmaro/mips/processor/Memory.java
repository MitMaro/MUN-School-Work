/**
 * CS 3725 - Assignment #1
 * By: Tim Oram (200529220)
 * Simple MIPS Processor
 *
 * Memory - it's RAM
 */
package ca.mitmaro.mips.processor;

// word aligned memory
public class Memory {
	
	// an array for memory
	private byte[] memory;
	
	// must be word aligned value (ie. multiple of 4)
	public Memory(int size) {
		this.memory = new byte[size];
	}
	
	// get an int (4 bytes) from memory at offset
	public int get(int offset) {
		int rtn = 0;
		if (this.isValidAddress(offset)) {
			rtn |= ((int)this.memory[offset]) & 0xFF;
			rtn <<= 8;
			rtn |= ((int)this.memory[offset+1]) & 0xFF;
			rtn <<= 8;
			rtn |= ((int)this.memory[offset+2]) & 0xFF;
			rtn <<= 8;
			rtn |= ((int)this.memory[offset+3]) & 0xFF;
			return rtn;
		}
		// TODO: add exception handling
		return 0;
	}
	
	// set an int to memory at offset
	public void set(int offset, int value) {
		
		if (this.isValidAddress(offset)) {
			this.memory[offset] = (byte)((value >>> 24) & 0xFF);
			this.memory[offset+1] = (byte)((value >>> 16) & 0xFF);
			this.memory[offset+2] = (byte)((value >>> 8) & 0xFF);
			this.memory[offset+3] = (byte)(value & 0xFF);
		}
		// TODO: add exception handling
	}
	
	// raw access to memory, returns number of bytes written
	public int setRaw(int offset, byte[] bytes) {
		int i = 0;
		for (byte b: bytes) {
			this.memory[offset + i] = (byte)(b & 0xFF);
			i++;
		}
		return i;
	}
	
	// is offset a valid (word aligned) address
	public boolean isValidAddress(int offset) {
		// everything is word aligned so +3 to ensure no overflow
		return offset >= 0 && offset + 3 < this.memory.length;
	}
	
}
