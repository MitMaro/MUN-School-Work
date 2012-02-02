/**
 * CS 3725 - Assignment #1
 * By: Tim Oram (200529220)
 * Simple MIPS Processor
 *
 * InstructionMemory - Code memoy (this is the .text section)
 */
package ca.mitmaro.mips.processor;

public class InstructionMemory {
	
	// the ram
	private Memory memory;
	
	// program counter
	private int pc = 0;
	
	// private instruction pointer - the location to write instructions
	private int inst_pointer = 0;
	
	public InstructionMemory(Memory memory) {
		this.memory = memory;
	}
	
	// loads a program from an array into the ram
	public void loadInstruction(int instr) {
		this.memory.set(this.inst_pointer, instr);
		this.inst_pointer += 4;
	}
	
	public void clearInstructionMemory() {
		for (int i = 0; i <= this.inst_pointer; i++) {
			this.memory.set(i, 0);
		}
		this.inst_pointer = 0;
	}
	
	// set the program counter to an address
	public void setPC(int address) {
		if (this.memory.isValidAddress(address)) {
			this.pc = address;
		}
	}
	
	// read the instruction pointed to by pc
	public int getInstruction() {
		return this.memory.get(this.pc);
	}
	
	// get the program counter
	public int getPC() {
		return this.pc;
	}
	
}
