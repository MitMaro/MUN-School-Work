/**
 * CS 3725 - Assignment #1
 * By: Tim Oram (200529220)
 * Simple MIPS Processor
 *
 * Branch Instruction Controller - controls the output of the Branch Multiplexer
 */
package ca.mitmaro.mips.processor.controller;

public class BranchController {
	
	// true if the instructionw as a branch instruction
	private boolean is_branch_instruction;
	
	// bne = false, beq = true
	private boolean required_value;
	
	// the value from the alu, 0 - true, 1 - false
	private boolean alu_value;
	
	// set true for branch instructions
	public void setIsBranchInstruction(boolean value) {
		this.is_branch_instruction = value;
	}
	
	// the type of branch, true - beq, false - bne
	public void setBranchType(boolean type) {
		this.required_value = type;
	}
	
	// set the response from the alu
	public void setAluZero(boolean value) {
		this.alu_value = value;
	}
	
	// the value for the multiplxer
	public boolean getValue() {
		if (!this.is_branch_instruction) {
			return false;
		}
		return this.alu_value == this.required_value;
	}
}
